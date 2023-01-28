package com.example.myfinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
//                FragmentManager fm = getSupportFragmentManager();
//                Fragment toHide = fm.findFragmentById(R.id.main_container);
//                FragmentTransaction ft = fm.beginTransaction();
//                if (toHide != null) {
//                    ft.hide(toHide);    // hide main fragment.
//                }
//
//                // This is the parent activity
//                // Pay attention on note that the SettingFragment has
//                ft.add(R.id.mainActivityLayoutID, new SettingFragment())
//                        .addToBackStack(null)
//                        .commit();
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

    // Nested class to show add item frag
    public static class AddItemFragment extends Fragment {
        private EditText product_name, product_quantity;
        private Button saveNewItemBtn;
        private MainViewModel mainViewModel;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.add_new_item_fragment, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            product_name = (EditText) view.findViewById(R.id.editText_ItemName);
            product_quantity = (EditText) view.findViewById(R.id.editText_itemQuantity);
            saveNewItemBtn = (Button) view.findViewById(R.id.button_saveNewItem);

            mainViewModel = MainViewModel.getInstance(getActivity().getApplication(), getActivity());

            //Add new Item - On Click
            saveNewItemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (product_name.getText().toString().matches("") || product_quantity.getText().toString().matches("")) {
                        Toast toast = Toast.makeText(getContext(), R.string.fill_all_fields, Toast.LENGTH_SHORT);
                        toast.show();
                    } else {

                    }
                }
            });
            super.onViewCreated(view, savedInstanceState);

        }
    }
}