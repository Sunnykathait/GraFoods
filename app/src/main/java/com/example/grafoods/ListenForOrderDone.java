package com.example.grafoods;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ListenForOrderDone extends Worker {

    String _token;
    public ListenForOrderDone(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        FirebaseFirestore firebaseFirestore;
        firebaseFirestore = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences;
        sharedPreferences = getApplicationContext().getSharedPreferences("orderClass",MODE_PRIVATE);
        String _token = sharedPreferences.getString("OrderNumber","-1");

        DocumentReference documentReference = firebaseFirestore.collection("Tokens").document(_token);

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String _check = (String) value.get("orderDone");
                if(_check.equals("Done")){
                    Toast.makeText(getApplicationContext(), "successfull", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return Result.success();
    }
}
