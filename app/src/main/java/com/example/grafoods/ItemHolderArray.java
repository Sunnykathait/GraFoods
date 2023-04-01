package com.example.grafoods;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemHolderArray{

    public String ItemName;
    public String itemQuantity;

    public ItemHolderArray(String itemName, String itemQuantity) {
        ItemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }


}
