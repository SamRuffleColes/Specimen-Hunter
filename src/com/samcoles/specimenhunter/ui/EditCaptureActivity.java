package com.samcoles.specimenhunter.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.samcoles.specimenhunter.R;
import com.samcoles.specimenhunter.provider.Capture;
import com.samcoles.specimenhunter.provider.Species;
import com.samcoles.specimenhunter.provider.SpecimenHunterDatabaseAdapter;
import com.samcoles.specimenhunter.ui.widget.PhotoPicker;
import com.samcoles.specimenhunter.ui.widget.WeightInput;

public class EditCaptureActivity extends SpecimenHunterBaseActivity {
	
	private static final String TAG = "com.samcoles.specimenhunter.ui.NewCaptureActivity";
	
	private static final int ACTION_CODE_CAMERA = 0;
	private static final int ACTION_CODE_GALLERY = 1;
	
	private PhotoPicker mPhotoPicker;	
	private ArrayList<Species> mSpecies = new ArrayList<Species>();
	private Capture mCapture;
	
	private String mTempImageFilePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_new_capture);
		
		mCapture = (Capture)getLastCustomNonConfigurationInstance();
		if(mCapture == null) mCapture = new Capture();
		
		onConfigureWeightInput();	
		onConfigurePhotoPicker();
		onConfigureSpeciesSpinner();
		onConfigureSaveButton();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		final Handler handler = new Handler();
		Runnable r = new Runnable() {			
			@Override
			public void run() {
				if(mCapture.getPhotoPath() != null) {
					mPhotoPicker.setImage(mCapture.getPhotoPath());
				} 	
			}
		};	
		handler.post(r);
	}



	private void onConfigureWeightInput() {
		// TODO pull in metric/imperial from settings
		
		final WeightInput weightInput = (WeightInput)findViewById(R.id.weight_input);
		//weightInput.setInputType(WeightInput.TYPE_METRIC);
		weightInput.setType(WeightInput.TYPE_IMPERIAL);
	}
	
	private void onConfigurePhotoPicker() {
		mPhotoPicker = (PhotoPicker)findViewById(R.id.photo_picker);
		
		mPhotoPicker.setGalleryButtonOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dispatchGalleryPickerIntent();
			}
		});
		
		mPhotoPicker.setCameraButtonOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dispatchTakePhotoIntent();
			}
		});		
		
		if(mCapture != null) {
			if(mCapture.getPhotoPath() != null) {
				mPhotoPicker.setImage(mCapture.getPhotoPath());
			}
		}
	}
	
	private void onConfigureSpeciesSpinner() {
		Spinner speciesSpinner = (Spinner)findViewById(R.id.spinner_species);
		SpecimenHunterDatabaseAdapter dbHelper = SpecimenHunterDatabaseAdapter.getInstance(this);	
		
		Cursor speciesCursor = dbHelper.fetchAllSpecies();
		speciesCursor.moveToFirst();	
		while(!speciesCursor.isAfterLast()) {
			int id = speciesCursor.getInt(speciesCursor.getColumnIndex(SpecimenHunterDatabaseAdapter.KEY_SPECIES_ROWID));
			String name = speciesCursor.getString(speciesCursor.getColumnIndex(SpecimenHunterDatabaseAdapter.KEY_SPECIES_NAME));
			Species species = new Species(id, name);
			mSpecies.add(species);
			speciesCursor.moveToNext();			
		}
		speciesCursor.close();
		
		ArrayAdapter<Species> speciesAdapter = new ArrayAdapter<Species>(this, android.R.layout.simple_spinner_item, mSpecies);
		speciesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		speciesSpinner.setAdapter(speciesAdapter);
		
		//dbHelper.close();
	}
	
	private void onConfigureSaveButton() {
		Button saveButton = (Button)findViewById(R.id.button_save);
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveDataToDatabase();
			}
		});		
	}
	
	private void dispatchTakePhotoIntent() {
		Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = timeStamp + "_" + "sh";
	    File imageFile = null;
		try {
			imageFile = File.createTempFile(imageFileName, ".jpg", getExternalCacheDir());
		} catch (IOException e) {
			Log.e(TAG, "error creating temp camera file");
		}
		mTempImageFilePath = imageFile.getAbsolutePath();
	    takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
	    
	    startActivityForResult(takePhotoIntent, ACTION_CODE_CAMERA);
	}
	
	private void dispatchGalleryPickerIntent() {
		Intent galleryPickerIntent = new Intent();
	    galleryPickerIntent.setType("image/*");
	    galleryPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
	    galleryPickerIntent.addCategory(Intent.CATEGORY_OPENABLE);
	    startActivityForResult(galleryPickerIntent, ACTION_CODE_GALLERY);
	}
	
	private void handleTakePhotoIntentResult(Intent intent) {
		mCapture.setPhotoPath(mTempImageFilePath);
		mPhotoPicker.setImage(mCapture.getPhotoPath());		
	}
	
	private void handleGalleryPickerIntentResult(Intent intent) {
		
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = timeStamp + "_" + "sh";
	    File imageFile = null;
	    InputStream inputStream = null;
	    OutputStream outputStream = null;
		try {
			imageFile = File.createTempFile(imageFileName, ".jpg", getExternalCacheDir());
			inputStream = getContentResolver().openInputStream(intent.getData());
			outputStream = new FileOutputStream(imageFile);
			
			int read = 0;
			byte[] bytes = new byte[1024];
		 
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (IOException e) {
			Log.e(TAG, "error creating temp gallery file");
		} finally {
			if(inputStream != null) {
				try {
					inputStream.close();
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					Log.e(TAG, "error closing input/output streams writing temp gallery file");
				}
			}
		}		
		mCapture.setPhotoPath(imageFile.getAbsolutePath());
		mPhotoPicker.setImage(mCapture.getPhotoPath());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {		
		if(resultCode != RESULT_OK) return;	
		
		switch(requestCode) {
			case ACTION_CODE_CAMERA:
				handleTakePhotoIntentResult(data);
				break;
			case ACTION_CODE_GALLERY:
				handleGalleryPickerIntentResult(data);
				break;
			default:
				super.onActivityResult(requestCode, resultCode, data);
				break;
		}
	}
	
	private void saveDataToModel() {
		EditText titleEditText = (EditText)findViewById(R.id.edittext_title);
		Spinner speciesSpinner = (Spinner)findViewById(R.id.spinner_species);
		WeightInput weightInput = (WeightInput)findViewById(R.id.weight_input);
		EditText commentEditText = (EditText)findViewById(R.id.edittext_comment);
		
		mCapture.setTitle(titleEditText.getText().toString());
		mCapture.setSpecies(mSpecies.get(speciesSpinner.getSelectedItemPosition()).getId());
		mCapture.setCentigrams(weightInput.getCentigrams());
		mCapture.setComment(commentEditText.getText().toString());
	}

	private void saveDataToDatabase() {
		saveDataToModel();
		
		//TODO launch progress dialog "saving"
		
		File tempPhotoFile = new File(mCapture.getPhotoPath());		
		File permanentImageFile = null; 
		if(tempPhotoFile != null && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			InputStream inputStream = null;
		    OutputStream outputStream = null;	    
		    try {
		    	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			    String imageFileName = timeStamp + "_" + "sh.jpg";
		    	permanentImageFile = new File(getExternalFilesDir(null), imageFileName);	
		    	
		    	inputStream = new FileInputStream(tempPhotoFile);
		    	outputStream = new FileOutputStream(permanentImageFile);
		    	
		    	int read = 0;
				byte[] bytes = new byte[1024];
			 
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
		    } catch(IOException e) {
		    	Log.e(TAG, "error writing permanent file");
		    } finally {
		    	try {
		    		inputStream.close();
		    		outputStream.flush();
		    		outputStream.close();
		    		tempPhotoFile.delete();
		    	} catch(IOException e) {
		    		Log.e(TAG, "error closing input/output streams while writing permanent file");
		    	}
		    }
		}
		
		SpecimenHunterDatabaseAdapter dbHelper = SpecimenHunterDatabaseAdapter.getInstance(this);	
		dbHelper.createCapture(mCapture.getTitle(), mCapture.getSpecies(), permanentImageFile.getAbsolutePath(), mCapture.getCentigrams(), mCapture.getComment());
		//dbHelper.close();
		finish();
	}
	
	@Override
	public Object onRetainCustomNonConfigurationInstance() {
		saveDataToModel();
		return mCapture;
	}
	
}
