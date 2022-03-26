package com.example.newnews;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newnews.Model.NewsItemModel;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    Activity activity;
    ArrayList<NewsItemModel> arrayList;

    public NewsAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.newsitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsItemModel itemModel = arrayList.get(position);
        holder.news_source.setText(itemModel.getName());
        holder.news_title.setText(itemModel.getTitle());
        if (itemModel.getImgUrl() != null) {
            try {
                Glide.with(activity)
                        .load(itemModel.getImgUrl())
                        .into(holder.img_news);
            }catch (Exception e){
                Log.e("",e.toString()) ;
            }

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setData(ArrayList<NewsItemModel> arrayList) {
        this.arrayList.clear();
        this.arrayList.addAll(arrayList);
        notifyDataSetChanged();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView news_title, news_source;
        ImageView img_news;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            news_source = itemView.findViewById(R.id.news_source);
            news_title = itemView.findViewById(R.id.news_title);
            img_news = itemView.findViewById(R.id.img_news);

        }
    }
}
