package com.samcoles.specimenhunter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.StaticLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.deaux.fan.FanView;
import com.samcoles.specimenhunter.R;

public class SpecimenHunterBaseActivity extends SherlockFragmentActivity {
	
	private static final String TAG = "com.samcoles.specimenhunter.ui.SpecimenHunterBaseActivity";
	
	private FanView mFan;

	@Override
	public void setContentView(int layoutResId) {
		super.setContentView(R.layout.activity_base);
		mFan = (FanView)findViewById(R.id.fan_view);
		mFan.setViews(layoutResId, R.layout.fan);	
		
		LinearLayout newCaptureFanItem = (LinearLayout)mFan.findViewById(R.id.fan_item_new_capture);
		LinearLayout allCapturesFanItem = (LinearLayout)mFan.findViewById(R.id.fan_item_all_captures);
		LinearLayout personalBestsFanItem = (LinearLayout)mFan.findViewById(R.id.fan_item_personal_bests);
		LinearLayout targetsFanItem = (LinearLayout)mFan.findViewById(R.id.fan_item_targets);
		LinearLayout speciesFanItem = (LinearLayout)mFan.findViewById(R.id.fan_item_species);
		
		FanClickListener fanClickListener = new FanClickListener();
		newCaptureFanItem.setOnClickListener(fanClickListener);
		allCapturesFanItem.setOnClickListener(fanClickListener);
		personalBestsFanItem.setOnClickListener(fanClickListener);
		targetsFanItem.setOnClickListener(fanClickListener);
		speciesFanItem.setOnClickListener(fanClickListener);		
				
		ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);	
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case android.R.id.home:
				mFan.showMenu();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	private class FanClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch(v.getId()) {
				case R.id.fan_item_new_capture:
					closeFanAndLaunchActivity(EditCaptureActivity.class, null);
					break;
				case R.id.fan_item_all_captures:
					//FIXME add bundle to launch w/ "All Captures" showing
					closeFanAndLaunchActivity(ViewCapturesActivity.class, null);
					break;
				case R.id.fan_item_personal_bests:
					//FIXME add bundle to launch w/ "Personal Bests" tab showing
					closeFanAndLaunchActivity(ViewCapturesActivity.class, null);
					break;
				case R.id.fan_item_targets:
					break;
				case R.id.fan_item_species:
					break;
				default:
					break;
			}
		}		
	}
	
	private void closeFanAndLaunchActivity(Class<?> activityClass, Bundle extras) {		
		
		if(mFan.isOpen()) mFan.showMenu();
		
		final Intent i = new Intent(this, activityClass);
		if(extras != null) i.putExtras(extras);
		
		final Handler handler = new Handler();
		
		final Runnable r = new Runnable() {			
			@Override
			public void run() {				
				if(mFan.isClosed()) {
					startActivity(i);
				} else {
					handler.postDelayed(this, 25);
				}
			}
		};
		
		handler.postDelayed(r, 100);
	}

	@Override
	public void onBackPressed() {
		if(mFan.isOpen()) mFan.showMenu();
		else super.onBackPressed();
	}
	
	

}
