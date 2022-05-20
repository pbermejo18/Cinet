package com.example.cinet;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinet.databinding.FragmentSeleccionButacasBinding;
import com.example.cinet.databinding.FragmentSeleccionEntradasBinding;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class SeleccionButacasFragment extends Fragment {
    NavController navController;
    TextView textViewNEntradas, preciototal, hora_entrada_txv, calendar_value;
    ExtendedFloatingActionButton extendedFloatingActionButton;
    String nentradas, horaentrada, calendarval;
    int intentradas;
    FragmentSeleccionButacasBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                nentradas = bundle.getString("nentradas");
                textViewNEntradas.setText("Total de entradas: " + nentradas);
                float ne = Float.parseFloat(nentradas);
                preciototal.setText("Precio final: " + ne * 11.85f + " €");
                intentradas = Integer.parseInt(nentradas);

                horaentrada = bundle.getString("horaentradas");
                hora_entrada_txv.setText(horaentrada);

                calendarval = bundle.getString("calendar");
                calendar_value.setText(calendarval);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentSeleccionButacasBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        textViewNEntradas = view.findViewById(R.id.nentradas);
        preciototal = view.findViewById(R.id.preciototal);
        hora_entrada_txv = view.findViewById(R.id.hora_entrada_txv);
        calendar_value = view.findViewById(R.id.calendar_value);
        extendedFloatingActionButton = view.findViewById(R.id.pagar_button);

        binding.butacaA1.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaA2.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaA3.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaA4.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaA5.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaB1.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaB2.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaB3.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaB4.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaB5.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaB6.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaC1.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaC2.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaC3.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaC4.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaC5.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaD1.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaD2.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaD3.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaD4.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaD5.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaD6.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaE1.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaE2.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaE3.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaE4.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaE5.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaF1.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaF2.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaF3.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaF4.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaF5.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaF6.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaG1.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaG2.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaG3.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaG4.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});
        binding.butacaG5.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { comprobarEntrada(v); }});

        /*
        binding.irabutacas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.seleccionButacasFragment);
            }
        });
        */
        binding.pagarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.pagarFragment);
            }
        });
    }

    public void comprobarEntrada(View v) {
        if (v.getBackground().getConstantState() == getResources().getDrawable(R.drawable.border_standard).getConstantState()) {
            if (intentradas != 0) {
                v.setBackgroundResource(R.drawable.border_standard_black);
                intentradas --;
                System.out.println(intentradas);
            } else {
                Toast toast = Toast.makeText(getActivity(), "No quedan entradas por seleccionar!", Toast.LENGTH_LONG);
                toast.show();
            }
        } else {
            v.setBackgroundResource(R.drawable.border_standard);
            intentradas ++;
            System.out.println(intentradas);
        }
    }
}