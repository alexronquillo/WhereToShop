package com.wheretoshop.model;

import android.content.Context;
import org.json.*;

import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class GroceryListJSONSerializer
{
	private Context appContext;
	private String filename;

	public GroceryListJSONSerializer(Context context, String filename)
	{
		this.appContext = context;
		this.filename = filename;
	}

	public List<GroceryListProduct> loadGroceryList() throws JSONException, IOException
	{
		List<GroceryListProduct> groceryList = new ArrayList<GroceryListProduct>();
		BufferedReader reader = null;
		try
		{
			InputStream in = appContext.openFileInput(filename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null)
			{
				jsonString.append(line);
			}

			JSONArray array = (JSONArray)new JSONTokener(jsonString.toString()).nextValue();
			for(int i = 0; i < array.length(); ++i)
			{
				groceryList.add(new GroceryListProduct(array.getJSONObject(i)));
			}
		}
		catch(FileNotFoundException e)
        {
            // Do nothing...
        }
		finally
		{
			if(reader != null)
            {
                reader.close();
            }
		}
		return groceryList;
	}

	public void saveGroceryList(List<GroceryListProduct> groceryList) throws JSONException, IOException
	{
		JSONArray array = new JSONArray();
		for(GroceryListProduct entry : groceryList)
			array.put(entry.toJSON());

		Writer writer = null;
		try
		{
			OutputStream outputStream = appContext.openFileOutput(filename, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(outputStream);
			writer.write(array.toString());
		}
		finally
		{
			if(writer != null)
				writer.close();
		}
	}
}
