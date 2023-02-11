package com.example.myfinalproject;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class AddItemFragment extends Fragment {
    private EditText product_name, product_quantity;
    private Button saveNewItemBtn;
    private MainViewModel mainViewModel;
    private String name, quantity, prevName;
    boolean bEdit = false;
    Integer position = 0;
    static private int color;
    private ConstraintLayout constraintlayout;
//    Context context;

    AddItemFragment(int color){
        this.color = color;
    }

    AddItemFragment(String name, String quantity, Integer position, boolean edit) {
        this.name = name;
        this.quantity = quantity;
        this.position = position;
        bEdit = edit;

//        prevName = name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.add_new_item_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        product_name = (EditText) view.findViewById(R.id.editText_ItemName);
        product_quantity = (EditText) view.findViewById(R.id.editText_itemQuantity);
        saveNewItemBtn = (Button) view.findViewById(R.id.button_saveNewItem);

        product_name.setText(name);
        product_quantity.setText(quantity);
        constraintlayout =  view.findViewById(R.id.layout);
        color = MainActivity.getColorFromRaw();
        constraintlayout.setBackgroundTintList(ColorStateList.valueOf(color));
        mainViewModel = MainViewModel.getInstance(getActivity().getApplication(), getActivity());

        //Add new Item - On Click
        saveNewItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (product_name.getText().toString().matches("") || product_quantity.getText().toString().matches("")) {
                    Toast toast = Toast.makeText(getContext(), R.string.fill_all_fields, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    // remove last product from the list
//                    if(bEdit){
//                        mainViewModel.removeItemFromList(name);
//
//                    }
                    // insert new product to the list
                    mainViewModel.setItemsListByFile(product_name.getText().toString(), product_quantity.getText().toString());
                    product_name.setText("");
                    product_quantity.setText("");
                    Toast toast = Toast.makeText(getContext(), R.string.added_to_list_toast, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item_settings = menu.findItem(R.id.settings);
        MenuItem item_addItem = menu.findItem(R.id.addItem);
        item_settings.setVisible(false);
        item_addItem.setVisible(false);

    }


}
