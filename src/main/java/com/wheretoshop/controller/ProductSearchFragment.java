package com.wheretoshop.controller;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.InflateException;
import android.widget.SearchView;
import android.widget.ListView;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.app.ProgressDialog;
import android.content.Context;

import java.util.List;
import java.util.ArrayList;
import junit.framework.Assert;

import com.wheretoshop.R;
import com.wheretoshop.model.SearchListArrayAdapter;
import com.wheretoshop.model.ProductTableDataSource;
import com.wheretoshop.model.Product;
import com.wheretoshop.model.SearchTaskHandler;

public class ProductSearchFragment extends Fragment implements SearchTaskHandler
{
	protected static final String LOG_TAG = "SEARCH_FRAGMENT_LOG_TAG";
	private SearchListArrayAdapter searchResultsAdapter; 
	private SearchView searchView;
	private ListView searchResultsListView;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		searchResultsAdapter = new SearchListArrayAdapter(getActivity(), new ArrayList<Product>());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View inflatedView = null;
		try
		{
			inflatedView = inflater.inflate(R.layout.search_fragment, container, false); 

			searchResultsListView = (ListView)inflatedView.findViewById(R.id.search_results_listview);
			searchResultsListView.setAdapter(searchResultsAdapter);

			searchView = (SearchView)inflatedView.findViewById(R.id.search_view);
			searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
				@Override
				public boolean onQueryTextChange(String newText) { return executeSearchTask(newText); }

				@Override
				public boolean onQueryTextSubmit(String query) { return executeSearchTask(query); }
			});
			
		}
		catch(InflateException e)
		{
			Log.e(LOG_TAG, e.getMessage());
		}

		return inflatedView;
	}

	private boolean executeSearchTask(String query)
	{
		try
		{
			new SearchTask(ProductSearchFragment.this).execute(query);
		}
		catch(Exception e)
		{
			Log.e(LOG_TAG, e.getMessage());
			return false;
		}

		return true;
	}

	public void replaceSearchList(List<Product> products)
	{
		Assert.assertNotNull(searchResultsAdapter);

		if(products != null)
		{
			searchResultsAdapter.replace(products);
			searchResultsAdapter.notifyDataSetChanged();
		}
	}

	@Override	
	public void handleSearchTask(List<Product> products) { replaceSearchList(products); }

	class SearchTask extends AsyncTask<String, String, List<Product>>
	{
		private List<Product> products;
		private SearchTaskHandler handler;

		public SearchTask(SearchTaskHandler handler) { this.handler = handler; }

		@Override
		protected List<Product> doInBackground(String... args)
		{
			ProductTableDataSource ds = new ProductTableDataSource();
			return ds.getProductsByProductOrBrandName(args[0]);
		}

		@Override
		protected void onPostExecute(List<Product> products)
		{
			handler.handleSearchTask(products);
		}
	}
}
