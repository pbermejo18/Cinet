package com.example.cinet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PerfilFragment extends Fragment {

    ImageView photoImageView;
    TextView displayNameTextView, emailTextView;

    public PerfilFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        photoImageView = view.findViewById(R.id.photoImageView);
        displayNameTextView = view.findViewById(R.id.displayNameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            if (FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() != null) {
                displayNameTextView.setText(user.getDisplayName());
                emailTextView.setText(user.getEmail());

                Glide.with(requireView()).load(user.getPhotoUrl()).into(photoImageView);
            } else {
                displayNameTextView.setText(user.getDisplayName());
                emailTextView.setText(user.getEmail());

                Glide.with(requireView()).load(R.drawable.perfil).into(photoImageView);
            }
        }
    }
}