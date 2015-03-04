package com.wheretoshop.model;

import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import android.util.Log;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import junit.framework.Assert;

public class ProductTableDataSource
{
	private static final String GET_PRODUCTS_BY_PRODUCT_OR_BRAND_NAME_PATH = "/cgi-bin/get_products_by_product_or_brand_name.py";
	private static final String LOG_TAG = "PRODUCT_TABLE_DS_LOG_TAG";

	public List<Product> getProductsByProductOrBrandName(String productOrBrandName)
	{
		List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
		queryParams.add(new BasicNameValuePair("product_or_brand_name", productOrBrandName));

		String responseString = new Connection().get(GET_PRODUCTS_BY_PRODUCT_OR_BRAND_NAME_PATH, queryParams);

		return decodeJSONProductList(responseString);
	}

	private List<Product> decodeJSONProductList(String json)
	{
		List<Product> result = null;
		try
		{
			JSONObject jsonProductObject = new JSONObject(json);
			String response = jsonProductObject.getString("Response");
			if(response.charAt(0) == 'K')
			{
				result = new ArrayList<Product>();
				
				JSONArray productArray = jsonProductObject.getJSONArray("Products");
				for(int i = 0; i < productArray.length(); ++i)
				{
					JSONObject obj = productArray.getJSONObject(i);

					int productId = obj.getInt("ProductId");
					String productName = obj.getString("ProductName");
					String brandName = obj.getString("BrandName");
					String sizeDescription = obj.getString("SizeDescription");
					BigDecimal ouncesOrCount = new BigDecimal(obj.getString("OuncesOrCount"));

					Product product = new Product(productId, productName, brandName, ouncesOrCount, sizeDescription);

					result.add(product);
				}
			}
		}
		catch(JSONException e)
		{
			Log.e(LOG_TAG, "JSONException: " + e.getMessage());
		}
		catch(Exception e)
		{
			Log.e(LOG_TAG, "Exception: " + e.getMessage());
		}

		return result;
	}
}
