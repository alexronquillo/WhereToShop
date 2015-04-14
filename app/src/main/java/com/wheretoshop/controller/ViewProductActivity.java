package com.wheretoshop.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wheretoshop.R;
import com.wheretoshop.model.GroceryList;
import com.wheretoshop.model.GroceryListProduct;
import com.wheretoshop.model.Product;

import java.math.BigDecimal;

public class ViewProductActivity extends Activity
{
    private static final Integer DEFAULT_QUANTITY = 1;
    private Product product = null;
    private TextView productNameTextView;
    private TextView brandNameTextView;
    private TextView sizeDescriptionTextView;
    private TextView ouncesOrCountTextView;
    private Button addButton;
    private Button contributeButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity);

        productNameTextView = (TextView) findViewById(R.id.product_name_textview);
        brandNameTextView = (TextView) findViewById(R.id.brand_name_textview);
        sizeDescriptionTextView = (TextView) findViewById(R.id.size_description_textview);
        ouncesOrCountTextView = (TextView) findViewById(R.id.ounces_count_textview);

        if (getIntent().hasExtra(ProductSearchActivity.PRODUCT_EXTRA))
        {
            product = (Product) getIntent().getSerializableExtra(ProductSearchActivity.PRODUCT_EXTRA);
            productNameTextView.setText(product.getProductName());
            brandNameTextView.setText(product.getBrandName());
            sizeDescriptionTextView.setText(product.getSizeDescription());
            ouncesOrCountTextView.setText(product.getOuncesOrCount().toString());
        }

        addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product != null)
                {
                    GroceryList groceryList = GroceryList.getInstance();
                    groceryList.add(new GroceryListProduct(product, DEFAULT_QUANTITY));
                    startGroceryListActivity();
                }
            }
        });

        contributeButton = (Button) findViewById(R.id.contribute_button);
        contributeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                contributeProduct();
            }
        });
    }

    private void startGroceryListActivity()
    {
        Intent intent = new Intent(this, GroceryListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void contributeProduct()
    {
        try
        {
            Product product = new Product(-1, productNameTextView.getText().toString(), brandNameTextView.getText().toString(), new BigDecimal(ouncesOrCountTextView.getText().toString()), sizeDescriptionTextView.getText().toString());
            Intent intent = new Intent(this, PriceContributorActivity.class);
            intent.putExtra("PRODUCT_EXTRA", product);
            startActivity(intent);
        }
        catch (Exception e)
        {
            Log.e("ViewProduct", "Exception: " + e.getMessage() + "\n\nStackTrace: " + e.getStackTrace());
        }
    }
}
