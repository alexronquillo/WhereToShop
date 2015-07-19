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
import android.widget.Toast;

import java.util.Map;
import java.util.HashMap;

import com.wheretoshop.R;
import com.wheretoshop.model.PriceContributionHandler;
import com.wheretoshop.model.Product;
import com.wheretoshop.model.utilities.PriceTableDataSource;

public class PriceContributorActivity extends ActionBarActivity implements PriceContributionHandler
{
    public static final String PRODUCT_EXTRA = "PRODUCT_EXTRA";
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
        if (success)
        {
            productNameEditText.setText("");
            brandNameEditText.setText("");
            sizeDescriptionEditText.setText("");
            ouncesOrCountEditText.setText("");
            Toast.makeText(this, "Price contribution was successful.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Price contribution was unsuccessful.", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertPrice()
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put(PriceTableDataSource.PRODUCT_NAME_KEY, productNameEditText.getText().toString());
        params.put(PriceTableDataSource.BRAND_NAME_KEY, brandNameEditText.getText().toString());
        params.put(PriceTableDataSource.OUNCES_OR_COUNT_KEY, ouncesOrCountEditText.getText().toString());
        params.put(PriceTableDataSource.SIZE_DESCRIPTION_KEY, sizeDescriptionEditText.getText().toString());
        params.put(PriceTableDataSource.STORE_NAME_KEY, storeNameEditText.getText().toString());
        params.put(PriceTableDataSource.ZIP_CODE_KEY, zipCodeEditText.getText().toString());
        params.put(PriceTableDataSource.GENERAL_PRICE_KEY, generalPriceEditText.getText().toString());

        if (valuesValid(params))
        {
            new PriceContributorTask(this).execute(params);
        }
    }

    private boolean valuesValid(Map<String, String> params)
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

    class PriceContributorTask extends AsyncTask<Map<String, String>, String, Boolean>
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
        protected Boolean doInBackground(Map<String, String>... args)
        {
            if (args.length == 1)
            {
                PriceTableDataSource ds = new PriceTableDataSource();
                return ds.insertPrice(args[0]);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success)
        {
            progressDialog.dismiss();
            handler.handlePriceContribution(success);
        }
    }
}
