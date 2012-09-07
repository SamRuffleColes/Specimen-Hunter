package com.samcoles.specimenhunter.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.samcoles.specimenhunter.R;

public class PhotoPicker extends RelativeLayout {

	public PhotoPicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.photo_picker, this);
		
		ImageButton galleryButton = (ImageButton)findViewById(R.id.imagebutton_gallery);
		galleryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ImageButton cameraButton = (ImageButton)findViewById(R.id.imagebutton_camera);
		cameraButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public PhotoPicker(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PhotoPicker(Context context) {
		this(context, null, 0);
	}
	
	

}
