package com.example.cinet;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.cinet.databinding.FragmentMostrarPeliculaBinding;
import com.example.cinet.databinding.FragmentSeleccionEntradasBinding;

public class SeleccionEntradasFragment extends Fragment {
    FragmentSeleccionEntradasBinding binding;
    NavController navController;
    public String nentradas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentSeleccionEntradasBinding.inflate(inflater, container, false)).getRoot();
        //return inflater.inflate(R.layout.fragment_seleccion_entradas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        // Menu desplegable
        Spinner spinnerNEntradas =view.findViewById(R.id.spinner_cantidad_entradas);
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(requireActivity(), R.array.cantidad_entradas, android.R.layout.simple_spinner_item);

        spinnerNEntradas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                nentradas = (String) spinnerNEntradas.getSelectedItem();
                System.out.println(nentradas);

                Bundle result = new Bundle();
                result.putString("nentradas", nentradas.toString().trim());
                getParentFragmentManager().setFragmentResult("key",result);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Modificar texto spinner: https://www.geeksforgeeks.org/how-to-change-spinner-text-style-in-android/
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerNEntradas.setAdapter(adapter);

        binding.irabutacas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.seleccionButacasFragment);
            }
        });
    }
}