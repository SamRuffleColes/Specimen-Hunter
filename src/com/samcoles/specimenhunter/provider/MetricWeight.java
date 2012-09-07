package com.samcoles.specimenhunter.provider;


public class MetricWeight {
	
	private static final int CENTIGRAMS_IN_ONE_KILOGRAM = 100000;
	private static final int CENTIGRAMS_IN_ONE_GRAM = 100;
	
	private static final double CENTIGRAMS_IN_ONE_POUND = 45359.237;
	private static final double CENTIGRAMS_IN_ONE_OUNCE = 2834.95231;
	private static final double CENTIGRAMS_IN_ONE_DRAM = 177.18452;
	
	private int mCentigrams;
	
	public int getCentigrams() {
		return mCentigrams;
	}

	public MetricWeight(int kilograms, int grams) {
		mCentigrams += kilograms * CENTIGRAMS_IN_ONE_KILOGRAM;
		mCentigrams += grams * CENTIGRAMS_IN_ONE_GRAM;
	}
	
	public MetricWeight(int centigrams) {
		mCentigrams = centigrams;
	}
	
	public MetricWeight(double kilograms) {
		mCentigrams = (int) (kilograms * CENTIGRAMS_IN_ONE_KILOGRAM);
	}
	
	public MetricWeight(int pounds, int ounces, int drams) {
		mCentigrams += pounds * CENTIGRAMS_IN_ONE_POUND;
		mCentigrams += ounces * CENTIGRAMS_IN_ONE_OUNCE;
		mCentigrams += drams * CENTIGRAMS_IN_ONE_DRAM;
	}
	
	public int getKilograms() {
		return mCentigrams / CENTIGRAMS_IN_ONE_KILOGRAM;
	}
	
	public int getGrams() {
		return mCentigrams / CENTIGRAMS_IN_ONE_GRAM;
	}

}
