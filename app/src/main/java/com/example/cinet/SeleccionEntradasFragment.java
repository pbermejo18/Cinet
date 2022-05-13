package com.example.cinet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SeleccionEntradasFragment extends Fragment {
    NavController navController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seleccion_entradas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        // Menu desplegable
        Spinner spinnerLanguages =view.findViewById(R.id.spinner_cantidad_entradas);
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(requireActivity(), R.array.cantidad_entradas, android.R.layout.simple_spinner_item);
            // Modificar texto spinner: https://www.geeksforgeeks.org/how-to-change-spinner-text-style-in-android/
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerLanguages.setAdapter(adapter);
    }
}