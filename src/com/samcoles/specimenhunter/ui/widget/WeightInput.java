package com.samcoles.specimenhunter.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.samcoles.specimenhunter.R;
import com.samcoles.specimenhunter.provider.MetricWeight;

public class WeightInput extends LinearLayout {
	
	public static final int TYPE_IMPERIAL = 0;
	public static final int TYPE_METRIC = 1;
	
	private int mInputType;

	public WeightInput(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	public WeightInput(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WeightInput(Context context) {
		this(context, null, 0);
	}
	
	public void setType(int inputType) {
		mInputType = inputType;
		
		Context context = getContext();
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		switch(mInputType) {
			case TYPE_IMPERIAL:
				setOrientation(LinearLayout.HORIZONTAL);
				inflater.inflate(R.layout.weight_input_imperial, this);
				break;
			case TYPE_METRIC:
				setOrientation(LinearLayout.VERTICAL);
				inflater.inflate(R.layout.weight_input_metric, this);
				break;
			default:
				break;
		}
		invalidate();
	}
	
	public int getCentigrams() {
		
		MetricWeight metricWeight = new MetricWeight(0);
		
		switch (mInputType) {
			case TYPE_IMPERIAL:				
				EditText poundsEditText = (EditText)findViewById(R.id.edittext_pounds);
				EditText ouncesEditText = (EditText)findViewById(R.id.edittext_ounces);
				EditText dramsEditText = (EditText)findViewById(R.id.edittext_drams);
				
				int pounds = Integer.parseInt(poundsEditText.getText().toString());
				int ounces = Integer.parseInt(ouncesEditText.getText().toString());
				int drams = Integer.parseInt(dramsEditText.getText().toString());
				
				metricWeight = new MetricWeight(pounds, ounces, drams);
				break;
				
			case TYPE_METRIC:				
				EditText kgEditText = (EditText)findViewById(R.id.edittext_kilograms);
				double kilograms = Double.parseDouble(kgEditText.getText().toString());
				metricWeight = new MetricWeight(kilograms);
				break;
	
			default:
				break;
		}
		
		return metricWeight.getCentigrams();
	}
	
	public void setCentigrams(int centigrams) {
		//TODO implement setCentigrams
	}
}
