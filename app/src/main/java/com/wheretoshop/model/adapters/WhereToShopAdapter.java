package com.wheretoshop.model.adapters;

import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.util.Log;
import android.content.Context;
import java.util.List;

import com.wheretoshop.R;
import com.wheretoshop.model.Product;
import com.wheretoshop.model.WTSProduct;

public class WhereToShopAdapter extends ArrayAdapter {
    private static final String LOG_TAG = "WTSAdapter";
    private int layoutResID = R.layout.wts_list_item;

    public WhereToShopAdapter(Context context, List<WTSProduct> objects)
    {
        super(context, R.layout.wts_list_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResID, null);
        }

        WTSProduct product = null;
        if (getItem(position) instanceof WTSProduct)
            product = (WTSProduct)getItem(position);
        else
            Log.e(LOG_TAG, "Item at position " + position + " is not a WTSProduct");

        TextView productNameTextView = null;
        TextView storeNameTextView = null;
        TextView generalPriceTextView = null;
        try {
            productNameTextView = (TextView)convertView.findViewById(R.id.product_name_textview);
            storeNameTextView = (TextView)convertView.findViewById(R.id.store_name_textview);
            generalPriceTextView = (TextView)convertView.findViewById(R.id.general_price_textview);
        } catch(Exception e) {
            Log.e(LOG_TAG, "Cannot find TextView");
        }

        productNameTextView.setText(product.getProduct().getProductName());
        storeNameTextView.setText(product.getStoreName());
        generalPriceTextView.setText(product.getGeneralPrice());

        return convertView;
    }

    public void replace(List<WTSProduct> products) {
        clear();
        addAll(products);
    }
}

