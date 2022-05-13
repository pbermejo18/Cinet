package com.example.cinet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.example.cinet.databinding.FragmentMostrarPeliculaBinding;

public class MostrarPeliculaFragment extends Fragment {
    FragmentMostrarPeliculaBinding binding;
    PeliculasViewModel elementosViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMostrarPeliculaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         //= new ViewModelProvider(requireActivity()).get(PeliculasViewModel.class);
        elementosViewModel = new ViewModelProvider(requireActivity()).get(PeliculasViewModel.class);

        elementosViewModel.seleccionado().observe(getViewLifecycleOwner(), new Observer<Post>() {
            @Override
            public void onChanged(Post pelicula) {
                Glide.with(getContext()).load(pelicula.imagen).into(binding.image);
                binding.nombre.setText(pelicula.titulo);
                binding.descripcion.setText(pelicula.descripcion);
                // binding.valoracion.setRating(elemento.valoracion);
            }
        });


    }
}