package com.wheretoshop.controller;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;

import com.wheretoshop.R;
import com.wheretoshop.model.Product;

public class GroceryListModifyActivity extends SearchSwitchFragmentActivity {
	private static final String LOG_TAG = "GroceryListModifyActivity_LOG_TAG";
	public static final String PRODUCT_ARG_KEY = "PRODUCT_KEY";
	private boolean addProduct = false;
	private Product product = null;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		if(intent != null) {
			Boolean addProduct = intent.getBooleanExtra(GroceryListActivity.ADD_MODIFY_FLAG_KEY, false);
			this.addProduct = addProduct;
		}
	}

	public void setProduct(Product product) { this.product = product; }

	public void setAddProductFlag(boolean flag) { this.addProduct = flag; }

	public boolean isAddProduct() { return addProduct; }

	@Override
	protected Fragment getFragment(boolean isSearch) {
		Fragment fragment = null;
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
