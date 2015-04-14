package com.wheretoshop.model.utilities;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StoreTableDataSource
{
    private static final String GET_STORE_ID_BY_STORE_NAME_AND_ZIP_CODE_PATH =
            "/cgi-bin/get_store_id_by_store_name_and_zip_code.py";
    private static final String INSERT_STORE_PATH =
            "/cgi-bin/insert_store.py";
    private static final String STORE_NAME_KEY = "store_name";
    private static final String ZIP_CODE_KEY = "zip_code";

    public int postStore(String storeName, String zipCode)
    {
        List<NameValuePair> queryParams = new ArrayList<>();
        queryParams.add(new BasicNameValuePair(STORE_NAME_KEY, storeName));
        queryParams.add(new BasicNameValuePair(ZIP_CODE_KEY, zipCode));

        String responseString = new Connection().post(INSERT_STORE_PATH, queryParams);

        int storeId = -1;
        try
        {
            JSONObject resultObject = new JSONObject(responseString);
            String response = resultObject.getString("Response");

            if (response.charAt(0) == 'K')
            {
                storeId = resultObject.getInt("Result");
            }
            else
            {
                Log.e("StoreTableDataSource", "Exception: " + resultObject.getString("Result"));
            }
        }
        catch (JSONException e)
        {
            Log.e("StoreTableDataSource", "Exception: " + e.getMessage() + "\n\nStackTrace: " + e.getStackTrace());
        }

        return storeId;
    }

    public int getStoreId(String storeName, String zipCode)
    {
        List<NameValuePair> queryParams = new ArrayList<>();
        queryParams.add(new BasicNameValuePair(STORE_NAME_KEY, storeName));
        queryParams.add(new BasicNameValuePair(ZIP_CODE_KEY, zipCode));

        String responseString = new Connection().get(GET_STORE_ID_BY_STORE_NAME_AND_ZIP_CODE_PATH, queryParams);

        int storeId = -1;
        try
        {
            JSONObject resultObject = new JSONObject(responseString);
            String response = resultObject.getString("Response");

            if (response.charAt(0) == 'K')
            {
                storeId = resultObject.getInt("Result");
            }
            else
            {
                Log.e("StoreTableDataSource", "Exception: " + resultObject.getString("Result"));
            }
        }
        catch (JSONException e)
        {
            Log.e("StoreTableDataSource", "Exception: " + e.getMessage() + "\n\nStackTrace: " + e.getStackTrace());
        }

        return storeId;
    }
}
