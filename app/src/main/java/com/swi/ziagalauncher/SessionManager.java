package com.swi.ziagalauncher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ditya Geraldy on 07 February 2019.
 */

public class SessionManager {

    private SharedPreferences.Editor editor;
    private SharedPreferences sharePref;
    private static final String PREF_NAME = "APPlauncher";

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        sharePref = context.getSharedPreferences(PREF_NAME, 0); //private mode
        editor = sharePref.edit();
    }

    public void setPassword(String password){
        editor.putString("password", password);
        editor.commit();
    }

    public String getPassword(){
        return sharePref.getString("password", "000000");
    }

    public void setMenuApp(String menuApp){
        editor.putString("menuApp", menuApp);
        editor.commit();
    }

    public String getMenuApp(){
        return sharePref.getString("menuApp", "");
    }

    public void seAdsTime(int adsTime){
        editor.putInt("adsTime", adsTime);
        editor.commit();
    }

    public int getAdsTime(){
        return sharePref.getInt("adsTime", 30000);
    }

    public void setTotalAds(int totalAds){
        editor.putInt("totalAds", totalAds);
        editor.commit();
    }

    public int getTotalAds(){
        return sharePref.getInt("totalAds", 0);
    }

}