package com.wheretoshop.model;

import android.content.Context;

import java.util.List;
import java.util.ArrayList;

public class GroceryList
{
	private static GroceryList instance;
	private List<GroceryListProduct> groceryList;

	private GroceryList()
    {
		this.groceryList = new ArrayList<>();
	}

	public static GroceryList getInstance()
    {
		if (instance != null)
        {
            return instance;
        }

		instance = new GroceryList();
		return instance;
	}

	public List<GroceryListProduct> getGroceryList()
    {
		List<GroceryListProduct> groceryList = new ArrayList<>();
		for (GroceryListProduct product : this.groceryList)
        {
            groceryList.add(product);
        }

		return groceryList; 
	}

	public void add(GroceryListProduct product)
    {
		groceryList.add(product);
	}

    public void remove(int position)
    {
        groceryList.remove(position);
    }

	private void backupList()
    {
        /* Todo */
    }

	private void reloadList()
    {
        /* Todo */
    }
}
