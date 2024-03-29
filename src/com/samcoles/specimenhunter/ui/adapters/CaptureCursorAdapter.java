package com.samcoles.specimenhunter.ui.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.samcoles.specimenhunter.R;
import com.samcoles.specimenhunter.provider.ImperialWeight;
import com.samcoles.specimenhunter.provider.SpecimenHunterDatabaseAdapter;
import com.samcoles.specimenhunter.ui.PhotoViewerDialogFragment;
import com.samcoles.specimenhunter.ui.SDImageLoader;

public class CaptureCursorAdapter extends SimpleCursorAdapter {
	
	private int mLayout;
	private FragmentActivity mActivity;	
	private SDImageLoader mImageLoader;

	public CaptureCursorAdapter(FragmentActivity activity, int layout, Cursor c) {
		
		super(activity, layout, c, new String[] {}, new int[] {}, 0);
		mLayout = layout;
		mActivity = activity;
		mImageLoader = new SDImageLoader();
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
		String comment = c.getString(c.getColumnIndexOrThrow(SpecimenHunterDatabaseAdapter.KEY_CAPTURES_COMMENT));
		int speciesId = c.getInt(c.getColumnIndexOrThrow(SpecimenHunterDatabaseAdapter.KEY_CAPTURES_SPECIES));
		
		//FIXME reimplement so that species is returned as string from fetchAllCaptures
		//making the below db call unneccessary 
		SpecimenHunterDatabaseAdapter dbHelper = new SpecimenHunterDatabaseAdapter(context).open();
		String species = dbHelper.fetchSpeciesName(speciesId);
		dbHelper.close();
		
		ImperialWeight imperialWeight = new ImperialWeight(centigrams);
		
		Resources resources = mContext.getResources();
		String speciesAndWeight = String.format(resources.getString(R.string.species_and_weight_imperial), species, imperialWeight.getPounds(), imperialWeight.getOunces(), imperialWeight.getDrams());
		
				
		TextView titleTextView = (TextView)v.findViewById(R.id.textview_capture_title);
		TextView commentTextView = (TextView)v.findViewById(R.id.textview_capture_comment);
		TextView speciesAndWeightTextView = (TextView)v.findViewById(R.id.textview_capture_species_and_weight);
		ImageView photoImageView = (ImageView)v.findViewById(R.id.imageview_capture_thumbnail);
		photoImageView.setTag(photoFilePath);
		
		photoImageView.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
				Fragment previous = mActivity.getSupportFragmentManager().findFragmentByTag("dialog");
				if(previous != null) ft.remove(previous);
				ft.addToBackStack(null);
				
				DialogFragment newFragment = PhotoViewerDialogFragment.newInstance((String)v.getTag());
				newFragment.show(ft, "dialog");
			}
		});

		titleTextView.setText(title);
		commentTextView.setText(comment);
		speciesAndWeightTextView.setText(speciesAndWeight);
		mImageLoader.load(mContext, photoFilePath, photoImageView);
	}
	

}
