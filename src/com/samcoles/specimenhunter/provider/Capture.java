package com.samcoles.specimenhunter.provider;

public class Capture {
	
	private String mTitle;
	private int mSpecies;
	private int mCentigrams;
	private String mPhotoPath;
	private String mComment;
	
	public String getTitle() {
		return mTitle;
	}
	public void setTitle(String title) {
		mTitle = title;
	}
	public int getSpecies() {
		return mSpecies;
	}
	public void setSpecies(int species) {
		mSpecies = species;
	}
	public int getCentigrams() {
		return mCentigrams;
	}
	public void setCentigrams(int centigrams) {
		mCentigrams = centigrams;
	}
	public String getPhotoPath() {
		return mPhotoPath;
	}
	public void setPhotoPath(String photoPath) {
		mPhotoPath = photoPath;
	}
	public String getComment() {
		return mComment;
	}
	public void setComment(String comment) {
		mComment = comment;
	}
	
}
