package com.wheretoshop.model;

import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.content.Context;
import android.widget.TextView;

import java.util.List;

import com.wheretoshop.R;

public class GroceryListAdapter extends ArrayAdapter {
	private int layoutResId;
	private List<GroceryListProduct> grocerylist;
		
	public GroceryListAdapter(Context context, List<GroceryListProduct> grocerylist) {
		super(context, R.layout.grocery_list_item, grocerylist);
		this.grocerylist = grocerylist;
		this.layoutResId = R.layout.grocery_list_item;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)	{
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(layoutResId, null);
		}

		TextView productNameTextView = (TextView)convertView.findViewById(R.id.gl_product_name_textview);	
		TextView brandNameTextView = (TextView)convertView.findViewById(R.id.gl_brand_name_textview);
		TextView sizeDescriptionTextView = (TextView)convertView.findViewById(R.id.gl_size_description_textview);
		TextView ouncesOrCountTextView = (TextView)convertView.findViewById(R.id.gl_ounces_or_count_textview);

		GroceryListProduct groceryListProduct = grocerylist.get(position);
		Product product = groceryListProduct.getProduct();

		if(product != null) {
			productNameTextView.setText(product.getProductName());
			brandNameTextView.setText(product.getBrandName());
			sizeDescriptionTextView.setText(product.getSizeDescription());
		}

		return convertView;
	}
}
