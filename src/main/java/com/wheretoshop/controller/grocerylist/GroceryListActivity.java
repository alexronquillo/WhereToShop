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

public class GroceryListActivity extends ActionBarActivity
{
	public static final String GROCERY_LIST_TAG = "GROCERY_LIST_TAG";
	public static final String ADD_MODIFY_FLAG_KEY = "ADD_MODIFY_FLAG_KEY";
	public static final Boolean ADD_PRODUCT_FLAG = true;
	public static final Boolean MODIFY_PRODUCT_FLAG = false;

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
				openGroceryListModifyActivity(ADD_PRODUCT_FLAG);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void openGroceryListModifyActivity(boolean flag)
	{
		try
		{
			Intent intent = new Intent(getApplicationContext(), GroceryListModifyActivity.class);
			intent.putExtra(ADD_MODIFY_FLAG_KEY, flag);
			startActivity(intent);
		}
		catch(ActivityNotFoundException e)
		{
			Log.e(GROCERY_LIST_TAG, e.getMessage());
		}
	}
}
