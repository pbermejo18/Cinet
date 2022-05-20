package com.example.cinet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EstrenosFragment extends Fragment {

    DatabaseReference reference;
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estrenos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
/*
        database = FirebaseDatabase.getInstance("https://cinet-cc0f5-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = database.getReference("entradas");

        Query query = reference.orderByChild("entrada").startAfter("0");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        Integer a = Integer.valueOf(7);
                        //Integer b = Integer.valueOf(String.valueOf(dataSnapshot.getValue(HashMap.class)));
                        String key = reference.child("entrada").getKey();
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/2LJaKLPp8AAxfTD2yyxO/" + key, "5");

                        reference.updateChildren(childUpdates);

                        if (a == 8) {
                            System.out.println("ok");
                            Toast toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            Toast toast = Toast.makeText(getActivity(), "Not equals: " + issue, Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("NO");
            }
        });
 */
    }
}