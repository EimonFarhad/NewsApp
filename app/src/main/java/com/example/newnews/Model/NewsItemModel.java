package com.example.newnews.Model;

public class NewsItemModel {
   String name,title,description,imgUrl;

   public String getName() {
      return name;
   }

   public String getTitle() {
      return title;
   }

   public String getDescription() {
      return description;
   }

   public String getImgUrl() {
      return imgUrl;
   }

   public NewsItemModel(String name, String title, String description, String imgUrl) {
      this.name = name;
      this.title = title;
      this.description = description;
      this.imgUrl = imgUrl;
   }
}
