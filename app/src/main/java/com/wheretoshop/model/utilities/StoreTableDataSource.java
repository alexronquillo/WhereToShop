package com.wheretoshop.model.utilities;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StoreTableDataSource
{
    private static final String LOG_TAG = "StoreTableDS";
    private static final String GET_STORES_PATH = "/cgi-bin/get_stores.py";

    public List<String> getStores()
    {
        try
        {
            Connection conn = new Connection();
            String responseString = conn.get(GET_STORES_PATH, null);

            JSONObject responseObject = new JSONObject(responseString);
            String response = responseObject.getString("Response");
            if (response.charAt(0) == 'K')
            {
                JSONArray result = responseObject.getJSONArray("Result");
                List<String> storeNames = new ArrayList<>();
                for (int i = 0; i < result.length(); ++i)
                {
                    JSONObject nameObject = result.getJSONObject(i);
                    storeNames.add(nameObject.getString("StoreName"));
                }
                return storeNames;
            }
        }
        catch (Exception e)
        {
            Log.e(LOG_TAG, "Exception: " + e.getMessage());
        }
        return null;
    }
}
