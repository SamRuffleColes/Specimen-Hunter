package com.samcoles.specimenhunter.service;

import com.samcoles.specimenhunter.provider.SpecimenHunterDatabaseAdapter;

import android.app.IntentService;
import android.content.Intent;

/**
 * Service for asynchronous call to convert database to centigrams
 * 
 * @author sam
 * @since 03-09-2012
 *
 */
public class DatabaseConversionService extends IntentService {

	public DatabaseConversionService() {
		super("DatabaseConversionService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
//		SpecimenHunterDatabaseAdapter dbHelper = new SpecimenHunterDatabaseAdapter(this);
//		dbHelper = dbHelper.open();
		
		
		
		
	}

}
