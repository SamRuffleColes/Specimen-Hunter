package com.samcoles.specimenhunter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.samcoles.specimenhunter.SpecimenHunterApplication;
import com.samcoles.specimenhunter.provider.SpecimenHunterDatabaseAdapter;

public abstract class SpecimenHunterPreferences {
		
	public static final String CAPTURE_SORT_METHOD = "com.samcoles.specimenhunter.utils.Preferences.captureSortMethod";
	
	private static SharedPreferences getPreferences() {
		Context context = SpecimenHunterApplication.getContext();
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs;
	}
	
	public static int getSortMethod() {		
		int sortMethod = getPreferences().getInt(CAPTURE_SORT_METHOD, SpecimenHunterDatabaseAdapter.SORT_TITLE);
		return sortMethod;
	}
	
	public static void setSortMethod(int sortMethod) {
		Editor editor = getPreferences().edit();
		editor.putInt(CAPTURE_SORT_METHOD, sortMethod);
		editor.commit();
	}

}
