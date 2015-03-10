package com.wheretoshop.controller;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.InflateException;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.R.layout;

import java.util.Set;

import com.wheretoshop.R;
import com.wheretoshop.controller.GroceryListActivity;
import com.wheretoshop.model.Product;
import com.wheretoshop.model.ProductTableDataSource;
import com.wheretoshop.model.ProductTableSingleColumnGetTask;
import com.wheretoshop.model.ModifiedProductHandler;

public class GroceryListModifyFragment extends Fragment implements ModifiedProductHandler
{
	private ArrayAdapter<String> brandNameAdapter;
	private ArrayAdapter<String> sizeDescriptionAdapter;
	private ArrayAdapter<String> ouncesOrCountAdapter;
	private static final String LOG_TAG = "GROCERY_LIST_MODIFY_FRAGMENT_LOG_TAG";
	private Product product;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Bundle args = getArguments();
		Product product;
		if(args != null && (product = (Product)args.getSerializable(GroceryListModifyActivity.PRODUCT_ARG_KEY)) != null)
			this.product = product;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View inflatedView = null;
		try
		{
			inflatedView = inflater.inflate(R.layout.grocery_list_modify_fragment, container, false);

			brandNameAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
			sizeDescriptionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
			ouncesOrCountAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);

			EditText productNameEditText = (EditText)inflatedView.findViewById(R.id.product_name_edittext);

			Spinner brandNameSpinner = (Spinner)inflatedView.findViewById(R.id.brand_name_spinner);
			brandNameSpinner.setAdapter(brandNameAdapter);

			Spinner sizeDescriptionSpinner = (Spinner)inflatedView.findViewById(R.id.size_description_spinner);
			sizeDescriptionSpinner.setAdapter(sizeDescriptionAdapter);

			Spinner ouncesOrCountSpinner = (Spinner)inflatedView.findViewById(R.id.ounces_or_count_spinner);
			ouncesOrCountSpinner.setAdapter(ouncesOrCountAdapter);

			if(product != null)
			{
				productNameEditText.setText(product.getProductName());
				executeBrandNameGetTask();
				executeSizeDescriptionGetTask();
				executeOuncesOrCountGetTask();
			}
		}
		catch(InflateException e)
		{
			Log.e(LOG_TAG, e.getMessage());
		}

		return inflatedView;
	}

	private void executeBrandNameGetTask()
	{
		try
		{
			new BrandNameGetTask(this).execute();
		}
		catch(Exception e)
		{
			Log.e(LOG_TAG, "Error: " + e.getMessage());
		}
	}

	private void executeSizeDescriptionGetTask()
	{
		try
		{
			new SizeDescriptionGetTask(this).execute();
		}
		catch(Exception e)
		{
			Log.e(LOG_TAG, "Error: " + e.getMessage());
		}
	}

	private void executeOuncesOrCountGetTask()
	{
		try
		{
			new OuncesOrCountGetTask(this).execute();
		}
		catch(Exception e)
		{
			Log.e(LOG_TAG, "Error: " + e.getMessage());
		}
	}

	private void refreshBrandNameSpinner(Set<String> brandNames) 
	{
	}

	private void refreshSizeDescriptionSpinner(Set<String> sizeDescriptions) 
	{
	}

	private void refreshOuncesOrCount(Set<String> ouncesOrCounts) 
	{
	}

	@Override
	public void handleModifiedProduct(PRODUCT_COLUMN_TYPE type, Set<String> column)
	{
		if(column != null)
		{
			switch(type)
			{
				case BRAND_NAME:
					refreshBrandNameSpinner(column);			
					break;

				case SIZE_DESCRIPTION:
					// Todo
					break;

				case OUNCES_OR_COUNT:
					// Todo
					break;

				default:
					Log.e(LOG_TAG, "Error: Invalid column type");
			}
		}
		else
		{
			Log.e(LOG_TAG, "Error: column is null from ProductTableSingleColumnGetTask");
		}
	}
	
	class BrandNameGetTask extends ProductTableSingleColumnGetTask
	{
		public BrandNameGetTask(ModifiedProductHandler handler)
		{
			super(handler);
		}

		@Override
		protected Set<String> getColumn(String... args)
		{
			ProductTableDataSource ds = new ProductTableDataSource();
			String productName = args[0];

			Set<String> result = null;
			if(productName != null)
				result = ds.getBrandNamesByProductName(productName);
			return result;
		}

		@Override
		protected void onPostExecute(Set<String> result)
		{
			getHandler().handleModifiedProduct(PRODUCT_COLUMN_TYPE.BRAND_NAME, result);
		}
	}

	class SizeDescriptionGetTask extends ProductTableSingleColumnGetTask
	{
		public SizeDescriptionGetTask(ModifiedProductHandler handler)
		{
			super(handler);
		}

		@Override
		protected Set<String> getColumn(String... args)
		{
			ProductTableDataSource ds = new ProductTableDataSource();
			String productName = args[0], brandName = args[1];
			
			Set<String> result = null;
			if(productName != null && brandName != null)
				result = ds.getSizeDescriptionsByProductNameAndBrandName(productName, brandName);

			return result;
		}

		@Override
		protected void onPostExecute(Set<String> result)
		{
			getHandler().handleModifiedProduct(PRODUCT_COLUMN_TYPE.SIZE_DESCRIPTION, result);
		}
	}
	
	class OuncesOrCountGetTask extends ProductTableSingleColumnGetTask 
	{
		public OuncesOrCountGetTask(ModifiedProductHandler handler)
		{
			super(handler);
		}

		@Override
		protected Set<String> getColumn(String... args)
		{
			ProductTableDataSource ds = new ProductTableDataSource();
			String productName = args[0], brandName = args[1], sizeDescription = args[2];

			Set<String> result = null;
			if(productName != null && brandName != null && sizeDescription != null)
				result = ds.getOuncesOrCountByProductNameBrandNameAndSizeDescription(productName, brandName, sizeDescription);

			return result;
		}

		@Override
		protected void onPostExecute(Set<String> result)
		{
			getHandler().handleModifiedProduct(PRODUCT_COLUMN_TYPE.OUNCES_OR_COUNT, result);
		}
	}
}
