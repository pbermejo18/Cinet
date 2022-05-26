package com.example.cinet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class PerfilFragment extends Fragment {

    ImageView photoImageView, lapiz_nombre, lapiz_movil, lapiz_fecha_nacimiento;
    EditText nombreEditText, emailEditText, movilEditText, fecha_nacimientoEditText;
    DatabaseReference reference;
    FirebaseDatabase database;
    ExtendedFloatingActionButton extendedFloatingActionButton;

    public PerfilFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        photoImageView = view.findViewById(R.id.photoImageView);
        nombreEditText = view.findViewById(R.id.nombreEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        movilEditText = view.findViewById(R.id.movilEditText);
        fecha_nacimientoEditText = view.findViewById(R.id.fecha_nacimientoEditText);

        extendedFloatingActionButton = view.findViewById(R.id.guardar_información);

        lapiz_nombre = view.findViewById(R.id.lapiz_nombre);
        lapiz_movil = view.findViewById(R.id.lapiz_movil);
        lapiz_fecha_nacimiento = view.findViewById(R.id.lapiz_fecha_nacimiento);

/*
        edittextprueba.setFocusable(true);
        edittextprueba.setClickable(true);
        edittextprueba.setFocusableInTouchMode(true);
        edittextprueba.setLongClickable(true);
        edittextprueba.setInputType(InputType.TYPE_CLASS_PHONE);
 */

        database = FirebaseDatabase.getInstance("https://cinet-cc0f5-default-rtdb.europe-west1.firebasedatabase.app/");
        assert user != null;
        reference = database.getReference("usuarios/"+user.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot);
                User user_data = dataSnapshot.getValue(User.class);
                System.out.println(user_data);

                assert user_data != null;
                nombreEditText.setHint(user_data.getNombre().toString());
                emailEditText.setHint(user_data.getEmail().toString());
                movilEditText.setHint(user_data.getMovil().toString());
                fecha_nacimientoEditText.setHint(user_data.getFecha_de_nacimiento().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { System.out.println("NO"); }
        });

        lapiz_nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreEditText.setFocusable(true);
                nombreEditText.setClickable(true);
                nombreEditText.setFocusableInTouchMode(true);
                nombreEditText.setLongClickable(true);
                nombreEditText.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });

        lapiz_movil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movilEditText.setFocusable(true);
                movilEditText.setClickable(true);
                movilEditText.setFocusableInTouchMode(true);
                movilEditText.setLongClickable(true);
                movilEditText.setInputType(InputType.TYPE_CLASS_PHONE);
            }
        });

        lapiz_fecha_nacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fecha_nacimientoEditText.setFocusable(true);
                fecha_nacimientoEditText.setClickable(true);
                fecha_nacimientoEditText.setFocusableInTouchMode(true);
                fecha_nacimientoEditText.setLongClickable(true);
                fecha_nacimientoEditText.setInputType(InputType.TYPE_CLASS_DATETIME);
            }
        });

        extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance("https://cinet-cc0f5-default-rtdb.europe-west1.firebasedatabase.app/");
                reference = database.getReference("usuarios/"+user.getUid());

                Query query = reference;//.orderByChild("entrada");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            if (nombreEditText.getText().toString() == null || nombreEditText.getText().toString().isEmpty()) {
                                reference.child("nombre").setValue(nombreEditText.getHint().toString());
                            } else {
                                reference.child("nombre").setValue(nombreEditText.getText().toString());
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(nombreEditText.getText().toString())
                                        .build();
                                user.updateProfile(profileUpdates);
                            }

                            if (emailEditText.getText().toString() == null || emailEditText.getText().toString().isEmpty()) {
                                reference.child("email").setValue(emailEditText.getHint().toString());
                            } else {
                                reference.child("email").setValue(emailEditText.getText().toString());
                            }

                            if (movilEditText.getText().toString() == null || movilEditText.getText().toString().isEmpty()) {
                                reference.child("movil").setValue(movilEditText.getHint().toString());
                            } else {
                                reference.child("movil").setValue(movilEditText.getText().toString());
                            }

                            if (fecha_nacimientoEditText.getText().toString() == null || fecha_nacimientoEditText.getText().toString().isEmpty()) {
                                reference.child("fecha_de_nacimiento").setValue(fecha_nacimientoEditText.getHint().toString());
                            } else {
                                reference.child("fecha_de_nacimiento").setValue(fecha_nacimientoEditText.getText().toString());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) { System.out.println("NO"); }
                });
            }
        });

        if(user != null){
            if (FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() != null) {
                Glide.with(requireView()).load(user.getPhotoUrl()).into(photoImageView);
            } else {
                Glide.with(requireView()).load(R.drawable.perfil).into(photoImageView);
            }
        }
    }
}