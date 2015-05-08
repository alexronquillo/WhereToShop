package com.wheretoshop.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.wheretoshop.R;
import com.wheretoshop.model.WTSHandler;
import com.wheretoshop.model.WTSProduct;
import com.wheretoshop.model.adapters.WhereToShopAdapter;
import com.wheretoshop.model.utilities.PriceTableDataSource;

import java.util.ArrayList;
import java.util.List;

public class WhereToShopActivity extends ActionBarActivity implements WTSHandler
{
    private ListView wtsListView;
    private List<WTSProduct> wtsList;
    private WhereToShopAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        wtsList = new ArrayList<>();
        wtsListView = (ListView) findViewById(R.id.list_view);
        adapter = new WhereToShopAdapter(this, wtsList);
        wtsListView.setAdapter(adapter);
        refreshWhereToShopList();
    }

    @Override
    public void handleWTS(List<WTSProduct> wtsList)
    {
        adapter.replace(wtsList);
    }

    private void refreshWhereToShopList()
    {
        new WhereToShopTask(this).execute();
    }

    class WhereToShopTask extends AsyncTask<String, String, List<WTSProduct>>
    {
        private WTSHandler handler;
        private ProgressDialog progressDialog;

        public WhereToShopTask(WTSHandler handler)
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
        protected List<WTSProduct> doInBackground(String... args)
        {
            PriceTableDataSource ds = new PriceTableDataSource();
            return ds.getBestPrice();
        }

        @Override
        protected void onPostExecute(List<WTSProduct> wtsList)
        {
            progressDialog.dismiss();
            handler.handleWTS(wtsList);
        }
    }
}
