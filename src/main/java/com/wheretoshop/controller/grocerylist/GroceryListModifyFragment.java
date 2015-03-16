package com.wheretoshop.controller;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.InflateException;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.R.layout;
import android.text.TextWatcher;
import android.text.Editable;
import android.content.Intent;

import java.util.Set;
import java.math.BigDecimal;

import com.wheretoshop.R;
import com.wheretoshop.controller.GroceryListActivity;
import com.wheretoshop.model.Product;
import com.wheretoshop.model.ProductTableDataSource;
import com.wheretoshop.model.ProductTableSingleColumnGetTask;
import com.wheretoshop.model.ModifiedProductHandler;
import com.wheretoshop.model.GroceryList;
import com.wheretoshop.model.GroceryListProduct;

public class GroceryListModifyFragment extends Fragment implements ModifiedProductHandler
{
	private static final String LOG_TAG = "GROCERY_LIST_MODIFY_FRAGMENT_LOG_TAG";
	private EditText productNameEditText;
	private Spinner brandNameSpinner;
	private Spinner sizeDescriptionSpinner;
	private Spinner ouncesOrCountSpinner;
	private Button submitButton;
	private ArrayAdapter<String> brandNameAdapter;
	private ArrayAdapter<String> sizeDescriptionAdapter;
	private ArrayAdapter<String> ouncesOrCountAdapter;
	private Product product;
	private GroceryList grocerylist;
	private boolean addProduct;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle args = getArguments();
		if(args != null) {
			Product product;
			if(args != null) {
				if((product = (Product)args.getSerializable(GroceryListModifyActivity.PRODUCT_ARG_KEY)) != null)
					this.product = product;

				addProduct = args.getBoolean(GroceryListActivity.ADD_MODIFY_FLAG_KEY);
			}
		}

		grocerylist = GroceryList.getInstance(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View inflatedView = null;
		try {
			inflatedView = inflater.inflate(R.layout.grocery_list_modify_fragment, container, false);

			brandNameAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
			sizeDescriptionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
			ouncesOrCountAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);

			productNameEditText = (EditText)inflatedView.findViewById(R.id.product_name_edittext);
			productNameEditText.addTextChangedListener(new TextWatcher() {
				@Override
				public void afterTextChanged(Editable s) { /* Do nothing */ }

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Do nothing */ }

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					product.setProductName(s.toString());
					executeBrandNameGetTask();
				}
			});

			brandNameSpinner = (Spinner)inflatedView.findViewById(R.id.brand_name_spinner);
			brandNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					product.setBrandName((String)((Spinner)parent).getSelectedItem());
					executeSizeDescriptionGetTask();
				}

				@Override
				public void onNothingSelected(AdapterView parent) { /* Do nothing */ }
			});
			brandNameSpinner.setAdapter(brandNameAdapter);

			sizeDescriptionSpinner = (Spinner)inflatedView.findViewById(R.id.size_description_spinner);
			sizeDescriptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					product.setSizeDescription((String)((Spinner)parent).getSelectedItem());
					executeOuncesOrCountGetTask();
				}

				@Override
				public void onNothingSelected(AdapterView parent) { /* Do nothing */ }
			});
			sizeDescriptionSpinner.setAdapter(sizeDescriptionAdapter);

			ouncesOrCountSpinner = (Spinner)inflatedView.findViewById(R.id.ounces_or_count_spinner);
			ouncesOrCountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					product.setOuncesOrCount(new BigDecimal((String)((Spinner)parent).getSelectedItem()));
				}

				@Override
				public void onNothingSelected(AdapterView parent) { /* Do nothing */ }
			});
			ouncesOrCountSpinner.setAdapter(ouncesOrCountAdapter);

			if(product != null) {
				productNameEditText.setText(product.getProductName());
				productNameEditText.setEnabled(false);
			}

			submitButton = (Button)inflatedView.findViewById(R.id.submit_button);
			submitButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(addProduct) {
						grocerylist.add(new GroceryListProduct(product, 1));	
						Intent intent = new Intent(getActivity(), GroceryListActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
					} else {
						// modify product
					}
				}
			});
		} catch(InflateException e) {
			Log.e(LOG_TAG, "Error: " + e.getMessage());
		}

		return inflatedView;
	}

	private void executeBrandNameGetTask() {
		try {
			if(productNameEditText != null) {
				String productName = productNameEditText.getText().toString();
				new BrandNameGetTask(this).execute(productName);
			}
		} catch(Exception e) {
			Log.e(LOG_TAG, "Error: " + e.getMessage());
		}
	}

	private void executeSizeDescriptionGetTask() {
		try {
			if(productNameEditText != null && brandNameSpinner != null) {
				String productName = productNameEditText.getText().toString();
				String brandName = brandNameSpinner.getSelectedItem().toString();
				new SizeDescriptionGetTask(this).execute(productName, brandName);
			}
		} catch(Exception e) {
			Log.e(LOG_TAG, "Error: " + e.getMessage());
		}
	}

	private void executeOuncesOrCountGetTask() {
		try {
			if(productNameEditText != null && brandNameSpinner != null && sizeDescriptionSpinner != null) {
				String productName = productNameEditText.getText().toString();
				String brandName = brandNameSpinner.getSelectedItem().toString();
				String sizeDescription = sizeDescriptionSpinner.getSelectedItem().toString();
				new OuncesOrCountGetTask(this).execute(productName, brandName, sizeDescription);
			}
		} catch(Exception e) {
			Log.e(LOG_TAG, "Error: " + e.getMessage());
		}
	}

	private void refreshBrandNameSpinner(Set<String> brandNames) {
		try {
			brandNameAdapter.clear();
			brandNameAdapter.addAll(brandNames);
			brandNameAdapter.notifyDataSetChanged();
		} catch(Exception e) {
			Log.e(LOG_TAG, "Error: " + e.getMessage());
		}
	}

	private void refreshSizeDescriptionSpinner(Set<String> sizeDescriptions) {
		try {
			sizeDescriptionAdapter.clear();
			sizeDescriptionAdapter.addAll(sizeDescriptions);
			sizeDescriptionAdapter.notifyDataSetChanged();
		} catch(Exception e) {
			Log.e(LOG_TAG, "Error: " + e.getMessage());
		}

	}

	private void refreshOuncesOrCount(Set<String> ouncesOrCounts) {
		try {
			ouncesOrCountAdapter.clear();
			ouncesOrCountAdapter.addAll(ouncesOrCounts);
			ouncesOrCountAdapter.notifyDataSetChanged();
		} catch(Exception e) {
			Log.e(LOG_TAG, "Error: " + e.getMessage());
		}

	}

	@Override
	public void handleModifiedProduct(PRODUCT_COLUMN_TYPE type, Set<String> column) {
		if(column != null) {
			switch(type) {
				case BRAND_NAME:
					refreshBrandNameSpinner(column);			
					break;

				case SIZE_DESCRIPTION:
					refreshSizeDescriptionSpinner(column);
					break;

				case OUNCES_OR_COUNT:
					refreshOuncesOrCount(column);
					break;

				default:
					Log.e(LOG_TAG, "Error: Invalid column type");
			}
		} else {
			Log.e(LOG_TAG, "Error: column is null from ProductTableSingleColumnGetTask");
		}
	}
	
	class BrandNameGetTask extends ProductTableSingleColumnGetTask {
		public BrandNameGetTask(ModifiedProductHandler handler) { super(handler); }

		@Override
		protected Set<String> getColumn(String... args) {
			ProductTableDataSource ds = new ProductTableDataSource();
			String productName = args[0];

			Set<String> result = null;
			if(productName != null)
				result = ds.getBrandNamesByProductName(productName);

			return result;
		}

		@Override
		protected void onPostExecute(Set<String> result) {
			getHandler().handleModifiedProduct(PRODUCT_COLUMN_TYPE.BRAND_NAME, result);
		}
	}

	class SizeDescriptionGetTask extends ProductTableSingleColumnGetTask {
		public SizeDescriptionGetTask(ModifiedProductHandler handler) { super(handler); }

		@Override
		protected Set<String> getColumn(String... args) {
			ProductTableDataSource ds = new ProductTableDataSource();
			String productName = args[0], brandName = args[1];
			
			Set<String> result = null;
			if(productName != null && brandName != null)
				result = ds.getSizeDescriptionsByProductNameAndBrandName(productName, brandName);

			return result;
		}

		@Override
		protected void onPostExecute(Set<String> result) {
			getHandler().handleModifiedProduct(PRODUCT_COLUMN_TYPE.SIZE_DESCRIPTION, result);
		}
	}
	
	class OuncesOrCountGetTask extends ProductTableSingleColumnGetTask {
		public OuncesOrCountGetTask(ModifiedProductHandler handler) { super(handler); }

		@Override
		protected Set<String> getColumn(String... args) {
			ProductTableDataSource ds = new ProductTableDataSource();
			String productName = args[0], brandName = args[1], sizeDescription = args[2];

			Set<String> result = null;
			if(productName != null && brandName != null && sizeDescription != null)
				result = ds.getOuncesOrCountByProductNameBrandNameAndSizeDescription(productName, brandName, sizeDescription);

			return result;
		}

		@Override
		protected void onPostExecute(Set<String> result) {
			getHandler().handleModifiedProduct(PRODUCT_COLUMN_TYPE.OUNCES_OR_COUNT, result);
		}
	}
}
