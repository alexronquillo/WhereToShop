package com.wheretoshop.controller;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.wheretoshop.R;
import com.wheretoshop.model.Product;

public class GroceryListModifyActivity extends SwitchFragmentActivity
{
	public static final String PRODUCT_ARG_KEY = "PRODUCT_KEY";
	private Product product = null;	

	public void setProduct(Product product)
	{
		this.product = product;
	}

	@Override
	protected Fragment getFragment(boolean isSearch)
	{
		Fragment fragment = null;
		if(isSearch)
			fragment = new GroceryListProductSearchFragment();
		else
		{
			fragment = new GroceryListModifyFragment();
			if(product != null)
			{
				Bundle args = new Bundle();
				args.putSerializable(PRODUCT_ARG_KEY, product);
				fragment.setArguments(args);
			}
		}
		return fragment;
	}
}
