package com.example.cinet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.example.cinet.databinding.FragmentMostrarPeliculaBinding;
import com.example.cinet.databinding.FragmentQRBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class QRFragment extends Fragment {

    FragmentQRBinding binding;
    ComprasViewModel elementosViewModel;
    NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentQRBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //= new ViewModelProvider(requireActivity()).get(PeliculasViewModel.class);
        navController = Navigation.findNavController(view);
        elementosViewModel = new ViewModelProvider(requireActivity()).get(ComprasViewModel.class);

        elementosViewModel.seleccionado().observe(getViewLifecycleOwner(), new Observer<Compra>() {
            @Override
            public void onChanged(Compra compra) {
                binding.titulo.setText(compra.titulo);
                binding.entradas.setText("Entradas: " + compra.entradas);
                binding.hora.setText(compra.hora_entradas);
                binding.precio.setText(compra.precio);
                binding.idCompra.setText("Identificador: " + compra.id_compra);
                // binding.valoracion.setRating(elemento.valoracion);
            }
        });
    }
}