package com.wheretoshop.controller;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.util.Log;

import com.wheretoshop.R;
import com.wheretoshop.controller.grocerylist.GroceryListActivity;

public class HomeActivity extends ActionBarActivity {
	private final static String HOME_ACTIVITY_LOG_TAG = "HOME_ACTIVITY_LOG_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
	}

	public void openGroceryList(View view) {
		try {
			Intent intent = new Intent(getApplicationContext(), GroceryListActivity.class);
			startActivity(intent);
		}
		catch(ActivityNotFoundException e) {
			Log.e(HOME_ACTIVITY_LOG_TAG, e.getMessage());
		}
	}
}
