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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MostrarPeliculaFragment extends Fragment {
    FragmentMostrarPeliculaBinding binding;
    PeliculasViewModel elementosViewModel;
    NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMostrarPeliculaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         //= new ViewModelProvider(requireActivity()).get(PeliculasViewModel.class);
        navController = Navigation.findNavController(view);
        elementosViewModel = new ViewModelProvider(requireActivity()).get(PeliculasViewModel.class);

        if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("admincinet@yopmail.com")) {
            binding.deleteFloatButton.setVisibility(View.VISIBLE);
            binding.deleteFloatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseFirestore.getInstance().collection("peliculas").document(Objects.requireNonNull(elementosViewModel.id_doc_seleccionado().getValue())).delete();
                }
            });
        }

        elementosViewModel.seleccionado().observe(getViewLifecycleOwner(), new Observer<Post>() {
            @Override
            public void onChanged(Post pelicula) {
                Glide.with(getContext()).load(pelicula.imagen).into(binding.image);
                binding.nombre.setText(pelicula.titulo);
                binding.descripcion.setText(pelicula.descripcion);
                binding.tipo.setText(pelicula.tipo);
                binding.productores.setText(pelicula.productores);
                binding.reparto.setText(pelicula.reparto);
                // binding.valoracion.setRating(elemento.valoracion);
            }
        });

        binding.verSinopsis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.descripcion.getVisibility() == View.VISIBLE)
                binding.descripcion.setVisibility(View.GONE);
                else binding.descripcion.setVisibility(View.VISIBLE);
            }
        });

        binding.verProductores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.productores.getVisibility() == View.VISIBLE)
                    binding.productores.setVisibility(View.GONE);
                else binding.productores.setVisibility(View.VISIBLE);
            }
        });
        binding.verReparto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.reparto.getVisibility() == View.VISIBLE)
                    binding.reparto.setVisibility(View.GONE);
                else binding.reparto.setVisibility(View.VISIBLE);
            }
        });

        binding.irAPantallaComprarEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.seleccionEntradasFragment);
            }
        });
    }
}