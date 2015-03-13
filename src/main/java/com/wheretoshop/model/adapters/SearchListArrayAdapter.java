package com.wheretoshop.model;

import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.util.Log;
import android.content.Context;
import java.util.List;

import com.wheretoshop.R;

public class SearchListArrayAdapter extends ArrayAdapter
{
	private static final String LOG_TAG = "PRODUCT_ARRAY_ADAPTER_LOG_TAG";
	private int layoutResID = R.layout.search_list_item;

	public SearchListArrayAdapter(Context context, List<Product> objects) { super(context, R.layout.search_list_item, objects); }

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(layoutResID, null);
		}

		Product product = null;
		if(getItem(position) instanceof Product)
			product = (Product)getItem(position);
		else
			Log.e(LOG_TAG, "Item at position " + position + " is not a Product");

		TextView brandNameTextView = null;
		TextView productNameTextView = null;
		TextView sizeDescriptionTextView = null;
		TextView ouncesOrCountTextView = null;
		try
		{
			brandNameTextView = (TextView)convertView.findViewById(R.id.brand_name_textview);
			productNameTextView = (TextView)convertView.findViewById(R.id.product_name_textview);
			sizeDescriptionTextView = (TextView)convertView.findViewById(R.id.size_description_textview);
			ouncesOrCountTextView = (TextView)convertView.findViewById(R.id.ounces_count_textview);
		}
		catch(Exception e)
		{
			Log.e(LOG_TAG, "Cannot find TextView");
		}

		brandNameTextView.setText(product.getBrandName());
		productNameTextView.setText(product.getProductName());
		sizeDescriptionTextView.setText(product.getSizeDescription());
		ouncesOrCountTextView.setText(product.getOuncesOrCount().toString());

		return convertView;
	}

	public void replace(List<Product> products)
	{
		clear();
		addAll(products);
	}
}
