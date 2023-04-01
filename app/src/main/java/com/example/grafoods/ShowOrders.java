package com.example.grafoods;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ShowOrders extends AppCompatActivity{
    ListView lv_item , lv_quant;

    LinearLayout LL_orderDone;

    FirebaseFirestore firebaseFirestore;

    ArrayAdapter<String> arrayAdapter_item;
    ArrayAdapter<String> arrayAdapter_quantz;

    ArrayList<String> arrayList_item;
    ArrayList<String> arrayList_quantz;

    String tokenNumber;

    ItemClass itemClass;

    SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_orders);

        firebaseFirestore = FirebaseFirestore.getInstance();

        sharedPreferences = getSharedPreferences("orderClass",MODE_PRIVATE);
        String _check = sharedPreferences.getString("tokenNumber","-1");


        if(_check.equals("-1")){
            Bundle token_bundle = getIntent().getExtras();
            Toast.makeText(this, "opening for the first time", Toast.LENGTH_SHORT).show();
            if(token_bundle != null){
                tokenNumber = token_bundle.getString("TokenNumber");
                sharedPreferences = getSharedPreferences("orderClass",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("tokenNumber",tokenNumber);
                editor.commit();
            }
        }else{
            tokenNumber = new String(_check);
            Toast.makeText(this, "opening for the another time" + tokenNumber, Toast.LENGTH_SHORT).show();
        }

        DocumentReference documentReference1 = firebaseFirestore.collection("Tokens").document(tokenNumber);
        documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // do nothing
                    } else {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        SharedPreferences sp = getSharedPreferences("MainAct" , Context.MODE_PRIVATE);
                        sp.edit().putString("orderPlaced","again").commit();

                        SharedPreferences sp2 = getSharedPreferences("orderClass" , Context.MODE_PRIVATE);
                        sp2.edit().putString("tokenNumber","-1").commit();

                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        lv_item = findViewById(R.id.lv_itemName);
        lv_quant = findViewById(R.id.lv_quantity);

        LL_orderDone = findViewById(R.id.LL_orderDone);

        // to check if the order is done
        DocumentReference docIdRef = firebaseFirestore.collection("Tokens").document(tokenNumber);
        docIdRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value != null && value.exists()){
                    String isDone = value.getString("orderDone");
                    if(isDone.equals("Done")){
                        LinearLayout ll_main = findViewById(R.id.mainLL);
                        ll_main.setVisibility(View.GONE);
                        LL_orderDone.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),"Your order is ready",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        // to set the order and the value on the listviews
        DocumentReference documentReference = firebaseFirestore.collection("Tokens").document(tokenNumber);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                List<String> arrayList_item = (List<String>) documentSnapshot.get("itemList");
                List<String> arrayList_quantz = (List<String>) documentSnapshot.get("itemQuantity");

                arrayList_item = new ArrayList<String>(arrayList_item);
                arrayList_quantz = new ArrayList<String>(arrayList_quantz);

                arrayAdapter_item = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        arrayList_item);

                arrayAdapter_quantz = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        arrayList_quantz);

                lv_item.setAdapter(arrayAdapter_item);
                lv_quant.setAdapter(arrayAdapter_quantz);

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        DocumentReference documentReference1 = firebaseFirestore.collection("Tokens").document(tokenNumber);
        documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // do nothing
                    } else {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        SharedPreferences sp = getSharedPreferences("MainAct" , Context.MODE_PRIVATE);
                        sp.edit().putString("orderPlaced","again").commit();

                        SharedPreferences sp2 = getSharedPreferences("orderClass" , Context.MODE_PRIVATE);
                        sp2.edit().putString("tokenNumber","-1").commit();

                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }
}