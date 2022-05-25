package com.example.cinet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ComprasFragment extends Fragment {

    TextView textView;
    DatabaseReference reference;
    FirebaseDatabase database;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public ComprasFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compras, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = view.findViewById(R.id.text0001);

        database = FirebaseDatabase.getInstance("https://cinet-cc0f5-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = database.getReference("compras");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Compra us = snapshot.getValue(Compra.class);
                        assert us != null;
                        if (us.getUid().equals(user.getUid())) {
                            textView.setText(us.getEntradas().toString());
                        }
                    }
                }
                /*
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Compra us=snapshot.getValue(Compra.class);
                    if(compra==null || usuario.getDosis()< us.getDosis()){
                        usuario = us;
                    }
                }
                Log.e("La mayor dosis es esta: " + usuario.getDosis());
                 */
            }
            /*
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot);
                User user_data = dataSnapshot.getValue(User.class);
                System.out.println(user_data);

                assert user_data != null;
                nombreEditText.setText(user_data.getNombre().toString());
                emailEditText.setText(user_data.getEmail().toString());
                movilEditText.setText(user_data.getMovil().toString());
                fecha_nacimientoEditText.setText(user_data.getFecha_de_nacimiento().toString());
            }

             */

            @Override
            public void onCancelled(DatabaseError databaseError) { System.out.println("NO"); }
        });
    }
}