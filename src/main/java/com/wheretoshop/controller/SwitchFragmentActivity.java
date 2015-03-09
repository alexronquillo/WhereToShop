package com.wheretoshop.controller;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import junit.framework.Assert;

import com.wheretoshop.R;
import com.wheretoshop.model.FragmentSwitcher;

public abstract class SwitchFragmentActivity extends ActionBarActivity implements FragmentSwitcher
{
	private static final String SWITCH_FRAGMENT_ACTIVITY_LOG_TAG = "SWITCH_FRAGMENT_ACTIVITY_LOG_TAG";
	private Switch searchSwitch;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.switch_fragment_activity);

		searchSwitch = (Switch)findViewById(R.id.search_switch);
		searchSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				changeFragment(isChecked);
			}
		});

		FragmentManager fragmentManager = getSupportFragmentManager();
		Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

		if(fragment == null)
		{
			fragment = getFragment(searchSwitch.isChecked());
			fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
		}
	}

	private void changeFragment(boolean switchIsChecked) 
	{ 
		Fragment fragment = getFragment(switchIsChecked);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_container, fragment).commit();
	}

	public void switchFragment()
	{
		if(searchSwitch != null)
		{
			searchSwitch.toggle();
		}
	}

	protected abstract Fragment getFragment(boolean switchIsChecked);
}
