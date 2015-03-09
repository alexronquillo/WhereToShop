package com.wheretoshop.controller;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.InflateException;
import android.widget.EditText;
import android.os.Bundle;
import android.util.Log;

import com.wheretoshop.R;
import com.wheretoshop.controller.GroceryListActivity;
import com.wheretoshop.model.Product;

public class GroceryListModifyFragment extends Fragment
{
	private Product product;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Bundle args = getArguments();
		Product product;
		if(args != null && (product = (Product)args.getSerializable(GroceryListModifyActivity.PRODUCT_ARG_KEY)) != null)
			this.product = product;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View inflatedView = null;
		try
		{
			inflatedView = inflater.inflate(R.layout.grocery_list_modify_fragment, container, false);

			EditText productNameEditText = (EditText)inflatedView.findViewById(R.id.product_name_edittext);
			EditText brandNameEditText = (EditText)inflatedView.findViewById(R.id.brand_name_edittext);
			EditText sizeDescriptionEditText = (EditText)inflatedView.findViewById(R.id.size_description_edittext);
			EditText ouncesOrCountEditText = (EditText)inflatedView.findViewById(R.id.ounces_or_count_edittext);

			if(product != null)
			{
				productNameEditText.setText(product.getProductName());
				brandNameEditText.setText(product.getBrandName());
				sizeDescriptionEditText.setText(product.getSizeDescription());
				ouncesOrCountEditText.setText(product.getOuncesOrCount().toString());
			}
		}
		catch(InflateException e)
		{
			Log.e(GroceryListActivity.GROCERY_LIST_TAG, e.getMessage());
		}

		return inflatedView;
	}
	
}
