package com.example.myfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {
    private static final int RECEIVE_SMS_REQUEST_CODE   = 1;
    private static final int READ_SMS_REQUEST_CODE      = 2;
    private static final String FILE_NAME = "rawColor.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askForSmsDangerousPermissions();

    }



    private void askForSmsDangerousPermissions() {
        requestSmsDangerousPermission(android.Manifest.permission.READ_SMS, READ_SMS_REQUEST_CODE);
        requestSmsDangerousPermission(Manifest.permission.RECEIVE_SMS, RECEIVE_SMS_REQUEST_CODE);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // This is where you would put your code that needs to be executed
        // after the activity has been fully initialized
        int color = getColorFromRaw();
        View frag = findViewById(R.id.relativeLayout);
        frag.setBackgroundColor(color);
    }


    private void requestSmsDangerousPermission(String permission, int permissionRequestCode)
    {
        // check if permission already granted
        if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED)
            return;

        // Permission is not granted. show an explanation.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
            Toast.makeText(this, "You must grant this permission in order to see SMS messages", Toast.LENGTH_LONG).show();

        // request the permission
        ActivityCompat.requestPermissions(this, new String[] { permission }, permissionRequestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 0)
            return;

        boolean firstPermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

        switch (requestCode) {
            case RECEIVE_SMS_REQUEST_CODE:
                Toast.makeText(this, "RECEIVE_SMS permission granted: " + firstPermissionGranted, Toast.LENGTH_LONG).show();
                break;
            case READ_SMS_REQUEST_CODE:
                Toast.makeText(this, "READ_SMS permission granted: " + firstPermissionGranted, Toast.LENGTH_LONG).show();
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        inflater.inflate(R.menu.menu_exit, menu);
        inflater.inflate(R.menu.add_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                openColorPicker();
                break;
            case R.id.exit:
                MyExit exitFragment = MyExit.newInstance();
                exitFragment.show(getSupportFragmentManager(), "exitDialog");
                break;
            case R.id.addItem:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main, new AddItemFragment())
                        .addToBackStack(null)
                        .commit();
                break;

        }
        return true;
    }


    private void openColorPicker() {
        //need to read from the sp
        int color = getColorFromRaw();
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, color /*initialColor*/, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                // user cancelled the dialog
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                // user selected a color

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplication());
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("color",color);
                editor.apply();
//                FileOutputStream fos = null;
//                try {
//                    fos = getApplication().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
//                    String s ="color " + Integer.toString(color);
//                    fos.write(s.getBytes());
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                finally {
//                    if (fos != null) {
//                        try {
//                            fos.close();
//                        } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

                View frag = findViewById(R.id.relativeLayout);
                frag.setBackgroundColor(color);
            }
        });
        colorPicker.show();
    }

    private int getColorFromRaw() {

        int color = Color.WHITE;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        color = sharedPref.getInt("color",color);

//        FileInputStream fis = null;
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

        return color;
    }

}