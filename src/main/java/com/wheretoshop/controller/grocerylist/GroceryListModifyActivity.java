package com.wheretoshop.controller;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.wheretoshop.R;

public class GroceryListModifyActivity extends SwitchFragmentActivity
{
	@Override
	protected Fragment getFragment(boolean isSearch)
	{
		if(isSearch)
			return new GroceryListModifySearchFragment();
		else
			return new GroceryListModifyManualFragment();
	}
}
