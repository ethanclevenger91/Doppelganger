package edu.Drake.doppelganger;

public class FeedsModel {

	private String name;
	  private boolean selected;

	  public FeedsModel(String name) {
	    this.name = name;
	    selected = false;
	  }

	  public String getName() {
	    return name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }

	  public boolean isSelected() {
	    return selected;
	  }

	  public void setSelected(boolean selected) {
	    this.selected = selected;
	  }
	
}
