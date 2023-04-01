package com.example.grafoods;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    TextView _genToken , _txtToken , _addOrder , _doneOrder;
    int token_num = 0 ,max = 999 ,min = 100;

    EditText _edtOrder , _edtQuantity;

    ListView listView_order;

    ArrayList<String> arrayList_Item;
    ArrayList<String> _Item;
    ArrayList<String> _Quantity;

    ArrayAdapter<String> arrayAdapter_ItemList;
    ItemClass itemClass;

    FirebaseFirestore firebaseFirestore;

    ProgressBar progressBar ;
    SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MainAct", MODE_PRIVATE);
        String _check = sharedPreferences.getString("orderPlaced","this");

        if(_check.equals("next")){
            Intent intent = new Intent(MainActivity.this , ShowOrders.class);
            startActivity(intent);
            finish();
        }

        _genToken = findViewById(R.id.txt_Gentoken);
        _txtToken = findViewById(R.id.txt_token);
        _addOrder = findViewById(R.id.addOrder);
        _doneOrder = findViewById(R.id.DoneOrder);

        listView_order = findViewById(R.id.LV_item);

        _edtOrder = findViewById(R.id.edt_order);
        _edtQuantity = findViewById(R.id.edt_quantity);

        progressBar = new ProgressBar(getApplicationContext());

        _Item = new ArrayList<String>();
        _Quantity = new ArrayList<String>();

        arrayList_Item = new ArrayList<String>();

        arrayAdapter_ItemList = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                arrayList_Item);

        listView_order.setAdapter(arrayAdapter_ItemList);

        // function to generate to token
        _genToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                token_num = (int)(Math.random() * max + min);

                // function to check for the token validation in the database (if it already exist) -- to be done later

                _txtToken.setText(String.valueOf(token_num));
            }
        });

        // function to add order in the listview
        _addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderName = _edtOrder.getText().toString();
                String orderQuant = _edtQuantity.getText().toString();

                _Item.add(orderName);
                _Quantity.add(orderQuant);

                String toBeInserted ="Item Name : " + orderName.toUpperCase() + "   Quantity : " + orderQuant;
                arrayList_Item.add(toBeInserted);
                arrayAdapter_ItemList.notifyDataSetChanged();
                _doneOrder.setVisibility(View.VISIBLE);
            }
        });

        _doneOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_txtToken.getText().toString().isEmpty()){
                    _txtToken.setError("Token required");
                    return;
                }
                if(arrayList_Item.size() == 0){
                    Toast.makeText(MainActivity.this, "Please order something first", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseFirestore = FirebaseFirestore.getInstance();
                DocumentReference documentReference = firebaseFirestore.collection("Tokens").document(String.valueOf(token_num));
                documentReference.set(new ItemClass(_Item,_Quantity,"")).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "Order created successfully....", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ShowOrders.class);
                        intent.putExtra("TokenNumber",String.valueOf(token_num));
                        startActivity(intent);

                        SharedPreferences sharedPreferences1 = getSharedPreferences("MainAct", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences1.edit();
                        myEdit.putString("orderPlaced","next");
                        myEdit.commit();

                        finish();
                    }
                });

            }
        });

        // function to delete the order from the listview by long pressing on it
        listView_order.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                arrayList_Item.remove(position);
                _Quantity.remove(position);
                _Item.remove(position);
                arrayAdapter_ItemList.notifyDataSetChanged();
                if(arrayList_Item.size() == 0){
                    _doneOrder.setVisibility(View.GONE);
                }
                return true;
            }
        });

    }
}