package com.example.myfinalproject;





import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.TimeZone;

public class ShoppingListFrag extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private ShoppingItemsAdapter itemsAdapter;
    private TextView txtView_Date, txtView_Time;
    private Button alarmServiceBtn, clearListBtn;
    private boolean flagForDate = false;
    private boolean flagForTime = false;
    private boolean flagAlarm = false;
    private MainViewModel mainViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.shopping_list_fragment, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        itemsAdapter = new ShoppingItemsAdapter(getActivity().getApplication(), getContext(), getActivity(), this); // create an instance of the adapter
        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setAdapter(itemsAdapter); // set the instance of the adapter to the recyclerView
        /// check if row below needed !!!!!!!!!
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); // What is the position of the list vertical or linear
        txtView_Date = (TextView) view.findViewById(R.id.text_view_Date);
        txtView_Time= (TextView) view.findViewById(R.id.text_view_Time);
        alarmServiceBtn = (Button) view.findViewById(R.id.btnSaveDateAndTime);
        clearListBtn =(Button) view.findViewById(R.id.btnClearList);
        mainViewModel = MainViewModel.getInstance(getActivity().getApplication(), getActivity());

        alarmServiceBtn.setOnClickListener(this);
        int color = MainActivity.getColorFromSP();
        View frag = view.findViewById(R.id.relativeLayout);
        frag.setBackgroundColor(color);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        flagAlarm = sharedPref.getBoolean("alarm",false);
        if(flagAlarm== true){
            txtView_Time.setText(sharedPref.getString("Time",null));
            txtView_Date.setText(sharedPref.getString("Date",null));
            txtView_Time.setTextColor(Color.parseColor("#FF0000"));
            txtView_Date.setTextColor(Color.parseColor("#FF0000"));
            alarmServiceBtn.setText(getResources().getString(R.string.txt_Alarm_OFF));
            alarmServiceBtn.setVisibility(View.VISIBLE);
        }

        clearListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage(R.string.remove_list_btnDialog);
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mainViewModel.clearListByFile();
                                alarmServiceBtn.setVisibility(View.INVISIBLE);
                                txtView_Date.setText("Select");
                                txtView_Date.setTextColor(Color.parseColor("#B1A1A1"));
                                txtView_Time.setText("Select");
                                txtView_Time.setTextColor(Color.parseColor("#B1A1A1"));

                                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("Time",null);
                                editor.putString("Date",null);
                                editor.putBoolean("alarm",false);
                                editor.apply();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });


        // Date Picker
        txtView_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getTimeZone("GMT+3"));

                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), R.style.DateTimeDialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                month = month + 1;
//                                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                                String date = day + "/" + month + "/" + year;
                                txtView_Date.setText(date);
                                txtView_Date.setTextColor(Color.parseColor("#db5a6b"));
                                flagForDate = true;
                                if (flagForTime == true)
                                    alarmServiceBtn.setVisibility(View.VISIBLE);
                            }
                        },
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextColor(Color.BLACK);

                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setTextColor(Color.BLACK);

            }
        });

        // Time Picker
        txtView_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getTimeZone("GMT+3"));

                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(getContext(), R.style.DateTimeDialog,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                String time = String.format("%02d", sHour) + ":" + String.format("%02d", sMinute);
                                txtView_Time.setText(time);
                                txtView_Time.setTextColor(Color.parseColor("#db5a6b"));
                                flagForTime = true;
                                if (flagForDate == true){
                                    alarmServiceBtn.setVisibility(View.VISIBLE);
                                }
                            }
                        }, hour, minutes, true);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));


                dialog.show();
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextColor(Color.BLACK);

                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setTextColor(Color.BLACK);
            }
        });

    }

    @Override
    public void onClick(View view) {  // save date and time
        //run service
        if(flagAlarm == false){
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("Time",txtView_Time.getText().toString());
            editor.putString("Date",txtView_Date.getText().toString());
            editor.putBoolean("alarm",true);
            editor.apply();

            Intent serviceIntent = new Intent(getContext(), myService.class);
            serviceIntent.putExtra("Time",txtView_Time.getText().toString());
            serviceIntent.putExtra("Date",txtView_Date.getText().toString());
            getActivity().startService(serviceIntent);
            flagAlarm=true;
            alarmServiceBtn.setText(getResources().getString(R.string.txt_Alarm_OFF));
        }else{
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("Time",null);
            editor.putString("Date",null);
            editor.putBoolean("alarm",false);
            editor.apply();

            Intent serviceIntent = new Intent(getContext(), myService.class);
            serviceIntent.setAction("StopService");
            getActivity().stopService(serviceIntent);
            flagAlarm=false;
            alarmServiceBtn.setText(getResources().getString(R.string.txt_Alarm_ON));
        }
    }

}
