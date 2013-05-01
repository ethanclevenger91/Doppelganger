package edu.Drake.doppelganger;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class FeedsModel {

	private String name;
	  private boolean selected;
	  private int id;
	  private String fid;
	  private long timestamp;
	  private int ups;
	  private int downs;
	  private int comments;
	  private String imageId; // photo from camera or gallery
	  private String desc;
	  private List<String> commentList;

	  public FeedsModel() {
		  
	  }
	  
	  public FeedsModel(String name, int ups, int downs, int comments, String image, String desc, List<String> commentList, String fid, Long timestamp) {
	    this.name = name;
	    this.fid = fid;
	    this.ups = ups;
	    this.timestamp = timestamp;
	    this.downs=downs;
	    this.comments=comments;
	    this.imageId = image;
	    this.desc = desc;
	    this.commentList = commentList;
	    selected = false;
	  }
	  
	  public FeedsModel(int id, String desc, String name, int likes, int dislikes, int comments, String allComments, String image, String fid, Long timestamp) {
		  	this.id = id;
		  	this.fid = fid;
		    this.name = name;
		    this.ups = likes;
		    this.downs=dislikes;
		    this.timestamp = timestamp;
		    this.desc = desc;
		    this.comments = comments;
		    this.imageId = image;
		    Gson gson = new Gson();
		    @SuppressWarnings("unchecked")
			List<String> obj = gson.fromJson(allComments, ArrayList.class);
		    
		    this.commentList = obj;
		    selected = false;
		  }
	  
	  public FeedsModel(int id, String desc, String name, int likes, int dislikes, int comments, List<String> allComments, String image, String fid, Long timestamp) {
		  	this.id = id;
		    this.name = name;
		    this.ups = likes;
		    this.downs=dislikes;
		    this.desc = desc;
		    this.timestamp = timestamp;
		    this.comments = comments;
		    this.fid = fid;
		    this.commentList = allComments;
		    this.imageId=image;
		    selected = false;
		  }
	  
	  public FeedsModel(String desc, String name, int likes, int dislikes, int comments, List<String> commentList, String image, String fId, Long timestamp) {
		    this.name = name;
		    this.ups = likes;
		    this.downs=dislikes;
		    this.desc = desc;
		    this.timestamp = timestamp;
		    this.comments = comments;
		    this.commentList = commentList;
		    this.imageId = image;
		    this.fid = fId;
		    selected = false;
		  }
	  //
	  
	  public void setFID(String fid)
	  {
		  this.fid = fid;
	  }
	  
	  public String getFID()
	  {
		  return this.fid;
	  }
	  
	  public int getId() {
		  return id;
	  }
	  
	  public void setId(int id) {
		  this.id=id;
	  }
	  
	  public void setTimestamp(long timestamp)
	  {
		  this.timestamp = timestamp;
	  }
	  
	  public long getTimestamp()
	  {
		  return this.timestamp;
	  }
	  
	  /*
	  public String getCeleb() {
		  return celeb;
	  }
	  
	  public void setCeleb(String id) {
		  this.celeb = id;
	  }
	  */
	  
	  public List<String> getCommentList() {
		  return commentList;
	  }
	  
	  public void setCommentList(List<String> commentList) {
		  this.commentList = commentList;
	  }
	  
	  public String getAllComments() {
		  Gson gson = new Gson();
		  String json = gson.toJson(commentList);
		  return json;
	  }
	  
	  public void setAllComments(String commentList) {
		  Gson gson = new Gson();
		    @SuppressWarnings("unchecked")
			List<String> obj = gson.fromJson(commentList, ArrayList.class);
		  this.commentList = obj;
	  }
	  
	  //this is a URI
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
	  
	// Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return desc;
	  }
	
}
