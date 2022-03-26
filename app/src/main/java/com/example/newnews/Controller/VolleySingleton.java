package com.example.newnews.Controller;

import android.app.Activity;
import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class VolleySingleton {

   private static VolleySingleton mInstance;
   private RequestQueue requestQueue ;
   private static Context mCtx;

   public VolleySingleton(Context context) {
      this.mCtx = context;
      requestQueue= getRequestQueue();
   }

   public static synchronized VolleySingleton getInstance(Context context) {
      if (mInstance == null) {
         mInstance = new VolleySingleton(context);
      }
      return mInstance;
   }
   public RequestQueue getRequestQueue(){
      if (requestQueue == null) {
         // getApplicationContext() is key, it keeps you from leaking the
         // Activity or BroadcastReceiver if someone passes one in.
         requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
      }
      return requestQueue;
   }
   public <T> void addToRequestQueue(Request<T> req) {
      getRequestQueue().add(req);
   }

   public  boolean isConnected() {
      ConnectivityManager cm = (ConnectivityManager) mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
      if (activeNetwork != null) {
         if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
         } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
         }
      } else {
         return false;
      }
      return false;
   }

}