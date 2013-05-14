package edu.Drake.doppelganger;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FeedSQLiteHelper extends SQLiteOpenHelper {

  //Table Name
  public static final String TABLE_POSTS = "comments";
  
  //Table Column names
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_CAPTION = "caption";
  public static final String COLUMN_NAME= "name";
  public static final String COLUMN_LIKE="like";
  public static final String COLUMN_DISLIKE="dislike";
  public static final String COLUMN_COMMENTS = "columns";
  public static final String COLUMN_ALL_COMMENTS = "allComments";
  public static final String COLUMN_PHOTO = "photo";
  public static final String COLUMN_CELEB = "celeb";
  public static final String COLUMN_FID = "facebook_id";
  public static final String COLUMN_TIME = "timestamp";
  public static final String COLUMN_TAG	 = "tag";
  public static final String COLUMN_READ = "isRead";

  private static final String DATABASE_NAME = "commments.db";
  private static final int DATABASE_VERSION = 11;
  
  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_POSTS + "(" + COLUMN_ID
      + " integer primary key autoincrement, " + COLUMN_CAPTION
      + " text not null, " + COLUMN_NAME + " text not null, " 
      + COLUMN_LIKE + " integer not null, " + COLUMN_DISLIKE + " integer not null, "
      + COLUMN_COMMENTS + " integer not null, " + COLUMN_ALL_COMMENTS + " text, " 
      + COLUMN_PHOTO + " text, " + COLUMN_CELEB + " text, " + COLUMN_FID + " text, " 
      + COLUMN_TIME + " long, " + COLUMN_TAG + " text, " + COLUMN_READ + " text);";

  public FeedSQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(FeedSQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    //if(oldVersion <6) {
    	final String ALTER_TBL = "ALTER TABLE " + TABLE_POSTS + " ADD COLUMN " + COLUMN_READ + " text;";
    	db.execSQL(ALTER_TBL);
    //}
  }
  
//Adding new contact
public void addContact(FeedsModel contact) {
	SQLiteDatabase db = this.getWritableDatabase();
	 
    ContentValues values = new ContentValues();
    values.put(COLUMN_NAME, contact.getName());
    values.put(COLUMN_CAPTION, contact.getDesc());
    values.put(COLUMN_LIKE, contact.getUps());
    values.put(COLUMN_DISLIKE, contact.getDowns());
    values.put(COLUMN_COMMENTS, contact.getComments());
    values.put(COLUMN_ALL_COMMENTS, contact.getAllComments());
    values.put(COLUMN_PHOTO, contact.getImageId());
    values.put(COLUMN_CELEB, contact.getCeleb());
    values.put(COLUMN_FID, contact.getFID());
    values.put(COLUMN_TIME, contact.getTimestamp());
    values.put(COLUMN_TAG, contact.getTag());
    values.put(COLUMN_READ, contact.getRead());
    
    // Inserting Row
    db.insert(TABLE_POSTS, null, values);
    db.close(); // Closing database connection
}

//Getting single contact
public FeedsModel getContact(int id) {
	SQLiteDatabase db = this.getReadableDatabase();
	 
    Cursor cursor = db.query(TABLE_POSTS, new String[] { COLUMN_ID,
            COLUMN_CAPTION, COLUMN_NAME, COLUMN_LIKE, COLUMN_DISLIKE, COLUMN_COMMENTS, COLUMN_ALL_COMMENTS, COLUMN_PHOTO, COLUMN_FID, COLUMN_TIME, COLUMN_CELEB, COLUMN_TAG, COLUMN_READ }, COLUMN_ID + "=?",
            new String[] { String.valueOf(id) }, null, null, null, null);
    if (cursor != null)
        cursor.moveToFirst();
 
    FeedsModel contact = new FeedsModel(
    		Integer.parseInt(cursor.getString(0)), 
    		cursor.getString(1), 
    		cursor.getString(2), 
    		cursor.getString(8),
    		Integer.parseInt(cursor.getString(3)), 
    		Integer.parseInt(cursor.getString(4)),
    		Integer.parseInt(cursor.getString(5)),
    		cursor.getString(6),
    		cursor.getString(7),
    		cursor.getString(9),
    		Long.parseLong(cursor.getString(10)),
    		cursor.getString(11),
    		cursor.getString(12));
    		
    
    // return contact
    return contact;
}

//filters the news feed for the 'Me' option
public List<FeedsModel> getMe(String me)
{
	List<FeedsModel> contactList = new ArrayList<FeedsModel>();
	
	String selectQuery = "SELECT * FROM " + TABLE_POSTS + " WHERE " + COLUMN_FID + "= "+ me;
	Log.v("ethan", selectQuery);
	
	SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
 
    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
            FeedsModel contact = new FeedsModel();
            contact.setId(Integer.parseInt(cursor.getString(0)));
            contact.setDesc(cursor.getString(1));
            contact.setName(cursor.getString(2));
            contact.setUps(Integer.parseInt(cursor.getString(3)));
            contact.setDowns(Integer.parseInt(cursor.getString(4)));
            contact.setComments(Integer.parseInt(cursor.getString(5)));
            contact.setAllComments(cursor.getString(6));
            contact.setImageId(cursor.getString(7));
            contact.setCeleb(cursor.getString(8));
            contact.setFID(cursor.getString(9));
            contact.setTimestamp(Long.parseLong(cursor.getString(10)));
            contact.setTag(cursor.getString(11));
            contact.setRead(cursor.getString(12));
            
            // Adding contact to list
            contactList.add(contact);
        } while (cursor.moveToNext());
    }
 
    // return contact list
    return contactList;
}

public List<FeedsModel> getNotifications(String myTag)
{
List<FeedsModel> contactList = new ArrayList<FeedsModel>();

String selectQuery = "SELECT * FROM " + TABLE_POSTS + " WHERE " + COLUMN_TAG + "= "+ myTag;
Log.v("ethan", selectQuery);

SQLiteDatabase db = this.getWritableDatabase();
Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
if (cursor.moveToFirst()) {
    do {
        FeedsModel contact = new FeedsModel();
        contact.setId(Integer.parseInt(cursor.getString(0)));
        contact.setDesc(cursor.getString(1));
        contact.setName(cursor.getString(2));
        contact.setUps(Integer.parseInt(cursor.getString(3)));
        contact.setDowns(Integer.parseInt(cursor.getString(4)));
        contact.setComments(Integer.parseInt(cursor.getString(5)));
        contact.setAllComments(cursor.getString(6));
        contact.setImageId(cursor.getString(7));
        contact.setCeleb(cursor.getString(8));
        contact.setFID(cursor.getString(9));
        contact.setTimestamp(Long.parseLong(cursor.getString(10)));
        contact.setTag(cursor.getString(11));
        contact.setRead(cursor.getString(12));
        
        // Adding contact to list
        contactList.add(contact);
    } while (cursor.moveToNext());
}

// return contact list
return contactList;
}

public List<FeedsModel> getCeleb(String celeb)
{
	List<FeedsModel> contactList = new ArrayList<FeedsModel>();
	
	String selectQuery = "SELECT * FROM " + TABLE_POSTS + " WHERE " + COLUMN_CELEB + "= '" + celeb + "'";
	Log.v("ethan", selectQuery);
	
	SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
 
    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
            FeedsModel contact = new FeedsModel();
            contact.setId(Integer.parseInt(cursor.getString(0)));
            contact.setDesc(cursor.getString(1));
            contact.setName(cursor.getString(2));
            contact.setUps(Integer.parseInt(cursor.getString(3)));
            contact.setDowns(Integer.parseInt(cursor.getString(4)));
            contact.setComments(Integer.parseInt(cursor.getString(5)));
            contact.setAllComments(cursor.getString(6));
            contact.setImageId(cursor.getString(7));
            contact.setCeleb(cursor.getString(8));
            contact.setFID(cursor.getString(9));
            contact.setTimestamp(Long.parseLong(cursor.getString(10)));
            contact.setTag(cursor.getString(11));
            contact.setRead(cursor.getString(12));
            
            // Adding contact to list
            contactList.add(contact);
        } while (cursor.moveToNext());
    }
 
    // return contact list
    return contactList;
}

//filters the news feed for the 'Me' option
public List<FeedsModel> getMostRecent()
{
	List<FeedsModel> contactList = new ArrayList<FeedsModel>();
	
	String selectQuery = "SELECT * FROM " + TABLE_POSTS + " ORDER BY " + COLUMN_TIME + " DESC";
	
	SQLiteDatabase db = this.getWritableDatabase();
  Cursor cursor = db.rawQuery(selectQuery, null);

  // looping through all rows and adding to list
  if (cursor.moveToFirst()) {
      do {
          FeedsModel contact = new FeedsModel();
          contact.setId(Integer.parseInt(cursor.getString(0)));
          contact.setDesc(cursor.getString(1));
          contact.setName(cursor.getString(2));
          contact.setUps(Integer.parseInt(cursor.getString(3)));
          contact.setDowns(Integer.parseInt(cursor.getString(4)));
          contact.setComments(Integer.parseInt(cursor.getString(5)));
          contact.setAllComments(cursor.getString(6));
          contact.setImageId(cursor.getString(7));
          contact.setCeleb(cursor.getString(8));
          contact.setFID(cursor.getString(9));
          contact.setTimestamp(Long.parseLong(cursor.getString(10)));
          contact.setTag(cursor.getString(11));
          contact.setRead(cursor.getString(12));
          // Adding contact to list
          contactList.add(contact);
      } while (cursor.moveToNext());
  }

  // return contact list
  return contactList;
}

//Getting All Contacts
public List<FeedsModel> getAllContacts() {
	
	List<FeedsModel> contactList = new ArrayList<FeedsModel>();
    // Select All Query
    String selectQuery = "SELECT  * FROM " + TABLE_POSTS + " ORDER BY " + COLUMN_LIKE + " DESC";
 
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
 
    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
            FeedsModel contact = new FeedsModel();
            contact.setId(Integer.parseInt(cursor.getString(0)));
            contact.setDesc(cursor.getString(1));
            contact.setName(cursor.getString(2));
            contact.setUps(Integer.parseInt(cursor.getString(3)));
            contact.setDowns(Integer.parseInt(cursor.getString(4)));
            contact.setComments(Integer.parseInt(cursor.getString(5)));
            contact.setAllComments(cursor.getString(6));
            contact.setImageId(cursor.getString(7));
            contact.setCeleb(cursor.getString(8));
            contact.setFID(cursor.getString(9));
            contact.setTimestamp(Long.parseLong(cursor.getString(10)));
            contact.setTag(cursor.getString(11));
            contact.setRead(cursor.getString(12));
           
            // Adding contact to list
            contactList.add(contact);
        } while (cursor.moveToNext());
    }
 
    // return contact list
    return contactList;
	
}

//Getting contacts Count
public int getContactsCount() {
	
	String countQuery = "SELECT  * FROM " + TABLE_POSTS;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(countQuery, null);
    cursor.close();

    // return count
    return cursor.getCount();
	
}
//Updating single contact
public int updateContact(FeedsModel contact) {
	
	SQLiteDatabase db = this.getWritableDatabase();
	 
    ContentValues values = new ContentValues();
    values.put(COLUMN_NAME, contact.getName());
    values.put(COLUMN_CAPTION, contact.getDesc());
    values.put(COLUMN_LIKE, contact.getUps());
    values.put(COLUMN_DISLIKE, contact.getDowns());
    values.put(COLUMN_COMMENTS, contact.getComments());
    values.put(COLUMN_ALL_COMMENTS, contact.getAllComments());
    values.put(COLUMN_PHOTO, contact.getImageId());
    values.put(COLUMN_FID, contact.getFID());
    values.put(COLUMN_TIME, contact.getTimestamp());
    values.put(COLUMN_CELEB, contact.getCeleb());
    values.put(COLUMN_TAG, contact.getTag());
    values.put(COLUMN_READ, contact.getRead());
 
    // updating row
    return db.update(TABLE_POSTS, values, COLUMN_ID + " = ?",
            new String[] { String.valueOf(contact.getId()) });
	
}

//Deleting single contact
public void deleteContact(FeedsModel contact) {
	
	SQLiteDatabase db = this.getWritableDatabase();
    db.delete(TABLE_POSTS, COLUMN_ID + " = ?",
            new String[] { String.valueOf(contact.getId()) });
    db.close();
	
}

} 