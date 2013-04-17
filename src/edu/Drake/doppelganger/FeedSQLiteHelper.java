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

  private static final String DATABASE_NAME = "commments.db";
  private static final int DATABASE_VERSION = 2;
  
  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_POSTS + "(" + COLUMN_ID
      + " integer primary key autoincrement, " + COLUMN_CAPTION
      + " text not null, " + COLUMN_NAME + " text not null, " 
      + COLUMN_LIKE + " integer not null, " + COLUMN_DISLIKE + " integer not null, "
      + COLUMN_COMMENTS + " integer not null);";

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
    if(oldVersion <2) {
    	final String ALTER_TBL = "ALTER TABLE " + TABLE_POSTS + " ADD COLUMN " + COLUMN_COMMENTS + " integer not null;";
    	db.execSQL(ALTER_TBL);
    }
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
 
    // Inserting Row
    db.insert(TABLE_POSTS, null, values);
    db.close(); // Closing database connection
}

//Getting single contact
public FeedsModel getContact(int id) {
	SQLiteDatabase db = this.getReadableDatabase();
	 
    Cursor cursor = db.query(TABLE_POSTS, new String[] { COLUMN_ID,
            COLUMN_CAPTION, COLUMN_NAME, COLUMN_LIKE, COLUMN_DISLIKE, COLUMN_COMMENTS }, COLUMN_ID + "=?",
            new String[] { String.valueOf(id) }, null, null, null, null);
    if (cursor != null)
        cursor.moveToFirst();
 
    FeedsModel contact = new FeedsModel(
    		Integer.parseInt(cursor.getString(0)), 
    		cursor.getString(1), 
    		cursor.getString(2), 
    		Integer.parseInt(cursor.getString(3)), 
    		Integer.parseInt(cursor.getString(4)),
    		Integer.parseInt(cursor.getString(5)));
    
    // return contact
    return contact;
}

//Getting All Contacts
public List<FeedsModel> getAllContacts() {
	
	List<FeedsModel> contactList = new ArrayList<FeedsModel>();
    // Select All Query
    String selectQuery = "SELECT  * FROM " + TABLE_POSTS;
 
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