package com.example.myfinalproject;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShoppingItemsAdapter extends RecyclerView.Adapter<ShoppingItemsAdapter.ItemsViewHolder> {

    private static ArrayList<ShoppingItem> itemsList;
    private MainViewModel mainViewModel;

    public ShoppingItemsAdapter(Application application, Context context, FragmentActivity activity, ShoppingListFrag shoppingListFrag) {
        mainViewModel = MainViewModel.getInstance(application, activity);
        itemsList = mainViewModel.getItemsLiveData().getValue();
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
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
//        private LinearLayout row_linearLayout;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            row_shoppingItem = itemView.findViewById(R.id.item_in_shoppingList);
            textView_itemName = itemView.findViewById(R.id.textView_itemName_inList);
            textView_itemQuantity = itemView.findViewById(R.id.textView_itemQuantity_inList);
//            row_linearLayout = itemView.findViewById(R.id.item_in_shoppingList);
        }
    }
}
