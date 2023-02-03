package com.example.myfinalproject;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class MainViewModel extends AndroidViewModel {
    private static MainViewModel instance;
    public Activity activity;
    private MutableLiveData<ArrayList<ShoppingItem>> itemsLiveData ;

    public MainViewModel(@NonNull Application application,Activity activity) {
        super(application);
        this.activity = activity;
        itemsLiveData = new MutableLiveData<>();
    }

    public static MainViewModel getInstance(Application application, Activity activity){
        if(instance ==null){
            instance = new MainViewModel(application, activity);
        }
        return instance;
    }



    public MutableLiveData<ArrayList<ShoppingItem>> getItemsLiveData() {
        return itemsLiveData;
    }

    // ******************* SP **********************
//    public void setItemsListBySP(String name) {
//
//        if (!removedCountryList.contains(name)) {
//            String removelist = getRemoveListBySP();
//            if (removelist.length() == 0)
//                removelist = name;
//            else
//                removelist += "," + name;
//
//            SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPref.edit();
//            editor.putString("removelist", removelist);
//            editor.apply();
//        }
//    }
//
//    public String getItemsListBySP() {
//
//        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
//        return sharedPref.getString("removelist", "");
//    }
//
//    private void clearItemsListBySP() {
//
//        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString("removelist", "");
//        editor.apply();
//    }
}
