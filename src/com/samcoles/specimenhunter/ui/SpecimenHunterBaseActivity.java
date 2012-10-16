package com.samcoles.specimenhunter.ui;

import net.simonvt.widget.MenuDrawer;
import net.simonvt.widget.MenuDrawerManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.samcoles.specimenhunter.R;
import com.samcoles.specimenhunter.ui.widget.MenuScrollView;

public class SpecimenHunterBaseActivity extends SherlockFragmentActivity {
	
	private static final String TAG = "com.samcoles.specimenhunter.ui.SpecimenHunterBaseActivity";
	private static final String STATE_MENUDRAWER = "com.samcoles.specimenhunter.ui.SpecimenHunterBaseActivity.menuDrawer";
    private static final String STATE_ACTIVE_VIEW_ID = "com.samcoles.specimenhunter.ui.SpecimenHunterBaseActivity.activeViewId";

    private MenuDrawerManager mMenuDrawer;
    private int mActiveViewId;
    
    @Override
	protected void onCreate(Bundle inState) {
		super.onCreate(inState);
        if (inState != null) {
            mActiveViewId = inState.getInt(STATE_ACTIVE_VIEW_ID);
        }

        mMenuDrawer = new MenuDrawerManager(this, MenuDrawer.MENU_DRAG_WINDOW);
        mMenuDrawer.setMenuView(R.layout.menu);

        MenuScrollView msv = (MenuScrollView) mMenuDrawer.getMenuView();
        msv.setOnScrollChangedListener(new MenuScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                mMenuDrawer.getMenuDrawer().invalidate();
            }
        });
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
    	LinearLayout newCaptureMenuItem = (LinearLayout)findViewById(R.id.fan_item_new_capture);
    	LinearLayout allCapturesMenuItem = (LinearLayout)findViewById(R.id.fan_item_all_captures);
    	LinearLayout personalBestsMenuItem = (LinearLayout)findViewById(R.id.fan_item_personal_bests);
    	LinearLayout targetsMenuItem = (LinearLayout)findViewById(R.id.fan_item_targets);
    	LinearLayout speciesMenuItem = (LinearLayout)findViewById(R.id.fan_item_species);
    	
        MenuClickListener menuClickListener = new MenuClickListener();
        newCaptureMenuItem.setOnClickListener(menuClickListener);
        allCapturesMenuItem.setOnClickListener(menuClickListener);
        personalBestsMenuItem.setOnClickListener(menuClickListener);
        targetsMenuItem.setOnClickListener(menuClickListener);
        speciesMenuItem.setOnClickListener(menuClickListener);       
        
	}
    
    @Override
	public final void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
	}

	@Override
	public final void setContentView(View view) {
		super.setContentView(view);
	}

	public void setContent(int layoutResId) {
    	mMenuDrawer.setContentView(layoutResId);
    }
	
	@Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mMenuDrawer.toggleMenu();
                return true;
            default:
            	return super.onOptionsItemSelected(item);
        }       
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        mMenuDrawer.onRestoreDrawerState(inState.getParcelable(STATE_MENUDRAWER));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_MENUDRAWER, mMenuDrawer.onSaveDrawerState());
        outState.putInt(STATE_ACTIVE_VIEW_ID, mActiveViewId);
    }
    
    @Override
    public void onBackPressed() {
        final int drawerState = mMenuDrawer.getDrawerState();
        if (drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING) {
            mMenuDrawer.closeMenu();
            return;
        }

        super.onBackPressed();
    }
    
    private class MenuClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			mMenuDrawer.closeMenu();
			
			Intent launchIntent = null;			
			switch(v.getId()) {
				case R.id.fan_item_new_capture:
					launchIntent = new Intent(SpecimenHunterBaseActivity.this, EditCaptureActivity.class);
					break;
				case R.id.fan_item_all_captures:
					//FIXME add bundle for all captures to show
					launchIntent = new Intent(SpecimenHunterBaseActivity.this, ViewCapturesActivity.class);
					break;
				case R.id.fan_item_personal_bests:
					//FIXME add bundle for personal bests to show
					launchIntent = new Intent(SpecimenHunterBaseActivity.this, ViewCapturesActivity.class);
					break;
				case R.id.fan_item_targets:
					break;
				case R.id.fan_item_species:
					break;
				default:
					break;			
			}
			
			if(launchIntent != null) {
				startActivity(launchIntent);
			}
		}    	
    }

}
