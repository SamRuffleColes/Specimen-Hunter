package com.samcoles.specimenhunter.ui.adapters;

import com.samcoles.specimenhunter.provider.SpecimenHunterDatabaseAdapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CaptureCursorAdapter extends SimpleCursorAdapter {
	
	private int mLayout;

	public CaptureCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		mLayout = layout;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		final LayoutInflater inflater = LayoutInflater.from(context);
		View newView = inflater.inflate(mLayout, parent, false);
		return newView;
	}
	
	@Override
	public void bindView(View v, Context context, Cursor c) {
		String title = c.getString(c.getColumnIndexOrThrow(SpecimenHunterDatabaseAdapter.KEY_CAPTURES_TITLE));
		int centigrams = c.getInt(c.getColumnIndexOrThrow(SpecimenHunterDatabaseAdapter.KEY_CAPTURES_CENTIGRAMS));
		String photoFilePath = c.getString((c.getColumnIndexOrThrow(SpecimenHunterDatabaseAdapter.KEY_CAPTURES_PHOTO)));
		
		//TODO finish....
				
	}
	

}
