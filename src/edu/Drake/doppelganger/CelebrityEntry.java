package edu.Drake.doppelganger;



public final class CelebrityEntry {

	private String name;
	private int pic;
	
	public CelebrityEntry(String name, int pic) {
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
	public int getPic() {
		return pic;
	}

}

