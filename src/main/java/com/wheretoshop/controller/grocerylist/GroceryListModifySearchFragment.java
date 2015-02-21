package com.wheretoshop.controller;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.InflateException;
import android.os.Bundle;
import android.util.Log;

import com.wheretoshop.R;
import com.wheretoshop.controller.GroceryListActivity;

public class GroceryListModifySearchFragment extends Fragment
{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View inflatedView = null;
		try
		{
			inflatedView = inflater.inflate(R.layout.search_fragment, container, false); 
		}
		catch(InflateException e)
		{
			Log.e(GroceryListActivity.GROCERY_LIST_TAG, e.getMessage());
		}

		return inflatedView;
	}
}
