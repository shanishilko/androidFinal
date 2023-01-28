package com.example.myfinalproject;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShoppingListFrag extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.shopping_list_fragment, container, false);
    }




//    private int getColorFromRaw(View view) {
//        FileInputStream fis = null;
//        int color = Color.RED;
//        try {
//            fis = getApplication().openFileInput(FILE_NAME);
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader br = new BufferedReader(isr);
//            String tempColor;
//            while((tempColor = br.readLine()) != null){
//                String[] tokens = tempColor.split(" ");
//                if(tokens[0].equals("color"))
//                    color =Integer.parseInt(tokens[1]);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return color;
//    }
}
