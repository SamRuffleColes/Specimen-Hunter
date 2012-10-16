package com.samcoles.specimenhunter.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.samcoles.specimenhunter.R;

public class PhotoViewerDialogFragment extends DialogFragment {
	
	private static final String FILE_PATH = "com.samcoles.specimenhunter.ui.PhotoViewerDialogFragment.filePath";
	
	private SDImageLoader mImageLoader;
	private String mFilePath;
	
	public static PhotoViewerDialogFragment newInstance(String photo) {
		PhotoViewerDialogFragment dialogFragment = new PhotoViewerDialogFragment();
		
		Bundle args = new Bundle();
		args.putString(FILE_PATH, photo);
		dialogFragment.setArguments(args);
		
		return dialogFragment;
	}
	
	@Override
	public void onCreate(Bundle inState) {
		super.onCreate(inState);
		mFilePath = getArguments().getString(FILE_PATH);
		mImageLoader = new SDImageLoader();
		setStyle(STYLE_NO_FRAME, 0);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle inState) {
		View v = inflater.inflate(R.layout.dialog_photo_viewer, container, false);
		ImageView photoImageView = (ImageView)v.findViewById(R.id.imageview_photo);
		photoImageView.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				PhotoViewerDialogFragment.this.dismiss();
			}
		});			
		mImageLoader.load(getActivity(), mFilePath, photoImageView);
		return v;
	}
	

}
