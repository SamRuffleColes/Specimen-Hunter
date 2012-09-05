package com.samcoles.specimenhunter.ui;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.deaux.fan.FanView;
import com.samcoles.specimenhunter.R;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;

public class ViewCapturesActivity extends SherlockFragmentActivity {
	
	private FanView mFan;
	
	private TabsAdapter mAdapter;
	private ViewPager mPager;
	private PageIndicator mIndicator;
	
	private static final String[] FRAGMENT_TITLES = { "All", "Personal Bests" };
	
	public static final String INITIAL_TAB = "InitialTab";
	public static final int ALL_CAPTURES_TAB = 0;
	public static final int PERSONAL_BESTS_TAB = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		
		//setContentView(R.layout.tabs);
		setContentView(R.layout.activity_fan);
		
		mFan = (FanView) findViewById(R.id.fan_view);
        mFan.setViews(R.layout.tabs, R.layout.fan);
		
		mAdapter = new TabsAdapter(getSupportFragmentManager());
		
		mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        
        mIndicator = (TabPageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	class TabsAdapter extends FragmentPagerAdapter {
		
		public TabsAdapter(FragmentManager fm) {
			super(fm);
		}
		
		@Override
		public Fragment getItem(int position) {
			switch(position) {
			case ALL_CAPTURES_TAB:
				return new AllCapturesFragment();
			case PERSONAL_BESTS_TAB:
				return new PersonalBestsFragment();
			default:
				return null;
			}
		}
		
		@Override
		public int getCount() {
			return ViewCapturesActivity.FRAGMENT_TITLES.length;
		}
		
		@Override
        public CharSequence getPageTitle(int position) {
			return ViewCapturesActivity.FRAGMENT_TITLES[position];
        }

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

	
	
}
