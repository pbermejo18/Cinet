package com.example.cinet;

import android.content.Context;
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
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cinet.databinding.FragmentMostrarPeliculaBinding;
import com.example.cinet.databinding.FragmentSeleccionEntradasBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SeleccionEntradasFragment extends Fragment {
    FragmentSeleccionEntradasBinding binding;
    Bundle result = new Bundle();
    NavController navController;
    CalendarView calendarView;
    String curDate;
    public String nentradas;
    public String hentradas;
    PeliculasViewModel elementosViewModel;

    DatabaseReference reference;
    FirebaseDatabase database;


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
        // reference = FirebaseDatabase.getInstance("https://cinet-cc0f5-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        elementosViewModel = new ViewModelProvider(requireActivity()).get(PeliculasViewModel.class);

        database = FirebaseDatabase.getInstance("https://cinet-cc0f5-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = database.getReference("entradas").child(Objects.requireNonNull(elementosViewModel.id_doc_seleccionado().getValue()));//.child("2LJaKLPp8AAxfTD2yyxO");

        calendarView=view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                curDate = String.valueOf(dayOfMonth);
                result.putString("calendar", String.valueOf(dayOfMonth + "/" + month + "/" + year));
            }
        });

        // Menu desplegable
        Spinner spinnerNEntradas =view.findViewById(R.id.spinner_cantidad_entradas);
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(requireActivity(), R.array.cantidad_entradas, R.layout.entradas_spinner);
        adapter.setDropDownViewResource(R.layout.drop_downentradas_spinner);

        Spinner spinner_hora_entradas =view.findViewById(R.id.spinner_horas);
        ArrayAdapter<CharSequence> adapter_horas =ArrayAdapter.createFromResource(requireActivity(), R.array.horas_entradas, R.layout.entradas_spinner);
        adapter_horas.setDropDownViewResource(R.layout.drop_downentradas_spinner);

        spinnerNEntradas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                nentradas = (String) spinnerNEntradas.getSelectedItem();
                System.out.println(nentradas);


                result.putString("nentradas", nentradas.toString().trim());
            }
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spinner_hora_entradas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hentradas = (String) spinner_hora_entradas.getSelectedItem();
                System.out.println(hentradas);

                result.putString("horaentradas", hentradas.toString().trim());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        // Modificar texto spinner: https://www.geeksforgeeks.org/how-to-change-spinner-text-style-in-android/
        getParentFragmentManager().setFragmentResult("key",result);
        spinnerNEntradas.setAdapter(adapter);
        spinner_hora_entradas.setAdapter(adapter_horas);


        binding.irabutacas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curDate == null) {
                    Toast toast = Toast.makeText(getActivity(), "Selecciona un día!", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Query query = reference.orderByChild(hentradas);//.orderByChild("entrada");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                    // entradas disponibles
                                    if (Objects.equals(issue.getKey(), hentradas)) {
                                        String s = issue.getValue().toString();
                                        System.out.println(s);
                                        if (Integer.parseInt(s) >= Integer.parseInt(nentradas)) {
                                            int rest = Integer.parseInt(s) - Integer.parseInt(nentradas);

                                            // String key = reference.getKey();
                                            Map<String, Object> childUpdates = new HashMap<>();
                                            childUpdates.put(hentradas, rest);

                                            reference.updateChildren(childUpdates);

                                            navController.navigate(R.id.seleccionButacasFragment);
                                        } else {
                                            if (Integer.parseInt(s) == 0) {
                                                Toast toast = Toast.makeText(getActivity(), "Lo sentimos, no quedan entradas disponibles", Toast.LENGTH_LONG);
                                                toast.show();
                                            }
                                            if (Integer.parseInt(s) < Integer.parseInt(nentradas) && Integer.parseInt(s) != 0) {
                                                Toast toast = Toast.makeText(getActivity(), "No quedan tantas entradas disponibles. Actualmente quedan: " + Integer.parseInt(s) + " entradas.", Toast.LENGTH_LONG);
                                                toast.show();
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("NO");
                        }
                    });
                }
            }
        });
    }
}