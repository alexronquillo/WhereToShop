package com.wheretoshop.controller.grocerylist;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.content.Intent;

import com.wheretoshop.controller.SearchSwitchFragmentActivity;
import com.wheretoshop.model.Product;

public class GroceryListModifyActivity extends SearchSwitchFragmentActivity {
	public static final String PRODUCT_ARG_KEY = "PRODUCT_KEY";
	private boolean addProduct = false;
	private Product product = null;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		if(intent != null) {
			addProduct = intent.getBooleanExtra(GroceryListActivity.ADD_MODIFY_FLAG_KEY, false);
		}
	}

	public void setProduct(Product product) { this.product = product; }

	@Override
	protected Fragment getFragment(boolean isSearch) {
		Fragment fragment;
		if(isSearch) {
			fragment = new GroceryListProductSearchFragment();
		} else {
			fragment = new GroceryListModifyFragment();
			if(product != null) {
				Bundle args = new Bundle();
				args.putSerializable(PRODUCT_ARG_KEY, product);
				args.putBoolean(GroceryListActivity.ADD_MODIFY_FLAG_KEY, addProduct);
				fragment.setArguments(args);
			}
		}

		return fragment;
	}
}
