package com.wheretoshop.controller;

import android.view.View;
import android.widget.AdapterView;

import com.wheretoshop.model.FragmentSwitcher;
import com.wheretoshop.model.Product;

public class GroceryListProductSearchFragment extends ProductSearchFragment
{
	@Override	
	public void onClickListItem(Product product)
	{
		if(getActivity() instanceof GroceryListModifyActivity)
			((GroceryListModifyActivity)getActivity()).setProduct(product);

		if(getActivity() instanceof FragmentSwitcher)
			((FragmentSwitcher)getActivity()).switchFragment();
	}
}
