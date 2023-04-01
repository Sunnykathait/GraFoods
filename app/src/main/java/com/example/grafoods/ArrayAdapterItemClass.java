package com.example.grafoods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ArrayAdapterItemClass extends ArrayAdapter<ItemClass> {
    public ArrayAdapterItemClass(@NonNull Context context, ArrayList<ItemClass> arrayList) {
        super(context, 0,arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.itemcar, parent, false);
        }



        return super.getView(position, convertView, parent);
    }
}
