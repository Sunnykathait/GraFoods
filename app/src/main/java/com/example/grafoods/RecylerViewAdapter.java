package com.example.grafoods;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.ViewHolder> {

    ArrayList<ItemHolderArray> arrayList;

    public RecylerViewAdapter(ArrayList<ItemHolderArray> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.itemcard, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecylerViewAdapter.ViewHolder holder, int position) {
        ItemHolderArray itemInfo = arrayList.get(position);
        holder.txt_itemName.setText(itemInfo.getItemName());
        holder.txt_itemQuantity.setText(itemInfo.getItemQuantity());
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txt_itemName , txt_itemQuantity;
        public ImageView img_cancel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_itemName = itemView.findViewById(R.id.itemName);
            txt_itemQuantity = itemView.findViewById(R.id.itemQuantity);
            img_cancel = itemView.findViewById(R.id.deleteBTN);
        }
    }
}
