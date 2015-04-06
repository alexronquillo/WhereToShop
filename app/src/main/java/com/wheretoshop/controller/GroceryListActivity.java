package com.wheretoshop.controller;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.wheretoshop.R;
import com.wheretoshop.model.GroceryList;
import com.wheretoshop.model.GroceryListProduct;
import com.wheretoshop.model.adapters.GroceryListAdapter;

public class GroceryListActivity extends ActionBarActivity
{
    private String LOG_TAG = "GroceryListActivity";
	private GroceryListAdapter adapter;
	private ListView listView;
	private GroceryList grocerylist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_activity);

		grocerylist = GroceryList.getInstance(this);
		adapter = new GroceryListAdapter(this, grocerylist.getGroceryList());

		listView = (ListView)findViewById(R.id.list_view);
		listView.setAdapter(adapter);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.remove:
                removeCheckedItems();
                return true;
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.grocery_list_menu, menu);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        } else {
            Log.i(LOG_TAG, "Sdk: " + Build.VERSION.SDK_INT);
        }

        return true;
    }

    private void removeCheckedItems() {
        for (int i = 0; i < listView.getChildCount(); ++i) {
            View child = listView.getChildAt(i);
            CheckBox checkbox = (CheckBox) child.findViewById(R.id.gl_product_checkbox);
            if (checkbox.isChecked()) {
                grocerylist.remove(i);
            }
        }

        adapter.clear();
        adapter.addAll(grocerylist.getGroceryList());
        adapter.notifyDataSetChanged();
        listView.refreshDrawableState();
    }
}
