package com.example.grafoods;

import java.util.ArrayList;

public class ItemClass {
    public ArrayList<String> itemList;
    public ArrayList<String> itemQuantity;

    public String orderDone;

    public ItemClass(ArrayList<String> itemList, ArrayList<String> itemQuantity , String orderDone) {
        this.itemList = itemList;
        this.itemQuantity = itemQuantity;
        this.orderDone = orderDone;
    }

    public ArrayList<String> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<String> itemList) {
        this.itemList = itemList;
    }

    public ArrayList<String> getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(ArrayList<String> itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getOrderDone() {
        return orderDone;
    }

    public void setOrderDone(String orderDone) {
        this.orderDone = orderDone;
    }
}
