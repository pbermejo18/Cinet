package com.example.cinet;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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

import com.bumptech.glide.Glide;
import com.example.cinet.databinding.FragmentSeleccionButacasBinding;
import com.example.cinet.databinding.FragmentSeleccionEntradasBinding;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class SeleccionButacasFragment extends Fragment {
    NavController navController;
    TextView textViewNEntradas, preciototal, hora_entrada_txv, calendar_value;
    ExtendedFloatingActionButton extendedFloatingActionButton;
    String nentradas, horaentrada, calendarval, titulo_peli;
    int intentradas;
    FragmentSeleccionButacasBinding binding;
    PeliculasViewModel elementosViewModel;
    double input, precio;
    float ne;

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    private static final String CONFIG_CLIENT_ID = "AZS0Lo6_wtFnpeYf1K6NpF1Pe6W4gIOqpiy3UyPraIWRh8N4zRE1CNgqGfKISuFuEBqQfOgC6io9JCt_";
    private static final int REQUEST_CODE_PAYMENT = 1;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)

            // configuracion minima del ente
            .merchantName("Cinet");

    PayPalPayment thingToBuy;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                nentradas = bundle.getString("nentradas");
                textViewNEntradas.setText("Total de entradas: " + nentradas);

                ne = Float.parseFloat(nentradas) * 11.85f;
                input = ne;
                BigDecimal bd = new BigDecimal(input).setScale(2, RoundingMode.HALF_UP);
                precio = bd.doubleValue();
                preciototal.setText("Precio final: " + precio + " €");
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

        elementosViewModel = new ViewModelProvider(requireActivity()).get(PeliculasViewModel.class);
        elementosViewModel.seleccionado().observe(getViewLifecycleOwner(), new Observer<Post>() {
            @Override
            public void onChanged(Post pelicula) {
                titulo_peli = pelicula.titulo;
            }
        });

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


        binding.pagarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thingToBuy = new PayPalPayment(new BigDecimal(precio), "EUR", titulo_peli, PayPalPayment.PAYMENT_INTENT_SALE);

                Intent intent = new Intent(requireContext(), PaymentActivity.class);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

                startActivityForResult(intent, REQUEST_CODE_PAYMENT);
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