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
    private MutableLiveData<Integer> positionSelected;
    private ArrayList<ShoppingItem> shoppingList = new ArrayList<>();

    public MainViewModel(@NonNull Application application,Activity activity) {
        super(application);
        this.activity = activity;
        this.context = application;
        itemsLiveData = new MutableLiveData<>();
        positionSelected = new MutableLiveData<>();
        positionSelected.setValue(-1);
        getItemsListByFile();
    }

    public static MainViewModel getInstance(Application application, Activity activity){
        if(instance ==null){
            instance = new MainViewModel(application, activity);
        }
        return instance;
    }

    public void setPositionSelected(Integer index){
        positionSelected.setValue(index);
    }

    public MutableLiveData<Integer> getPositionSelected(){
        return positionSelected;
    }

    public MutableLiveData<ArrayList<ShoppingItem>> getItemsLiveData() {
        return itemsLiveData;
    }

    public void removeItemFromList(String name)
    {
        for(ShoppingItem item : shoppingList){
            if (item.getName().equals(name)){
                shoppingList.remove(item);
            }
        }
        saveItemsListToFile();
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
                this.itemsLiveData.setValue(ret);

            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public void setItemsListByFile(String name, String quantity)
    {
        boolean bExist = false;
        clearListByFile();   // clear file before writing
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("shoppingList.txt", Context.MODE_PRIVATE));
            shoppingList = getItemsListByFile();
            for (int i = 0; i < shoppingList.size(); i++) {
                if (shoppingList.get(i).getName().equals(name)) { // if list contains the product, just update quantity
                    shoppingList.get(i).setQuantity(Integer.valueOf(quantity));
                    bExist = true;
                }
                // then write to file  -- need to delete before or auto??
                outputStreamWriter.append(shoppingList.get(i).getName() + " " + shoppingList.get(i).getQuantity() + "\n");
            }
            if(!bExist){
                ShoppingItem newItem = new ShoppingItem(name, Integer.valueOf(quantity));
                shoppingList.add(newItem);
                outputStreamWriter.append(name + " " + quantity + "\n");

            }
            this.itemsLiveData.setValue(shoppingList);
            outputStreamWriter.flush();
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }

    public void saveItemsListToFile()
    {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("shoppingList.txt", Context.MODE_PRIVATE));
            shoppingList = getItemsListByFile();
            for (int i = 0; i < shoppingList.size(); i++)
                outputStreamWriter.write(shoppingList.get(i).getName() + " " + shoppingList.get(i).getQuantity() + "\n");

            outputStreamWriter.flush();
            outputStreamWriter.close();
            this.itemsLiveData.setValue(shoppingList);
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
