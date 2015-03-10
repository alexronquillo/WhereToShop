package com.wheretoshop.model;

import android.os.AsyncTask;

import java.util.Set;

public abstract class ProductTableSingleColumnGetTask extends AsyncTask<String, String, Set<String>>
{
	private ModifiedProductHandler handler;

	public ProductTableSingleColumnGetTask(ModifiedProductHandler handler)
	{
		this.handler = handler;
	}

	protected ModifiedProductHandler getHandler()
	{
		return handler;
	}

	@Override
	protected Set<String> doInBackground(String... params)
	{
		return getColumn(params);
	}


	protected abstract Set<String> getColumn(String... args);
}
