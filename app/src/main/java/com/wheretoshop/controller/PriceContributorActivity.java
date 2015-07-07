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

import com.wheretoshop.R;
import com.wheretoshop.model.PriceContributionHandler;
import com.wheretoshop.model.Product;
import com.wheretoshop.model.utilities.PriceTableDataSource;

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
        String productName = productNameEditText.getText().toString();
        String brandName = brandNameEditText.getText().toString();
        String sizeDescription = sizeDescriptionEditText.getText().toString();
        String ouncesOrCount = ouncesOrCountEditText.getText().toString();
        String storeName = storeNameEditText.getText().toString();
        String zipCode = zipCodeEditText.getText().toString();
        String generalPrice = generalPriceEditText.getText().toString();

        if (valuesValid(productName, brandName, sizeDescription, ouncesOrCount, storeName, zipCode, generalPrice))
        {
            new PriceContributorTask(this).execute(productName, brandName, sizeDescription, ouncesOrCount, storeName, zipCode, generalPrice);
        }
    }

    private boolean valuesValid(String... values)
    {
        final String EMPTY = "";
        for (String value : values)
        {
            if (value == null || value.equals(EMPTY))
            {
                return false;
            }
        }
        return true;
    }

    class PriceContributorTask extends AsyncTask<String, String, Boolean>
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
        protected Boolean doInBackground(String... args)
        {
            if (args.length == 7)
            {
                // Todo: make sure these match up
                String productName = args[0];
                String brandName = args[1];
                String sizeDescription = args[2];
                String ouncesOrCount = args[3];
                String storeName = args[4];
                String zipCode = args[5];
                String generalPrice = args[6];

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
