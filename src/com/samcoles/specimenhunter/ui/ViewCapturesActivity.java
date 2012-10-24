package com.samcoles.specimenhunter.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.samcoles.specimenhunter.R;
import com.samcoles.specimenhunter.provider.SpecimenHunterDatabaseAdapter;
import com.samcoles.specimenhunter.utils.SpecimenHunterPreferences;
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
        
        SpinnerAdapter spinnerAdapter = new SortTypeSpinnerAdapter();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(spinnerAdapter, this);     
        actionBar.setSelectedNavigationItem(SpecimenHunterPreferences.getSortMethod());
	}
	
	private class SortTypeSpinnerAdapter extends BaseAdapter {
						
		List<SortTypeItem> mItems;
		
		public SortTypeSpinnerAdapter() {
			mItems = new ArrayList<SortTypeItem>();
			mItems.add(new SortTypeItem(getString(R.string.sort_title), R.drawable.ic_alphabet));
			mItems.add(new SortTypeItem(getString(R.string.sort_species), R.drawable.ic_species));
			mItems.add(new SortTypeItem(getString(R.string.sort_weight), R.drawable.ic_weight));
		}

		@Override
		public int getCount() {
			return mItems.size();
		}

		@Override
		public Object getItem(int position) {
			return mItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return mItems.get(position).getId();
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View spinnerSortTypeView = inflater.inflate(R.layout.li_spinner_sort_type, null);
			TextView text = (TextView)spinnerSortTypeView.findViewById(R.id.textview_sort_type);
			ImageView icon = (ImageView)spinnerSortTypeView.findViewById(R.id.imageview_sort_type_icon);
			text.setText(mItems.get(position).getText());
			icon.setImageResource(mItems.get(position).getDrawable());
			
			return spinnerSortTypeView;
		}
		
		private class SortTypeItem {
			
			private int mId;
			private int mDrawable;
			private String mText;
			
			public SortTypeItem(String text, int drawable) {
				mDrawable = drawable;
				mText = text;
			}
			
			public int getId() {
				return mId;
			}

			public int getDrawable() {
				return mDrawable;
			}
			public String getText() {
				return mText;
			}			
		}
		
	}
	
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {		
					
		switch(itemPosition) {
			case 0:
				SpecimenHunterPreferences.setSortMethod(SpecimenHunterDatabaseAdapter.SORT_TITLE);
				mAdapter.notifyDataSetChanged();
				break;
			case 1:
				SpecimenHunterPreferences.setSortMethod(SpecimenHunterDatabaseAdapter.SORT_SPECIES);
				mAdapter.notifyDataSetChanged();
				break;
			case 2:
				SpecimenHunterPreferences.setSortMethod(SpecimenHunterDatabaseAdapter.SORT_WEIGHT);
				mAdapter.notifyDataSetChanged();
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
					return AllCapturesFragment.newInstance();
				case PERSONAL_BESTS_TAB:
					return PersonalBestsFragment.newInstance();
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

		//forces refresh when notifyDataSetChanged() is called
		@Override
		public int getItemPosition(Object object) {
			return PagerAdapter.POSITION_NONE;
		}	
		
	}	
}
