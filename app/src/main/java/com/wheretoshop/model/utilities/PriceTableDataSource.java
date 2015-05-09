package com.wheretoshop.model.utilities;

import android.util.Log;

import com.wheretoshop.model.GroceryList;
import com.wheretoshop.model.GroceryListProduct;
import com.wheretoshop.model.WhereToShopProduct;

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

    // Todo: modify endpoint:
    private static final String INSERT_PRICE_PATH = "/cgi-bin/insert_price.py";

    private static final String PRODUCT_ID_KEY = "product_id";
    private static final String ZIPCODE_KEY = "zipcode";

    // Todo: modify key constants to the params from the server script:
    private static final String PRODUCT_NAME_KEY = "product_name";
    private static final String BRAND_NAME_KEY = "brand_name";
    private static final String SIZE_DESCRIPTION_KEY = "size_description";
    private static final String OUNCES_OR_COUNT_KEY = "ounces_or_count";
    private static final String STORE_NAME_KEY = "store_name";
    private static final String ZIP_CODE_KEY = "zip_code";
    private static final String GENERAL_PRICE_KEY = "general_price";

    public boolean insertPrice(String productName, String brandName, String sizeDescription,
                              String ouncesOrCount, String storeName, String zipCode, String generalPrice)
    {
        List<NameValuePair> queryParams = new ArrayList<>();
        queryParams.add(new BasicNameValuePair(PRODUCT_NAME_KEY, productName));
        queryParams.add(new BasicNameValuePair(BRAND_NAME_KEY, brandName));
        queryParams.add(new BasicNameValuePair(SIZE_DESCRIPTION_KEY, sizeDescription));
        queryParams.add(new BasicNameValuePair(OUNCES_OR_COUNT_KEY, ouncesOrCount));
        queryParams.add(new BasicNameValuePair(STORE_NAME_KEY, storeName));
        queryParams.add(new BasicNameValuePair(ZIP_CODE_KEY, zipCode));
        queryParams.add(new BasicNameValuePair(GENERAL_PRICE_KEY, generalPrice));

        String responseString = new Connection().post(INSERT_PRICE_PATH, queryParams);

        try
        {
            JSONObject resultObject = new JSONObject(responseString);
            String response = resultObject.getString("Response");

            if (response.charAt(0) == 'K')
            {
                return true;
            }
            else
            {
                Log.e(LOG_TAG, "Exception: " + resultObject.getString("Result"));
            }
        }
        catch (JSONException e)
        {
            Log.e(LOG_TAG, "Exception: " + e.getMessage());
        }
        return false;
    }

    public List<WhereToShopProduct> getBestPrice()
    {
        GroceryList groceryList = GroceryList.getInstance();
        List<GroceryListProduct> list = groceryList.getGroceryList();
        String testZipCode = "30144"; // For testing purposes, always use the local zip code
        List<NameValuePair> queryParams = new ArrayList<>();
        List<WhereToShopProduct> whereToShopProductList = new ArrayList<>();

        for (GroceryListProduct groceryListProduct : list)
        {
            queryParams.clear();
            queryParams.add(new BasicNameValuePair(PRODUCT_ID_KEY, "" + groceryListProduct.getProduct().getProductId()));
            queryParams.add(new BasicNameValuePair(ZIPCODE_KEY, testZipCode));
            String responseString = new Connection().get(WTS_BASE_PATH, queryParams);
            try
            {
                JSONObject responseObject = new JSONObject(responseString);
                String response = responseObject.getString("Response");

                if (response.charAt(0) == 'K')
                {
                    JSONObject resultObject = responseObject.getJSONObject("Result");
                    String price = resultObject.getString("price");
                    String storeName = resultObject.getString("store_name");
                    WhereToShopProduct product = new WhereToShopProduct(groceryListProduct.getProduct(), storeName, price);
                    whereToShopProductList.add(product);
                } else
                {
                    Log.e(LOG_TAG, "Exception: " + responseObject.getString("Result"));
                }
            } catch (JSONException e)
            {
                Log.e(LOG_TAG, "Exception: " + e.getMessage());
            }
        }
        return whereToShopProductList;
    }
}
