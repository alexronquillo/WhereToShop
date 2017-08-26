package com.wheretoshop.controller;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wheretoshop.R;
import com.wheretoshop.model.Product;
import com.wheretoshop.model.ProductSearchHandler;
import com.wheretoshop.model.adapters.ProductArrayAdapter;
import com.wheretoshop.model.utilities.ProductTableDataSource;

import java.util.ArrayList;
import java.util.List;

public class ProductSearchActivity extends ActionBarActivity implements ProductSearchHandler
{
    private static final String LOG_TAG = "ProductSearchActivity";
    public static final String PRODUCT_EXTRA = "ProductExtra";
    private ListView productListView;
    private ProductArrayAdapter productAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        productListView = (ListView)findViewById(R.id.list_view);
        productAdapter = new ProductArrayAdapter(this, new ArrayList<Product>());
        productListView.setAdapter(productAdapter);
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try
                {
                    Product product = (Product) parent.getItemAtPosition(position);
                    Intent intent = new Intent(ProductSearchActivity.this, ViewProductActivity.class);
                    intent.putExtra(PRODUCT_EXTRA, product);
                    startActivity(intent);
                }
                catch (ClassCastException e)
                {
                    Log.e(LOG_TAG, "ClassCastException: " + e.getMessage());
                }
            }
        });
        handleSearchIntent(getIntent());
    }

    @Override
    public void onNewIntent(Intent intent)
    {
        handleSearchIntent(intent);
    }

    private void handleSearchIntent(Intent intent)
    {
        if (Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String query = intent.getStringExtra(SearchManager.QUERY);
            new ProductSearchTask(this).execute(query);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        return true;
    }

    @Override
    public void handleProductSearchResult(List<Product> products)
    {
        if (productAdapter != null)
        {
            if (products != null)
            {
                productAdapter.clear();
                productAdapter.addAll(products);
                productAdapter.notifyDataSetChanged();
            }
            else
            {
                Log.e(LOG_TAG, "No products from search results.");
            }
        }
        else
        {
            Log.e(LOG_TAG, "Error: productAdapter is null in handleProductSearchResult(List)");
        }
    }

    class ProductSearchTask extends AsyncTask<String, String, List<Product>>
    {
        private ProductSearchHandler handler;
        private ProgressDialog progressDialog;

        public ProductSearchTask(ProductSearchHandler handler)
        {
            this.handler = handler;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ProductSearchActivity.this);
            progressDialog.setMessage("Searching for products...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected List<Product> doInBackground(String... args)
        {
            if (args.length == 1)
            {
                ProductTableDataSource ds = new ProductTableDataSource();
                return ds.getProductsByProductOrBrandName(args[0]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Product> products)
        {
            if (handler != null)
            {
                handler.handleProductSearchResult(products);
            }
            progressDialog.dismiss();
        }
    }
}
