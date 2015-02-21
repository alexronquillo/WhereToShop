package com.wheretoshop.controller;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.InflateException;
import android.os.Bundle;
import android.util.Log;

import com.wheretoshop.R;

public class SearchFragment extends Fragment
{
	protected static final String SEARCH_FRAGMENT_LOG_TAG = "SEARCH_FRAGMENT_LOG_TAG";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View inflatedView = null;
		try
		{
			inflatedView = inflater.inflate(R.layout.search_fragment, container, false); 
		}
		catch(InflateException e)
		{
			Log.e(SEARCH_FRAGMENT_LOG_TAG, e.getMessage());
		}

		return inflatedView;
	}
}
