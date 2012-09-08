package com.samcoles.specimenhunter.provider;

public class Species {
	
	private int mId;
	private String mName;
	private String mDescription;
	private String mBaits;
	private String mMethods;
	private boolean mEditable;
	
	
	@Override
	public String toString() {
		
		if(mName != null) return mName;
		
		return super.toString();
	}
	
	public Species(int id, String name) {
		mId = id;
		mName = name;		
	}
	
	public Species(int id, String name, String description, String baits,
			String methods, boolean editable) {
		mId = id;
		mName = name;
		mDescription = description;
		mBaits = baits;
		mMethods = methods;
		mEditable = editable;
	}
	public boolean isEditable() {
		return mEditable;
	}
	public void setEditable(boolean editable) {
		mEditable = editable;
	}
	public int getId() {
		return mId;
	}
	public void setId(int id) {
		mId = id;
	}
	public String getName() {
		return mName;
	}
	public void setName(String name) {
		mName = name;
	}
	public String getDescription() {
		return mDescription;
	}
	public void setDescription(String description) {
		mDescription = description;
	}
	public String getBaits() {
		return mBaits;
	}
	public void setBaits(String baits) {
		mBaits = baits;
	}
	public String getMethods() {
		return mMethods;
	}
	public void setMethods(String methods) {
		mMethods = methods;
	}

}
