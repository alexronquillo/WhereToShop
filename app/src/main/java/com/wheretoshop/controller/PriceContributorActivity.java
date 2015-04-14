package com.wheretoshop.controller;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wheretoshop.R;
import com.wheretoshop.model.GetProductIdHandler;
import com.wheretoshop.model.GetStoreIdHandler;
import com.wheretoshop.model.PostProductHandler;
import com.wheretoshop.model.PostStoreHandler;
import com.wheretoshop.model.Product;
import com.wheretoshop.model.utilities.ProductTableDataSource;
import com.wheretoshop.model.utilities.StoreTableDataSource;

public class PriceContributorActivity extends ActionBarActivity implements GetStoreIdHandler, GetProductIdHandler, PostStoreHandler, PostProductHandler
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

    private Integer storeId;
    private Integer productId;

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
                confirmStore();
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

    private void confirmStore()
    {
        if
        (
            storeNameEditText != null && !storeNameEditText.getText().toString().equals("") &&
            zipCodeEditText != null && !zipCodeEditText.getText().toString().equals("")
        )
        {
            new GetStoreIdTask(this).execute(storeNameEditText.getText().toString(), zipCodeEditText.getText().toString());
        }
        else
        {
            Toast.makeText(this, "Missing required text fields", Toast.LENGTH_LONG).show();
        }
    }

    private void confirmProduct()
    {
        if
        (
            productNameEditText != null && !productNameEditText.getText().toString().equals("") &&
            brandNameEditText != null && !brandNameEditText.getText().toString().equals("") &&
            sizeDescriptionEditText != null && !sizeDescriptionEditText.getText().toString().equals("") &&
            ouncesOrCountEditText != null && !ouncesOrCountEditText.getText().toString().equals("")
        )
        {
            new GetProductIdTask(this).execute(productNameEditText.getText().toString(),
                    brandNameEditText.getText().toString(),
                    sizeDescriptionEditText.getText().toString(),
                    ouncesOrCountEditText.getText().toString());
        }
        else
        {
            Toast.makeText(this, "Missing required text fields", Toast.LENGTH_LONG).show();
        }
    }

    private void postStore()
    {
        if
        (
            storeNameEditText != null && !storeNameEditText.getText().toString().equals("") &&
            zipCodeEditText != null && !zipCodeEditText.getText().toString().equals("")
        )
        {
            new PostStoreTask(this).execute(storeNameEditText.getText().toString(), zipCodeEditText.getText().toString());
        }
    }

    private void postProduct()
    {
        if
        (
            productNameEditText != null && !productNameEditText.getText().toString().equals("") &&
            brandNameEditText != null && !brandNameEditText.getText().toString().equals("") &&
            sizeDescriptionEditText != null && !sizeDescriptionEditText.getText().toString().equals("") &&
            ouncesOrCountEditText != null && !ouncesOrCountEditText.getText().toString().equals("")
        )
        {
            new PostProductTask(this).execute(productNameEditText.getText().toString(),
                    brandNameEditText.getText().toString(),
                    sizeDescriptionEditText.getText().toString(),
                    ouncesOrCountEditText.getText().toString());
        }
    }

    @Override
    public void handleGetStoreId(int storeId)
    {
        Log.i("testing", "Store Id: " + storeId);
        if (storeId == -1)
        {
            postStore();
        }
        else
        {
            confirmProduct();
        }
    }

    @Override
    public void handleGetProductId(int productId)
    {
        Log.i("testing", "Product Id: " + productId);
        if (productId == -1)
        {
            postProduct();
        }
        else
        {
            this.productId = productId;
        }
    }

    @Override
    public void handlePostStore(int storeId)
    {
        Log.i("testing", "Store Id: " + storeId);
        this.storeId = storeId;
        confirmProduct();
    }

    @Override
    public void handlePostProduct(int productId)
    {
        Log.i("testing", "Product Id: " + productId);
        this.productId = productId;
    }

    class GetStoreIdTask extends AsyncTask<String, String, Integer>
    {
        GetStoreIdHandler handler;
        private ProgressDialog progressDialog;

        public GetStoreIdTask(GetStoreIdHandler handler)
        {
            this.handler = handler;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PriceContributorActivity.this);
            progressDialog.setMessage("Searching for store id...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(String... args)
        {
            if (args.length == 2)
            {
                StoreTableDataSource ds = new StoreTableDataSource();
                return ds.getStoreId(args[0], args[1]);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer storeId)
        {
            progressDialog.dismiss();
            handler.handleGetStoreId(storeId);
        }
    }

    class GetProductIdTask extends AsyncTask<String, String, Integer>
    {
        GetProductIdHandler handler;
        private ProgressDialog progressDialog;

        public GetProductIdTask(GetProductIdHandler handler)
        {
            this.handler = handler;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PriceContributorActivity.this);
            progressDialog.setMessage("Searching for product id...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(String... args)
        {
            if (args.length == 4)
            {
                ProductTableDataSource ds = new ProductTableDataSource();
                return ds.getProductId(args[0], args[1], args[2], args[3]);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer productId)
        {
            progressDialog.dismiss();
            handler.handleGetProductId(productId);
        }
    }

    class PostStoreTask extends AsyncTask<String, String, Integer>
    {
        PostStoreHandler handler;
        private ProgressDialog progressDialog;

        public PostStoreTask(PostStoreHandler handler)
        {
            this.handler = handler;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PriceContributorActivity.this);
            progressDialog.setMessage("Creating new store...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(String... args)
        {
            if (args.length == 2)
            {
                StoreTableDataSource ds = new StoreTableDataSource();
                return ds.postStore(args[0], args[1]);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer storeId)
        {
            progressDialog.dismiss();
            handler.handlePostStore(storeId);
        }
    }

    class PostProductTask extends AsyncTask<String, String, Integer>
    {
        PostProductHandler handler;
        private ProgressDialog progressDialog;

        public PostProductTask(PostProductHandler handler)
        {
            this.handler = handler;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PriceContributorActivity.this);
            progressDialog.setMessage("Creating new product...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(String... args)
        {
            if (args.length == 4)
            {
                ProductTableDataSource ds = new ProductTableDataSource();
                return ds.postProduct(args[0], args[1], args[2], args[3]);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer productId)
        {
            progressDialog.dismiss();
            handler.handlePostProduct(productId);
        }
    }
}
