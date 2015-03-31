package com.wheretoshop.model.utilities;

import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import android.util.Log;

import com.wheretoshop.model.Product;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class ProductTableDataSource {
	private static final String GET_PRODUCTS_BY_PRODUCT_OR_BRAND_NAME_PATH = 
		"/cgi-bin/get_products_by_product_or_brand_name.py";

	private static final String PRODUCT_OR_BRAND_NAME_KEY = "product_or_brand_name";
	private static final String LOG_TAG = "ProductTableDS";

	public List<Product> getProductsByProductOrBrandName(String productOrBrandName) {
		List<NameValuePair> queryParams = new ArrayList<>();
		queryParams.add(new BasicNameValuePair(PRODUCT_OR_BRAND_NAME_KEY, productOrBrandName));

		String responseString = new Connection().get(GET_PRODUCTS_BY_PRODUCT_OR_BRAND_NAME_PATH, queryParams);

		return decodeJSONProductList(responseString);
	}

	private JSONArray getResultArray(String json) {
		JSONArray resultArray = null;

		try {
			JSONObject resultObject = new JSONObject(json);
			String response = resultObject.getString("Response");
			if(response.charAt(0) == 'K')
				resultArray = resultObject.getJSONArray("Result");
		} catch(JSONException e) {
			Log.e(LOG_TAG, "JSONException: " + e.getMessage());
		} catch(Exception e) {
			Log.e(LOG_TAG, "Exception: " + e.getMessage());
		}

		return resultArray;
	}

	private List<Product> decodeJSONProductList(String json) {
		List<Product> result = null;

		JSONArray productArray = getResultArray(json);

		if(productArray != null) {

			result = new ArrayList<>();

			try {
				for(int i = 0; i < productArray.length(); ++i) {
					JSONObject obj = productArray.getJSONObject(i);
					int productId = obj.getInt("ProductId");
					String productName = obj.getString("ProductName");
					String brandName = obj.getString("BrandName");
					String sizeDescription = obj.getString("SizeDescription");
					BigDecimal ouncesOrCount = new BigDecimal(obj.getString("OuncesOrCount"));

					Product product = new Product(productId, productName, brandName, ouncesOrCount, sizeDescription);

					result.add(product);
				}
			} catch(Exception e) {
				Log.e(LOG_TAG, "Error: " + e.getMessage());
			}
		}
				
		return result;
	}
}
