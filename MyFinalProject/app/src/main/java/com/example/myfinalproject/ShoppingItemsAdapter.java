package com.example.myfinalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShoppingItemsAdapter extends RecyclerView.Adapter<ShoppingItemsAdapter.ItemsViewHolder> {

    private static ArrayList<ShoppingItem> itemsList;
    private MainViewModel mainViewModel;
    private Context context;
    private FragmentActivity activity;

    public ShoppingItemsAdapter(Application application, Context context, FragmentActivity activity, ShoppingListFrag shoppingListFrag) {
        mainViewModel = MainViewModel.getInstance(application, activity);
        itemsList = mainViewModel.getItemsLiveData().getValue();
        this.context = context;
        this.activity = activity;

        mainViewModel.getItemsLiveData().observe((LifecycleOwner) context, new Observer<ArrayList<ShoppingItem>>() {
            @Override
            public void onChanged(ArrayList<ShoppingItem> items) {
                itemsList = items;
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
//        listener = (ICountriesAdapterListener)parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context); // instance of the inflater
        View itemView = inflater.inflate(R.layout.item_row, parent, false); // get view of the item view object
        return new ItemsViewHolder(itemView); // return items view holder
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        ShoppingItem item = itemsList.get(position);
        holder.fillData(item.getName(),item.getQuantity());

        //Remove Button - remove item from the shopping list
        holder.btnRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage(R.string.remove_item_btnDialog);
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                int position = holder.getAdapterPosition();

                                itemsList.remove(position);
                                mainViewModel.removeItemFromList(item.getName());

                                // if the position equals to the current selected row so we need to unselected completely the selected row
                                if(position == mainViewModel.getPositionSelected().getValue())
                                    mainViewModel.setPositionSelected(-1);

                                if(position < mainViewModel.getPositionSelected().getValue())
                                    mainViewModel.setPositionSelected(mainViewModel.getPositionSelected().getValue()-1);

                                notifyDataSetChanged();
                                dialog.cancel();
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

        //Edit Button - edit item details
        holder.btnEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getFragmentManager();
               activity.getSupportFragmentManager().beginTransaction().
                        addToBackStack(null).
                        replace(R.id.main, new AddItemFragment(holder.textView_itemName.getText().toString(),holder.textView_itemQuantity.getText().toString(), holder.getAdapterPosition()), "ADD_NEW_ITEM_FRAGMENT").
                        commit();
            }
        });
    }

    @Override
    public int getItemCount() {
//        return itemsList.size();
        return 0;
    }



    // every row in our RecyclerView will get a reference of this ItemsViewHolder
    public class ItemsViewHolder extends RecyclerView.ViewHolder
    {
        private final Context context;
        private final View row_shoppingItem;
        private final TextView textView_itemName;
        private final TextView  textView_itemQuantity;
        private final Button btnRemoveItem;
        private final Button btnEditItem;
//        private LinearLayout row_linearLayout;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            row_shoppingItem = itemView.findViewById(R.id.item_in_shoppingList);
            textView_itemName = itemView.findViewById(R.id.textView_itemName_inList);
            textView_itemQuantity = itemView.findViewById(R.id.textView_itemQuantity_inList);
            btnRemoveItem = itemView.findViewById(R.id.button_deleteItem);
            btnEditItem = itemView.findViewById(R.id.button_editItem);
//            row_linearLayout = itemView.findViewById(R.id.item_in_shoppingList);
        }

        public void fillData(String name, int quantity){
            textView_itemName.setText(name);
            textView_itemQuantity.setText(String.valueOf(quantity));
        }

    }
}
