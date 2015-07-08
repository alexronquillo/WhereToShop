package com.wheretoshop.controller;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;
import java.util.HashMap;

import com.wheretoshop.R;
import com.wheretoshop.model.PriceContributionHandler;
import com.wheretoshop.model.Product;
import com.wheretoshop.model.utilities.PriceTableDataSource;

enum VIEW_ENUM
{
    PRODUCT_NAME
    , BRAND_NAME
    , SIZE_DESCRIPTION
    , OUNCES_OR_COUNT
    , STORE_NAME
    , ZIP_CODE
    , GENERAL_PRICE
}

public class PriceContributorActivity extends ActionBarActivity implements PriceContributionHandler
{
    private static final String PRODUCT_EXTRA = "PRODUCT_EXTRA";
    private EditText productNameEditText;
    private EditText brandNameEditText;
    private EditText sizeDescriptionEditText;
    private EditText ouncesOrCountEditText;
    private EditText storeNameEditText;
    private EditText zipCodeEditText;
    private EditText generalPriceEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.price_contributor_activity);

        productNameEditText = (EditText) findViewById(R.id.pc_product_name_edit_text);
        brandNameEditText = (EditText) findViewById(R.id.pc_brand_name_edit_text);
        sizeDescriptionEditText = (EditText) findViewById(R.id.pc_size_description_edit_text);
        ouncesOrCountEditText = (EditText) findViewById(R.id.pc_ounces_or_count_edit_text);
        storeNameEditText = (EditText) findViewById(R.id.pc_store_name_edit_text);
        zipCodeEditText = (EditText) findViewById(R.id.pc_zip_code_edit_text);
        generalPriceEditText = (EditText) findViewById(R.id.pc_general_price_edit_text);
        submitButton = (Button) findViewById(R.id.pc_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                insertPrice();
            }
        });

        Product product = (Product) getIntent().getSerializableExtra(PRODUCT_EXTRA);
        if (product != null)
        {
            productNameEditText.setText(product.getProductName());
            brandNameEditText.setText(product.getBrandName());
            sizeDescriptionEditText.setText(product.getSizeDescription());
            ouncesOrCountEditText.setText(product.getOuncesOrCount().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        ComponentName searchableComponentName = new ComponentName(this, ProductSearchActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(searchableComponentName));
        searchView.setIconifiedByDefault(false);
        return true;
    }

    @Override
    public void handlePriceContribution(Boolean success)
    {
        // ToDo: handle price contribution
        if (success)
        {
        }
        else
        {
        }
    }

    private void insertPrice()
    {
        // Todo: Put these into a map and pass the map
        Map<VIEW_ENUM, String> params = new HashMap<VIEW_ENUM, String>();
        params.put(VIEW_ENUM.PRODUCT_NAME, productNameEditText.getText().toString());
        params.put(VIEW_ENUM.BRAND_NAME, brandNameEditText.getText().toString());
        params.put(VIEW_ENUM.OUNCES_OR_COUNT, ouncesOrCountEditText.getText().toString());
        params.put(VIEW_ENUM.SIZE_DESCRIPTION, sizeDescriptionEditText.getText().toString());
        params.put(VIEW_ENUM.STORE_NAME, storeNameEditText.getText().toString());
        params.put(VIEW_ENUM.ZIP_CODE, zipCodeEditText.getText().toString());
        params.put(VIEW_ENUM.GENERAL_PRICE, generalPriceEditText.getText().toString());

        if (valuesValid(params))
        {
            new PriceContributorTask(this).execute(params);
        }
    }

    private boolean valuesValid(Map<VIEW_ENUM, String> params)
    {
        final String EMPTY = "";
        for (String value : params.values())
        {
            if (value == null || value.equals(EMPTY))
            {
                return false;
            }
        }
        return true;
    }

    class PriceContributorTask extends AsyncTask<Map<VIEW_ENUM, String>, String, Boolean>
    {
        PriceContributionHandler handler;
        private ProgressDialog progressDialog;

        public PriceContributorTask(PriceContributionHandler handler)
        {
            this.handler = handler;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PriceContributorActivity.this);
            progressDialog.setMessage("Contributing price...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Map<VIEW_ENUM, String>... args)
        {
            if (args.length == 1)
            {
                String productName = args[0].get(VIEW_ENUM.PRODUCT_NAME);
                String brandName = args[0].get(VIEW_ENUM.BRAND_NAME);
                String sizeDescription = args[0].get(VIEW_ENUM.SIZE_DESCRIPTION);
                String ouncesOrCount = args[0].get(VIEW_ENUM.OUNCES_OR_COUNT);
                String storeName = args[0].get(VIEW_ENUM.STORE_NAME);
                String zipCode = args[0].get(VIEW_ENUM.ZIP_CODE);
                String generalPrice = args[0].get(VIEW_ENUM.GENERAL_PRICE);

                PriceTableDataSource ds = new PriceTableDataSource();
                return ds.insertPrice(productName, brandName, sizeDescription, ouncesOrCount, storeName, zipCode, generalPrice);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Boolean success)
        {
            progressDialog.dismiss();
            handler.handlePriceContribution(success);
        }
    }
}
