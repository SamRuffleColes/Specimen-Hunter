package com.samcoles.specimenhunter.ui.widget;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;

import com.samcoles.specimenhunter.R;
import com.samcoles.specimenhunter.utils.IntentUtils;

public class PhotoPicker extends RelativeLayout {
	
	private ImageButton mGalleryButton, mCameraButton;

	public PhotoPicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)) {
			inflater.inflate(R.layout.photo_picker, this);
			
			mGalleryButton = (ImageButton)findViewById(R.id.imagebutton_gallery);		
			mCameraButton = (ImageButton)findViewById(R.id.imagebutton_camera);
			
			PackageManager pm = context.getPackageManager();
			if(!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
				|| !IntentUtils.isIntentAvailable(context, MediaStore.ACTION_IMAGE_CAPTURE)) {
				mCameraButton.setVisibility(INVISIBLE);
			}
		} else {
			//can't read & write storage
			inflater.inflate(R.layout.photo_picker_unavailable, this);
		}	
	}

	public PhotoPicker(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PhotoPicker(Context context) {
		this(context, null);
	}
	
	public void setCameraButtonOnClickListener(OnClickListener clickListener) {
		if(mCameraButton != null) mCameraButton.setOnClickListener(clickListener);
	}
	
	public void setGalleryButtonOnClickListener(OnClickListener clickListener) {
		if(mGalleryButton != null) mGalleryButton.setOnClickListener(clickListener);
	}
	
	public void setImage(String imageFilePath) {
		
		ImageView imageView = (ImageView)findViewById(R.id.imageview_image);
		if(imageView == null) return;
		
		int targetWidth = imageView.getWidth();
	    
		if(targetWidth <= 0) return;
		
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(imageFilePath, bmOptions);
	    int imageWidth = bmOptions.outWidth;
	    
	    int scaleFactor = imageWidth / targetWidth;
	    if(scaleFactor < 3) scaleFactor = 3;
	    
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;
	  
	    Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath, bmOptions);
	    imageView.setImageBitmap(bitmap);
	}

}
