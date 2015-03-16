package com.wheretoshop.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.math.BigDecimal;
import android.util.Log;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import junit.framework.Assert;

public class ProductTableDataSource {
	private static final String GET_PRODUCTS_BY_PRODUCT_OR_BRAND_NAME_PATH = 
		"/cgi-bin/get_products_by_product_or_brand_name.py";
	private static final String GET_SIZE_DESCRIPTIONS_BY_PRODUCT_NAME_AND_BRAND_NAME = 
		"/cgi-bin/get_size_descriptions_by_product_name_and_brand_name.py";
	private static final String GET_BRAND_NAMES_BY_PRODUCT_NAME = 
		"/cgi-bin/get_brand_name_by_product_name.py";
	private static final String GET_OUNCES_OR_COUNT_BY_PRODUCT_NAME_SIZE_DESCRIPTION_AND_BRAND_NAME = 
		"/cgi-bin/get_ounces_or_count_by_product_name_size_description_and_brand_name.py";

	private static final String PRODUCT_OR_BRAND_NAME_KEY = "product_or_brand_name";
	private static final String PRODUCT_NAME_KEY = "product_name";
	private static final String BRAND_NAME_KEY = "brand_name";
	private static final String SIZE_DESCRIPTION = "size_description";
	private static final String OUNCES_OR_COUNT = "ounces_or_count";
	private static final String LOG_TAG = "PRODUCT_TABLE_DS_LOG_TAG";

	public List<Product> getProductsByProductOrBrandName(String productOrBrandName) {
		List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
		queryParams.add(new BasicNameValuePair(PRODUCT_OR_BRAND_NAME_KEY, productOrBrandName));

		String responseString = new Connection().get(GET_PRODUCTS_BY_PRODUCT_OR_BRAND_NAME_PATH, queryParams);

		return decodeJSONProductList(responseString);
	}

	public Set<String> getSizeDescriptionsByProductNameAndBrandName(String productName, String brandName) {
		List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
		queryParams.add(new BasicNameValuePair(PRODUCT_NAME_KEY, productName));
		queryParams.add(new BasicNameValuePair(BRAND_NAME_KEY, brandName));

		String responseString = new Connection().get(GET_SIZE_DESCRIPTIONS_BY_PRODUCT_NAME_AND_BRAND_NAME, queryParams);

		return decodeSizeDescriptions(responseString);
	}

	public Set<String> getBrandNamesByProductName(String productName) {
		List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
		queryParams.add(new BasicNameValuePair(PRODUCT_NAME_KEY, productName));

		String responseString = new Connection().get(GET_BRAND_NAMES_BY_PRODUCT_NAME, queryParams);

		return decodeBrandNames(responseString);
	}
	
	public Set<String> getOuncesOrCountByProductNameBrandNameAndSizeDescription(String productName, String brandName, String sizeDescription) {
		List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
		queryParams.add(new BasicNameValuePair(PRODUCT_NAME_KEY, productName));
		queryParams.add(new BasicNameValuePair(BRAND_NAME_KEY, brandName));
		queryParams.add(new BasicNameValuePair(SIZE_DESCRIPTION, sizeDescription));

		String responseString = new Connection().get(GET_OUNCES_OR_COUNT_BY_PRODUCT_NAME_SIZE_DESCRIPTION_AND_BRAND_NAME, queryParams);

		return decodeOuncesOrCounts(responseString);
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

	private Set<String> decodeOuncesOrCounts(String json) {
		Set<String> resultSet = null;

		JSONArray ouncesOrCountArray = getResultArray(json);
	
		if(ouncesOrCountArray != null) {
			resultSet = new HashSet<String>();

			try {
				for(int i = 0; i < ouncesOrCountArray.length(); ++i) {
					JSONObject obj = ouncesOrCountArray.getJSONObject(i);
					String ouncesOrCount= obj.getString("OuncesOrCount");
					resultSet.add(ouncesOrCount);
				}
			} catch(Exception e) {
				Log.e(LOG_TAG, "Error: " + e.getMessage());
			}
		}

		return resultSet;

	}

	private Set<String> decodeBrandNames(String json) {
		Set<String> resultSet = null;

		JSONArray brandNameArray = getResultArray(json);
	
		if(brandNameArray != null) {
			resultSet = new HashSet<String>();

			try {
				for(int i = 0; i < brandNameArray.length(); ++i) {
					JSONObject obj = brandNameArray.getJSONObject(i);
					String brandName= obj.getString("BrandName");
					resultSet.add(brandName);
				}
			} catch(Exception e) {
				Log.e(LOG_TAG, "Error: " + e.getMessage());
			}
		}

		return resultSet;

	}

	private Set<String> decodeSizeDescriptions(String json) {
		Set<String> resultSet = null;

		JSONArray sizeDescriptionArray = getResultArray(json);
	
		if(sizeDescriptionArray != null) {
			resultSet = new HashSet<String>();

			try {
				for(int i = 0; i < sizeDescriptionArray.length(); ++i) {
					JSONObject obj = sizeDescriptionArray.getJSONObject(i);
					String sizeDescription = obj.getString("SizeDescription");
					resultSet.add(sizeDescription);
				}
			} catch(Exception e) {
				Log.e(LOG_TAG, "Error: " + e.getMessage());
			}
		}

		return resultSet;
	}

	private List<Product> decodeJSONProductList(String json) {
		List<Product> result = null;

		JSONArray productArray = getResultArray(json);

		if(productArray != null) {

			result = new ArrayList<Product>();

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
