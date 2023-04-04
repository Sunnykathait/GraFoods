package com.example.grafoods;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    ArrayList<ItemHolderArray> arrayList;
    RecylerViewAdapter recylerViewAdapter;

    RecyclerView recyclerView;

    GridLayout gridLayout;

    FirebaseFirestore firebaseFirestore;

    SharedPreferences sharedPreferences;

    ArrayList<String> _Item , _Quantity;

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

        arrayList = new ArrayList<>();

        _genToken = findViewById(R.id.txt_Gentoken);
        _txtToken = findViewById(R.id.txt_token);
        _doneOrder = findViewById(R.id.DoneOrder);

        gridLayout = findViewById(R.id.grid_item);

        recyclerView = findViewById(R.id.RV_itemCard);

        recylerViewAdapter = new RecylerViewAdapter(arrayList,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recylerViewAdapter);

        Toast.makeText(getApplicationContext(),"here working",Toast.LENGTH_SHORT).show();

        // function to generate to token
        _genToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                token_num = (int)(Math.random() * max + min);

                // function to check for the token validation in the database (if it already exist) -- to be done later

                _txtToken.setText(String.valueOf(token_num));
            }
        });


        _doneOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _Item = new ArrayList<>();
                _Quantity = new ArrayList<>();

                for (int i  = 0 ; i < arrayList.size() ; i++){
                    _Item.add(arrayList.get(i).getItemName());
                    _Quantity.add(arrayList.get(i).getItemQuantity());
                }

                if(_txtToken.getText().toString().isEmpty()){
                    _txtToken.setError("Token required");
                    return;
                }
                if(arrayList.size() == 0){
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

    }

    public void addItem(View view) {
        TextView itemName = (TextView) view;
        String _strItemName = itemName.getText().toString();
        for (int i = 0; i < arrayList.size(); i++) {
            if(arrayList.get(i).getItemName().equals(_strItemName)){
                int quant = Integer.parseInt(arrayList.get(i).getItemQuantity().toString()) ;
                quant += 1;
                arrayList.get(i).setItemQuantity(String.valueOf(quant));
                recylerViewAdapter.notifyDataSetChanged();
                return;
            }
        }
        arrayList.add(new ItemHolderArray(_strItemName,"1"));
        recylerViewAdapter.notifyDataSetChanged();
    }
}