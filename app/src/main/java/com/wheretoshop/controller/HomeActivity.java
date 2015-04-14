package com.wheretoshop.controller;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.util.Log;

import com.wheretoshop.R;

public class HomeActivity extends ActionBarActivity
{
	private final static String HOME_ACTIVITY_LOG_TAG = "HOME_ACTIVITY_LOG_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        ComponentName searchableComponentName = new ComponentName(this, ProductSearchActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(searchableComponentName));
        searchView.setIconifiedByDefault(false);

        return true;
    }

	public void openGroceryList(View view)
    {
		try
        {
			Intent intent = new Intent(this, GroceryListActivity.class);
			startActivity(intent);
		}
        catch(ActivityNotFoundException e)
        {
			Log.e(HOME_ACTIVITY_LOG_TAG, e.getMessage());
		}
	}

	public void openPriceContributor(View view)
    {
		try
        {
			Intent intent = new Intent(this, PriceContributorActivity.class);
			startActivity(intent);
		}
        catch (ActivityNotFoundException e)
        {
			Log.e(HOME_ACTIVITY_LOG_TAG, e.getMessage());
		}
	}
}
