package com.example.newnews.Controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.newnews.Model.DbModel;
import com.example.newnews.Model.NewsItemModel;


import java.util.ArrayList;

public class DbController {
   private SQLiteDatabase database;
   private DbModel dbModel;

   public DbController(Activity activity){
      dbModel =new DbModel(activity);
   }
   public void open()throws SQLException {
      database = dbModel.getWritableDatabase();
   }
   public void insertData(ArrayList<NewsItemModel>arrayList){
      ContentValues cv ;
      for (int i=0;i<arrayList.size();i++){
         cv=new ContentValues();
         cv.put(DbModel.COLUMN_TITLE,arrayList.get(i).getTitle());
         cv.put(DbModel.COLUMN_DESCRIPTION,arrayList.get(i).getDescription());
         cv.put(DbModel.COLUMN_IMG_URL,arrayList.get(i).getImgUrl());
         cv.put(DbModel.COLUMN_NEWS_SOURCE,arrayList.get(i).getName());
         long j= database.insert(DbModel.TABLE_NAME, null, cv);
      }

   }

   public ArrayList<NewsItemModel>getNewsromDatabase(){
      ArrayList<NewsItemModel>arrayList=new ArrayList<>();

      Cursor cursor = database.rawQuery("SELECT * FROM " + DbModel.TABLE_NAME, null);
      if (cursor.moveToFirst()) {
         do {
            arrayList.add(new NewsItemModel(cursor.getString(1),
                    cursor.getString(3),
                    cursor.getString(2),
                    cursor.getString(4)));
         } while (cursor.moveToNext());

      }
      cursor.close();
      return arrayList;
   }
   public void deleteData(){
      database.execSQL("delete from "+ DbModel.TABLE_NAME);
   }



}
