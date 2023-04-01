package com.example.grafoods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterItemRV extends RecyclerView.Adapter<AdapterItemRV.ViewHolder> {
    private Context context;
    private ArrayList<ItemClass> Item_arrayList;

    public AdapterItemRV(Context context, ArrayList<ItemClass> item_arrayList) {
        this.context = context;
        this.Item_arrayList = item_arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.itemcar, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return Item_arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView _delete;
        EditText _edtOrder , _edtQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            _delete =itemView.findViewById(R.id.txt_delete);
            _edtOrder = itemView.findViewById(R.id.edt_order);
            _edtQuantity = itemView.findViewById(R.id.edt_quantity);


        }
    }
}
