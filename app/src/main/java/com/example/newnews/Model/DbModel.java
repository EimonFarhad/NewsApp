package com.example.newnews.Model;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbModel extends SQLiteOpenHelper {
   public static final String DATABASE_NAME = "news.db";
   public static final int DATABASE_VERSION = 1;
   public static final String TABLE_NAME = "news";
   public static final String COLUMN_NEWS_ID = "user_id";
   public static final String COLUMN_NEWS_SOURCE = "source";
   public static final String COLUMN_IMG_URL = "img_url";
   public static final String COLUMN_TITLE = "title";
   public static final String COLUMN_DESCRIPTION = "description";

   Activity activity;

   public DbModel(Activity  activity){
      super(activity, DATABASE_NAME, null, DATABASE_VERSION);
      this.activity=activity;
   }
   @Override
   public void onCreate(SQLiteDatabase sqLiteDatabase) {
      sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (" + COLUMN_NEWS_ID + " integer primary key autoincrement, " +
              COLUMN_NEWS_SOURCE + " varchar, " +
              COLUMN_IMG_URL + " varchar, " +
              COLUMN_TITLE + " varchar, " +
              COLUMN_DESCRIPTION + " varchar)");
   }

   @Override
   public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
      sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);

   }
}
