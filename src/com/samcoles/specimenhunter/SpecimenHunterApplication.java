package com.samcoles.specimenhunter;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;
import android.content.Context;

@ReportsCrashes(formKey = "dHJMeDVPUnFOOXJKeWdwYnU4R0N4b0E6MQ") 
public class SpecimenHunterApplication extends Application {
	
	private static Context context;
	
	@Override
    public void onCreate() {
        ACRA.init(this);
        super.onCreate();
        SpecimenHunterApplication.context = getApplicationContext();        
    }
	
	public static Context getContext() {
		return SpecimenHunterApplication.context;
	}
	
}
