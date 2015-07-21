package com.wheretoshop.model;

import android.content.Context;
import android.view.ContextMenu;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class GroceryList
{
    private static final String FILE_NAME = "grocery_list.data";
	private static GroceryList instance;
    private Context context;
	private List<GroceryListProduct> groceryList;
    private GroceryListJSONSerializer serializer;

	private GroceryList(Context context)
    {
        this.context = context;
        serializer = new GroceryListJSONSerializer(context, FILE_NAME);
        groceryList = new ArrayList<>();
        try
        {
            groceryList = serializer.loadGroceryList();
        }
        catch (JSONException e)
        {
            // Do nothing...
        }
        catch (IOException e)
        {
            // Do nothing...
        }
	}

	public static GroceryList getInstance(Context context)
    {
		if (instance == null)
        {
            instance = new GroceryList(context);
        }
		return instance;
	}

	public List<GroceryListProduct> getGroceryList()
    {
		List<GroceryListProduct> groceryList = new ArrayList<>();
		for (GroceryListProduct product : this.groceryList)
        {
            groceryList.add(new GroceryListProduct(product));
        }

		return groceryList; 
	}

	public void add(GroceryListProduct product)
    {
		groceryList.add(product);
        try
        {
            serializer.saveGroceryList(groceryList);
        }
        catch (JSONException e)
        {
            // Do nothing...
        }
        catch (IOException e)
        {
            // Do nothing...
        }
	}

    public void remove(int position)
    {
        groceryList.remove(position);
        try
        {
            serializer.saveGroceryList(groceryList);
        }
        catch (JSONException e)
        {
            // Do nothing...
        }
        catch (IOException e)
        {
            // Do nothing...
        }
    }
}
