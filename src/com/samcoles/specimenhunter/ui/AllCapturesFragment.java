package com.samcoles.specimenhunter.ui;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockListFragment;
import com.samcoles.specimenhunter.R;
import com.samcoles.specimenhunter.provider.SpecimenHunterDatabaseAdapter;
import com.samcoles.specimenhunter.ui.adapters.CaptureCursorAdapter;

public class AllCapturesFragment extends SherlockListFragment {

	private SpecimenHunterDatabaseAdapter mDbHelper;
	private Context mContext;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);		
		mContext = getSherlockActivity();		
		mDbHelper = new SpecimenHunterDatabaseAdapter(mContext).open();
	}

	@Override
	public void onResume() {
		super.onResume();
		Cursor c = mDbHelper.fetchAllCaptures(SpecimenHunterDatabaseAdapter.SORT_NONE);
		setListAdapter(new CaptureCursorAdapter(getActivity(), R.layout.li_capture, c));
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mDbHelper.close();
	}
}
