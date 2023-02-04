package com.example.myfinalproject;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Map;

public class MainViewModel extends AndroidViewModel {
    private static MainViewModel instance;
    public Activity activity;
    public Context context;
    private MutableLiveData<ArrayList<ShoppingItem>> itemsLiveData ;
    private ArrayList<ShoppingItem> shoppingList = new ArrayList<>();

    public MainViewModel(@NonNull Application application,Activity activity) {
        super(application);
        this.activity = activity;
        this.context = application;
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

    // ******************* raw file **********************

    public ArrayList<ShoppingItem> getItemsListByFile() {
        ArrayList<ShoppingItem> ret = new ArrayList<>();

        try {
            InputStream inputStream = context.openFileInput("shoppingList.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int size = inputStream.available();
                char[] buffer = new char[size];

                inputStreamReader.read(buffer);
                inputStream.close();

                String parts[] = buffer.toString().split("\n");
                for (String part : parts)
                {
                    String name_quantity[] = part.split(" ");
                    ShoppingItem item = new ShoppingItem(name_quantity[0], Integer.parseInt(name_quantity[1]));
                    ret.add(item);
                }

            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public void setItemsListByFile(String name, int quantity)
    {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("shoppingList.txt", Context.MODE_PRIVATE));

            for (int i = 0; i < shoppingList.size(); i++) {
                if (shoppingList.get(i).getName().equals(name))   // if list contains the product, just update quantity
                    shoppingList.get(i).setQuantity(quantity);
                else {
                    ShoppingItem newItem = new ShoppingItem(name, quantity);
                    shoppingList.add(newItem);
                }
                // then write to file
                outputStreamWriter.write(shoppingList.get(i).getName() + " " + shoppingList.get(i).getQuantity() + "\n");
            }

            outputStreamWriter.flush();
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }


    private void clearListByFile() {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("shoppingList.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write("");
            outputStreamWriter.flush();
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }
}
