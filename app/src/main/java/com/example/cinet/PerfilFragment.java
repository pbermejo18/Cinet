package com.example.cinet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

    ImageView photoImageView;
    TextView displayNameTextView, emailTextView, movilTextView, fechnacTextView;

    DatabaseReference reference;
    FirebaseDatabase database;

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
        displayNameTextView = view.findViewById(R.id.displayNameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        movilTextView = view.findViewById(R.id.movilTextView);
        fechnacTextView = view.findViewById(R.id.fechnacTextView);

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
                displayNameTextView.setText(user_data.getNombre().toString());
                emailTextView.setText(user_data.getEmail().toString());
                movilTextView.setText(user_data.getMovil().toString());
                fechnacTextView.setText(user_data.getFecha_de_nacimiento().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { System.out.println("NO"); }
        });



        if(user != null){
            if (FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() != null) {
                //displayNameTextView.setText(user.getDisplayName());
                //emailTextView.setText(user.getEmail());

                Glide.with(requireView()).load(user.getPhotoUrl()).into(photoImageView);
            } else {
                //displayNameTextView.setText(user.getDisplayName());
                //emailTextView.setText(user.getEmail());

                Glide.with(requireView()).load(R.drawable.perfil).into(photoImageView);
            }
        }
    }
}