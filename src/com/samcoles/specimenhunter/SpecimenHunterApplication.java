package com.samcoles.specimenhunter;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;

@ReportsCrashes(formKey = "dHJMeDVPUnFOOXJKeWdwYnU4R0N4b0E6MQ") 
public class SpecimenHunterApplication extends Application {
	
	@Override
    public void onCreate() {
        ACRA.init(this);
        super.onCreate();
    }
	
}
