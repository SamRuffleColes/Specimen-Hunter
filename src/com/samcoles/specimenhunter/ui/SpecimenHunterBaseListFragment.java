package com.samcoles.specimenhunter.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.actionbarsherlock.app.SherlockListFragment;
import com.samcoles.specimenhunter.SpecimenHunterApplication;
import com.samcoles.specimenhunter.provider.SpecimenHunterDatabaseAdapter;
import com.samcoles.specimenhunter.utils.SpecimenHunterPreferences;

public abstract class SpecimenHunterBaseListFragment extends SherlockListFragment {
	
	@Override
	public void onResume() {
		super.onResume();		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);		
//		setListSortMethod(SpecimenHunterPreferences.getSortMethod());
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
