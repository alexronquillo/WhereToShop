package com.wheretoshop.controller;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.util.Log;

import com.wheretoshop.R;

public class HomeActivity extends ActionBarActivity
{
	private final static String HOME_ACTIVITY_LOG_TAG = "HOME_ACTIVITY_LOG_TAG";
	private Button groceryListButton;

    /** 
		Called when the activity is first created. 
		
		@param savedInstanceState The last saved instance state of the activity.
	*/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

		groceryListButton = (Button)findViewById(R.id.grocery_list_button);
		groceryListButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				try
				{
					Intent intent = new Intent(getApplicationContext(), GroceryListActivity.class);
					startActivity(intent);
				}
				catch(ActivityNotFoundException e)
				{
					Log.e(HOME_ACTIVITY_LOG_TAG, e.getMessage());
				}
			}
		});
    }
}
