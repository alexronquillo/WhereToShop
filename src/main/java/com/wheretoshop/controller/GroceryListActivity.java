package com.wheretoshop.controller;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.InflateException;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.util.Log;

import com.wheretoshop.R;

/**
	The GroceryList component is a component of the WhereToShop application which 
	allows users to view, add, and remove items on his/her grocery list.
*/
public class GroceryListActivity extends ActionBarActivity
{
	private static final String GROCERY_LIST_TAG = "GROCERY_LIST_TAG";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grocery_list_activity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		try
		{
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.grocery_list_activity_actions, menu);
		}
		catch(InflateException e)
		{
			Log.e(GROCERY_LIST_TAG, e.getMessage());
		}

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.action_bar_add:
				openGroceryListModifyActivity();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void openGroceryListModifyActivity()
	{
		try
		{
			Intent intent = new Intent(getApplicationContext(), GroceryListModifyActivity.class);
			startActivity(intent);
		}
		catch(ActivityNotFoundException e)
		{
			Log.e(GROCERY_LIST_TAG, e.getMessage());
		}
	}
}
