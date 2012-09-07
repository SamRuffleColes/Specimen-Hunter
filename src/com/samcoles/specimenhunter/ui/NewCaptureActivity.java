package com.samcoles.specimenhunter.ui;

import android.os.Bundle;

import com.samcoles.specimenhunter.R;
import com.samcoles.specimenhunter.ui.widget.WeightInput;

public class NewCaptureActivity extends SpecimenHunterBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_new_capture);
		
		onConfigureWeightInput();	
		
	}

	private void onConfigureWeightInput() {
		// TODO pull in metric/imperial from settings
		
		final WeightInput weightInput = (WeightInput)findViewById(R.id.weight_input);
		//weightInput.setInputType(WeightInput.TYPE_METRIC);
		weightInput.setInputType(WeightInput.TYPE_IMPERIAL);
	}
	
	

}
