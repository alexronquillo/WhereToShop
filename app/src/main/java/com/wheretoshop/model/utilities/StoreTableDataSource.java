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
    private static final String INSERT_STORE_PATH = "/cgi-bin/insert_store.py";
    private static final String LOG_TAG = "StoreTableDS";
    private static final String STORE_NAME_KEY = "store_name";
    private static final String ZIP_CODE_KEY = "store_zip_code";

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
                Log.e(LOG_TAG, "Exception: " + resultObject.getString("Result"));
            }
        }
        catch (JSONException e)
        {
            Log.e(LOG_TAG, "Exception: " + e.getMessage() + "\n\nStackTrace: " + e.getStackTrace());
        }

        if (storeId == -1)
        {
            Log.e(LOG_TAG, "Error: could not get store id");
        }

        return storeId;
    }
}
