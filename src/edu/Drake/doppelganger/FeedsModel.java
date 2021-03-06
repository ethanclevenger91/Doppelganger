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
	  private String read;
	  private int downs;
	  private int comments;
	  private String tag;
	  private String imageId; // photo from camera or gallery
	  private String desc;
	  private List<String> commentList;
	  private String celeb;

	  public FeedsModel() {
		  
	  }
	  
	  public FeedsModel(String name, String celeb, int ups, int downs, int comments, String image, String desc, List<String> commentList, String fid, Long timestamp, String tag, String read) {
	    this.name = name;
	    this.fid = fid;
	    this.ups = ups;
	    this.timestamp = timestamp;
	    this.downs=downs;
	    this.comments=comments;
	    this.imageId = image;
	    this.read = read;
	    this.desc = desc;
	    this.tag = tag;
	    this.commentList = commentList;
	    this.celeb = celeb;
	    selected = false;
	  }
	  
	  public FeedsModel(int id, String desc, String name, String celeb, int likes, int dislikes, int comments, String allComments, String image, String fid, Long timestamp, String tag, String read) {
		  	this.id = id;
		  	this.fid = fid;
		    this.name = name;
		    this.ups = likes;
		    this.downs=dislikes;
		    this.read = read;
		    this.tag = tag;
		    this.timestamp = timestamp;
		    this.desc = desc;
		    this.comments = comments;
		    this.imageId = image;
		    this.celeb = celeb;
		    Gson gson = new Gson();
		    @SuppressWarnings("unchecked")
			List<String> obj = gson.fromJson(allComments, ArrayList.class);
		    
		    this.commentList = obj;
		    selected = false;
		  }
	  
	  public FeedsModel(int id, String desc, String name, String celeb, int likes, int dislikes, int comments, List<String> allComments, String image, String fid, Long timestamp, String tag, String read) {
		  	this.id = id;
		    this.name = name;
		    this.ups = likes;
		    this.downs=dislikes;
		    this.desc = desc;
		    this.timestamp = timestamp;
		    this.tag = tag;
		    this.comments = comments;
		    this.read = read;
		    this.fid = fid;
		    this.commentList = allComments;
		    this.imageId=image;
		    this.celeb = celeb;
		    selected = false;
		  }
	  
	  public FeedsModel(String desc, String name, String celeb, int likes, int dislikes, int comments, List<String> commentList, String image, String fId, Long timestamp, String tag, String read) {
		    this.name = name;
		    this.ups = likes;
		    this.downs=dislikes;
		    this.desc = desc;
		    this.tag = tag;
		    this.read = read;
		    this.timestamp = timestamp;
		    this.comments = comments;
		    this.commentList = commentList;
		    this.imageId = image;
		    this.fid = fId;
		    this.celeb = celeb;
		    selected = false;
		  }
	  //
	  
	  public void setFID(String fid)
	  {
		  this.fid = fid;
	  }
	  
	  public void setRead(String read)
	  {
		  this.read = read;
	  }
	  
	  public String getRead()
	  {
		  return this.read;
	  }
	  
	  public String getFID()
	  {
		  return this.fid;
	  }
	  
	  public void setTag(String tag)
	  {
		  this.tag =tag;
	  }
	  
	  public String getTag()
	  {
		  return this.tag;
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
	  
	  
	  public String getCeleb() {
		  return celeb;
	  }
	  
	  public void setCeleb(String celeb) {
		  this.celeb = celeb;
	  }
	  
	  
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
	  
	  public void decrementUp() {
		  this.ups--;
	  }
	  
	  public void incrementDown() {
		  this.downs++;
	  }
	  
	  public void decrementDown() {
		  this.downs--;
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
