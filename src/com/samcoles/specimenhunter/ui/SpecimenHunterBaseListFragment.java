package com.samcoles.specimenhunter.ui;

import android.content.Context;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockListFragment;
import com.samcoles.specimenhunter.provider.SpecimenHunterDatabaseAdapter;

public abstract class SpecimenHunterBaseListFragment extends SherlockListFragment {
	
	private SpecimenHunterDatabaseAdapter mDbHelper;
	private Context mContext;
	private int mSortMethod;

	public int getSortMethod() {
		return mSortMethod;
	}

	public void setSortMethod(int sortMethod) {
		mSortMethod = sortMethod;
	}

	public SpecimenHunterDatabaseAdapter getDbHelper() {
		mDbHelper = SpecimenHunterDatabaseAdapter.getInstance(mContext);
		return mDbHelper;
	}
	
	@Override
	public void onResume() {
		super.onResume();		
		setListSortMethod(getSortMethod());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);		
		mContext = getSherlockActivity();		
		mSortMethod = SpecimenHunterDatabaseAdapter.SORT_TITLE;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		//mDbHelper.close();
	}
	
	public abstract void setListSortMethod(int sortMethod);

}
