package com.wheretoshop.model.utilities;

import android.util.Log;

import com.wheretoshop.model.GroceryList;
import com.wheretoshop.model.GroceryListProduct;
import com.wheretoshop.model.WTSProduct;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PriceTableDataSource 
{
    private static final String LOG_TAG = "PriceTableDS";
    private static final String WTS_BASE_PATH = "/cgi-bin/wts_base.py";
    private static final String PRODUCT_ID_KEY = "product_id";
    private static final String ZIPCODE_KEY = "zipcode";

    public List<WTSProduct> getBestPrice()
    {
        GroceryList groceryList = GroceryList.getInstance();
        List<GroceryListProduct> list = groceryList.getGroceryList();
        String testZipCode = "30144"; // For testing purposes, always use the local zip code
        List<NameValuePair> queryParams = new ArrayList<>();
        List<WTSProduct> wtsList = new ArrayList<>();

        for (GroceryListProduct glProduct : list)
        {
            queryParams.clear();
            queryParams.add(new BasicNameValuePair(PRODUCT_ID_KEY, "" + glProduct.getProduct().getProductId()));
            queryParams.add(new BasicNameValuePair(ZIPCODE_KEY, testZipCode));
            String responseString = new Connection().post(WTS_BASE_PATH, queryParams);
            try
            {
                JSONObject responseObject = new JSONObject(responseString);
                String response = responseObject.getString("Response");

                if (response.charAt(0) == 'K')
                {
                    JSONObject resultObject = responseObject.getJSONObject("Result");
                    String price = resultObject.getString("price");
                    Log.i(LOG_TAG, "Best price for product id " + glProduct.getProduct().getProductId() + " is " + price);
                    String storeName = resultObject.getString("store_name");
                    Log.i(LOG_TAG, "Store name for best price for product id " + glProduct.getProduct().getProductId() + " is " + storeName);
                    WTSProduct product = new WTSProduct(glProduct.getProduct(), storeName, price);
                    wtsList.add(product);
                } else
                {
                    Log.e(LOG_TAG, "Exception: " + responseObject.getString("Result"));
                }
            } catch (JSONException e)
            {
                Log.e(LOG_TAG, "Exception: " + e.getMessage() + "\n\nStackTrace: " + e.getStackTrace().toString());
            }
        }
        return wtsList;
    }
}
