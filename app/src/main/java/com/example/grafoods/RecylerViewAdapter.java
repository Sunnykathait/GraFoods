package com.example.grafoods;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.ViewHolder> {

    ArrayList<ItemHolderArray> arrayList;

    int item_value;

    private Context mContext;

    public RecylerViewAdapter(ArrayList<ItemHolderArray> arrayList , Context context) {
        this.mContext = context;
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
    public void onBindViewHolder(@NonNull RecylerViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ItemHolderArray itemInfo = arrayList.get(position);
        holder.txt_itemName.setText(itemInfo.getItemName());
        holder.txt_itemQuantity.setText(itemInfo.getItemQuantity());
        holder.img_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.seekBar_itemQuants.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                item_value = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                holder.txt_itemQuantity.setText(String.valueOf(item_value+1));
                arrayList.get(position).setItemQuantity(String.valueOf(item_value));
                item_value = 0;
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txt_itemName ,txt_itemQuantity;
        public ImageView img_cancel;

        public SeekBar seekBar_itemQuants;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_itemName = itemView.findViewById(R.id.itemName);
            txt_itemQuantity = itemView.findViewById(R.id.itemQuantity);
            img_cancel = itemView.findViewById(R.id.deleteBTN);
            seekBar_itemQuants =itemView.findViewById(R.id.seekBar);
        }
    }
}
