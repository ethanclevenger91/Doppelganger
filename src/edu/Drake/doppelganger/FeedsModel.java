package edu.Drake.doppelganger;

import java.util.List;

public class FeedsModel {

	private String name;
	  private boolean selected;
	  private int ups;
	  private int downs;
	  private int comments;
	  private String imageId;
	  private String desc;
	  private List<String> commentList;

	  public FeedsModel(String name, int ups, int downs, int comments, String image, String desc, List<String> commentList) {
	    this.name = name;
	    this.ups = ups;
	    this.downs=downs;
	    this.comments=comments;
	    this.imageId = image;
	    this.desc = desc;
	    this.commentList = commentList;
	    selected = false;
	  }
	  
	  public List<String> getCommentList() {
		  return commentList;
	  }
	  
	  public void setCommentList(List<String> commentList) {
		  this.commentList = commentList;
	  }
	  
	  public String getImageId() {
	        return imageId;
	    }
	    public void setImageId(String imageId) {
	        this.imageId = imageId;
	    }
	    
	    public String getDesc() {
	        return desc;
	    }
	    public void setDesc(String desc) {
	        this.desc = desc;
	    }
	  
	  public void setUps(int n) {
		  this.ups = n;
	  }
	  
	  public String getUps() {
		  String upString=Integer.toString(ups);
		  return upString;
	  }
	  
	  public void incrementUp() {
		  this.ups++;
	  }
	  
	  public void incrementDown() {
		  this.downs++;
	  }
	  
	  public void setDowns(int n) {
		  this.downs = n;
	  }
	  
	  public String getDowns() {
		  String downString = Integer.toString(downs);
		  return downString;
	  }
	  
	  public void setComments(int n) {
		  this.comments = n;
	  }
	  
	  public String getComments() {
		  String commentsString = Integer.toString(comments);
		  return commentsString;
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
