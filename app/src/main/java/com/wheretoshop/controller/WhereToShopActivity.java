package com.wheretoshop.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.wheretoshop.R;
import com.wheretoshop.model.WhereToShopProduct;
import com.wheretoshop.model.WhereToShopResponseHandler;
import com.wheretoshop.model.adapters.WhereToShopAdapter;
import com.wheretoshop.model.utilities.PriceTableDataSource;

import java.util.ArrayList;
import java.util.List;

public class WhereToShopActivity extends ActionBarActivity implements WhereToShopResponseHandler
{
    private ListView whereToShopListView;
    private List<WhereToShopProduct> whereToShopList;
    private WhereToShopAdapter whereToShopAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        whereToShopList = new ArrayList<>();
        whereToShopListView = (ListView) findViewById(R.id.list_view);
        whereToShopAdapter = new WhereToShopAdapter(this, whereToShopList);
        whereToShopListView.setAdapter(whereToShopAdapter);
        refreshWhereToShopList();
    }

    @Override
    public void handleWhereToShopResponse(List<WhereToShopProduct> whereToShopList)
    {
        whereToShopAdapter.replace(whereToShopList);
    }

    private void refreshWhereToShopList()
    {
        new WhereToShopTask(this).execute();
    }

    class WhereToShopTask extends AsyncTask<String, String, List<WhereToShopProduct>>
    {
        private WhereToShopResponseHandler handler;
        private ProgressDialog progressDialog;

        public WhereToShopTask(WhereToShopResponseHandler handler)
        {
            this.handler = handler;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = new ProgressDialog(WhereToShopActivity.this);
            progressDialog.setMessage("Refreshing WhereToShop list...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected List<WhereToShopProduct> doInBackground(String... args)
        {
            PriceTableDataSource ds = new PriceTableDataSource();
            return ds.getBestPrice();
        }

        @Override
        protected void onPostExecute(List<WhereToShopProduct> whereToShopList)
        {
            progressDialog.dismiss();
            handler.handleWhereToShopResponse(whereToShopList);
        }
    }
}
