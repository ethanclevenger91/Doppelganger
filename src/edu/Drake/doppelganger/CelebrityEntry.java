package edu.Drake.doppelganger;

import android.graphics.drawable.Drawable;



public final class CelebrityEntry {

	private String name;
	private String pic;
	
	public CelebrityEntry(String name, String pic) {
		this.name = name;
		this.pic = pic;
	}
 
	/**
	 * @return name of celebrity
	 */
	public String getName() {
		return name;
	}
	/**
	 * override the toString function so filter will work
	 */
	public String toString() {
		return name;
	}
	/**
	 * @return picture of celebrity
	 */
	public String getPic() {
		return pic;
	}
	

}

