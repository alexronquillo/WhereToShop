package com.wheretoshop.controller;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.wheretoshop.R;
import com.wheretoshop.model.GroceryList;
import com.wheretoshop.model.adapters.GroceryListAdapter;

public class GroceryListActivity extends ActionBarActivity
{
	private GroceryListAdapter adapter;
	private ListView listView;
	private GroceryList grocerylist;

	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_activity);
		grocerylist = GroceryList.getInstance();
		adapter = new GroceryListAdapter(this, grocerylist.getGroceryList());
		listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(adapter);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.remove:
                removeCheckedItems();
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.grocery_list_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        ComponentName searchableComponentName = new ComponentName(this, ProductSearchActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(searchableComponentName));
        searchView.setIconifiedByDefault(false);
        return true;
    }

    private void removeCheckedItems()
    {
        for (int i = listView.getChildCount() - 1; i >= 0; --i)
        {
            View child = listView.getChildAt(i);
            CheckBox checkbox = (CheckBox) child.findViewById(R.id.gl_product_checkbox);
            if (checkbox.isChecked())
            {
                grocerylist.remove(i);
            }
        }
        adapter.clear();
        adapter.addAll(grocerylist.getGroceryList());
        adapter.notifyDataSetChanged();
    }
}
