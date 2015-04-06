package com.wheretoshop.model;

import android.content.Context;

import java.util.List;
import java.util.ArrayList;

public class GroceryList {
	private static GroceryList instance;
	private Context context;
	private List<GroceryListProduct> groceryList;

	private GroceryList(Context context) {
		this.context = context;	
		this.groceryList = new ArrayList<GroceryListProduct>();
	}

	public static GroceryList getInstance(Context context) {
		if (instance != null)
			return instance;

		instance = new GroceryList(context);
		return instance;
	}

	public List<GroceryListProduct> getGroceryList() { 
		List<GroceryListProduct> groceryList = new ArrayList<GroceryListProduct>();
		for(GroceryListProduct product : this.groceryList)
			groceryList.add(product);

		return groceryList; 
	}

	public void add(GroceryListProduct product) {
		groceryList.add(product);
	}

    public void remove(int position) {
        groceryList.remove(position);
    }

	private void backupList() { /* Todo */ }
	private void reloadList() { /* Todo */ }
}
