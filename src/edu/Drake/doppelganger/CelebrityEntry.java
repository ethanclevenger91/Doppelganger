package edu.Drake.doppelganger;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;


public final class CelebrityEntry {

	private String name;
	private int pic;
	
	public CelebrityEntry(String name, int pic) {
		this.name = name;
		this.pic = pic;
	}
 
	/**
	 * @return Title of news entry
	 */
	public String getName() {
		return name;
	}
 
	/**
	 * @return Author of news entry
	 */
	public int getPic() {
		return pic;
	}

}

