package com.samcoles.specimenhunter.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.samcoles.specimenhunter.R;
import com.samcoles.specimenhunter.provider.SpecimenHunterDatabaseAdapter;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;

public class ViewCapturesActivity extends SpecimenHunterBaseActivity implements OnNavigationListener {
	
	private TabsAdapter mAdapter;
	private ViewPager mPager;
	private PageIndicator mIndicator;
	
	private static final String[] FRAGMENT_TITLES = { "All", "Personal Bests" };
	
	public static final String INITIAL_TAB = "InitialTab";
	public static final int ALL_CAPTURES_TAB = 0;
	public static final int PERSONAL_BESTS_TAB = 1;
	
	@Override
	protected void onCreate(Bundle inState) {
		super.onCreate(inState);		
		
		setContent(R.layout.activity_tabs);
		
		mAdapter = new TabsAdapter(getSupportFragmentManager());
		
		mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        
        mIndicator = (TabPageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
        	int launchTab = extras.getInt(INITIAL_TAB);
        	mPager.setCurrentItem(launchTab);
        }
        
        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.sort_options, android.R.layout.simple_spinner_dropdown_item);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setListNavigationCallbacks(spinnerAdapter, this);
        
	}
	
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {		
		switch(itemPosition) {
			case 0:
				Toast.makeText(this, "sort title", Toast.LENGTH_SHORT).show();
				setListSortMethod(SpecimenHunterDatabaseAdapter.SORT_TITLE);
				break;
			case 1:
				Toast.makeText(this, "sort species", Toast.LENGTH_SHORT).show();
				setListSortMethod(SpecimenHunterDatabaseAdapter.SORT_SPECIES);
				break;
			case 2:
				Toast.makeText(this, "sort weight", Toast.LENGTH_SHORT).show();
				setListSortMethod(SpecimenHunterDatabaseAdapter.SORT_WEIGHT);
				break;
			default:
				return false;
		}		
		return true;
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
	
	
}
