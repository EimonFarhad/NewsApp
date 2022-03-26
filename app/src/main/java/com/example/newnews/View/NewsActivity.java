package com.example.newnews.View;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.newnews.Controller.AppController;
import com.example.newnews.Controller.DbController;
import com.example.newnews.Controller.VolleySingleton;
import com.example.newnews.Model.NewsItemModel;
import com.example.newnews.NewsAdapter;
import com.example.newnews.R;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class NewsActivity extends AppCompatActivity {
    RecyclerView newsListRecycler;
    private String TAG = NewsActivity.class.getSimpleName();
    private String url = "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=e5e3a444364b42f0829f035765ce82e9";
    ArrayList<NewsItemModel> arrayList;
    DbController dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsListRecycler = findViewById(R.id.newsList);

        arrayList = new ArrayList<>();
        dbController = new DbController(this);
        dbController.open();
        if (VolleySingleton.getInstance(this).isConnected()) {
            getDataFromApi();
        } else {
            getDataFromDb();
        }

    }

    private void getDataFromDb() {
        arrayList.addAll(dbController.getNewsromDatabase());
        newsListRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        NewsAdapter adapter = new NewsAdapter(this);
        adapter.setData(arrayList);
        newsListRecycler.setAdapter(adapter);
    }

    private void getDataFromApi() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "e5e3a444364b42f0829f035765ce82e9");
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject object = response.getJSONObject("JSON");
                            if (object.getString("status").equalsIgnoreCase("ok")) {
                                JSONArray jsonArray = object.getJSONArray("articles");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object1 = jsonArray.getJSONObject(i);
                                    JSONObject object2 = object1.getJSONObject("source");
                                    arrayList.add(new NewsItemModel(object2.getString("name"),
                                            object1.getString("title"),
                                            object1.getString("description"),
                                            object1.getString("urlToImage")));
                                }
                            }
                            dbController.deleteData();
                            dbController.insertData(arrayList);
                            arrayList.clear();
                            getDataFromDb();
                        } catch (Exception e) {

                        }
                    }
                }, error -> Log.e(TAG, error.toString())) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);
    }

}