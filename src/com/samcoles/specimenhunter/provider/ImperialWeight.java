package com.samcoles.specimenhunter.provider;


public class ImperialWeight {
	
	private static final double DRAMS_IN_ONE_CENTIGRAM = 0.00564383391;
	private static final int DRAMS_IN_ONE_POUND = 256;
	private static final int DRAMS_IN_ONE_OUNCE = 16;
	
	private int mPounds;
	private int mOunces;
	private int mDrams;
	
	public ImperialWeight(int pounds, int ounces, int drams) {
		mPounds = pounds;
		mOunces = ounces;
		mDrams = drams;
	}
	
	public ImperialWeight(int centigrams) {
		int totalDrams = (int) (centigrams * DRAMS_IN_ONE_CENTIGRAM);
		
		if(totalDrams > 0) {
			mPounds = totalDrams / DRAMS_IN_ONE_POUND;
			if(mPounds > 0) totalDrams -= mPounds * DRAMS_IN_ONE_POUND;			
			
			mOunces = totalDrams / DRAMS_IN_ONE_OUNCE;
			if(mOunces > 0) totalDrams -= mOunces * DRAMS_IN_ONE_OUNCE;
			
			mDrams = totalDrams;			
		}	
		
	}

	public int getPounds() {
		return mPounds;
	}

	public void setPounds(int pounds) {
		mPounds = pounds;
	}

	public int getOunces() {
		return mOunces;
	}

	public void setOunces(int ounces) {
		mOunces = ounces;
	}

	public int getDrams() {
		return mDrams;
	}

	public void setDrams(int drams) {
		mDrams = drams;
	}

}
