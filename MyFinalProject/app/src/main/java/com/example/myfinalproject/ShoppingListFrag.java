package com.example.myfinalproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShoppingListFrag extends Fragment {
    private RecyclerView recyclerView;
    private ShoppingItemsAdapter itemsAdapter;
    private TextView txtView_Date, txtView_Time;

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
                                txtView_Date.setTextColor(Color.parseColor("#ff2d57"));
//                                if (txtView_Time.getText().toString().length() >= 1)
//                                    btnSaveDateAndTime.setVisibility(View.VISIBLE);
                            }
                        },
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();

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
                                txtView_Time.setTextColor(Color.parseColor("#ff2d57"));
//                                if (tvDatePicker.getText().toString().length() >= 1)
//                                    btnSaveDateAndTime.setVisibility(View.VISIBLE);
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

}
