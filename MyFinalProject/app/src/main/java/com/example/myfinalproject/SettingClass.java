package com.example.myfinalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import yuku.ambilwarna.AmbilWarnaDialog;

public class SettingClass extends DialogFragment {

    public static SettingClass newInstance() {
        SettingClass setting = new SettingClass();

        return setting;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        int color = args.getInt("color");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(getActivity(), color /*initialColor*/, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                // user cancelled the dialog
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                // user selected a color
            }
        });
        colorPicker.show();
        //        Button button1 = seekBarView.findViewById(R.id.button);
//        button1.setBackgroundColor(Color.RED);

//        mSeekBar = (SeekBar) seekBarView.findViewById(R.id.seekBar);
//        editTextExample = (TextView) seekBarView.findViewById(R.id.txtExample1);
//        mSeekBar.setOnSeekBarChangeListener(this);
//

        /* Get precision and update seekBar */
//        Bundle args = getArguments();
////        this.mListener = (ISeekBarActivity)context;
//        if (args != null) {
//            int prog = args.getInt("pres");
//            if (0 <= prog && prog <= 5){}
////                mSeekBar.setProgress(prog);
//        }
//        // Inflate and set the layout for the dialog
//
//        builder.setTitle("Set the number of precision ")
//                .setView(seekBarView)
//                .setPositiveButton("Ok",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
////                                mListener.onSeekBarChanged(mSeekBar.getProgress());
//                                dismiss();
//                            }
//                        })
//                .setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int whichButton) {
//                                dismiss();
//                            }
//                        }
//                );
//
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        //this connect our mainactivity with the B fragment when the context var is the mainactivity
        try{
//            this.mListener = (ISeekBarActivity)context;
//            mListener = (ISeekBarActivity) getParentFragment();
//            mListener = (ISeekBarActivity)getTargetFragment();
        }catch(ClassCastException e){
            throw new ClassCastException("the class " +
                    getActivity().getClass().getName() +
                    " must implements the interface 'FragBListener'");
        }
        super.onAttach(context);
    }
}
