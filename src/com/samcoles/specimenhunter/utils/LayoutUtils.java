package com.samcoles.specimenhunter.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

public abstract class LayoutUtils {
	
	@SuppressLint("NewApi")
	public static Point getScreenSize(Activity activity) {
		
		Display display = activity.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		
		if(android.os.Build.VERSION.SDK_INT > 12) {
			display.getSize(size);
		}
		else {
			size.x = display.getWidth();
			size.y = display.getHeight();
		}
		
		return size;
	}

}
