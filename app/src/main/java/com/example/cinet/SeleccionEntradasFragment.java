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
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

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

public class SeleccionEntradasFragment extends Fragment {
    FragmentSeleccionEntradasBinding binding;
    Bundle result = new Bundle();
    NavController navController;
    CalendarView calendarView;
    String curDate;
    public String nentradas;
    public String hentradas;

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

        database = FirebaseDatabase.getInstance("https://cinet-cc0f5-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = database.getReference("entradas").child("2LJaKLPp8AAxfTD2yyxO");

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
                // database query

                System.out.println(nentradas);

                hentradas = (String) spinner_hora_entradas.getSelectedItem();


                result.putString("nentradas", nentradas.toString().trim());
                result.putString("horaentradas", hentradas.toString().trim());
                //result.putString("calendar", curDate.toString().trim());
                getParentFragmentManager().setFragmentResult("key",result);
            }
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // Modificar texto spinner: https://www.geeksforgeeks.org/how-to-change-spinner-text-style-in-android/
        spinnerNEntradas.setAdapter(adapter);
        spinner_hora_entradas.setAdapter(adapter_horas);


        binding.irabutacas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = reference.orderByChild("entrada");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                // do something with the individual "issues"
                                Map<String,Object> hm = new HashMap<>();
                                hm.put(dataSnapshot.getValue().toString(),dataSnapshot.getValue());
                                System.out.println(hm);

                                // entradas disponibles
                                String s = issue.getValue().toString();
                                /*Toast toast2 = Toast.makeText(getActivity(), s, Toast.LENGTH_LONG);
                                toast2.show();*/

                                Integer a = 7;
                                //Integer b = Integer.valueOf(Integer.parseInt(dataSnapshot.getValue().toString()));


                                if (Integer.parseInt(s) >= Integer.parseInt(nentradas)) {
                                    int rest = Integer.parseInt(s) - Integer.parseInt(nentradas);

                                    String key = reference.getKey();
                                    Map<String, Object> childUpdates = new HashMap<>();
                                    childUpdates.put("/entrada/", rest);

                                    reference.updateChildren(childUpdates);

                                    Toast toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);
                                    toast.show();
                                } else {
                                    /*
                                    Toast toast = Toast.makeText(getActivity(), "Not equals: " + issue, Toast.LENGTH_LONG);
                                    toast.show();

                                     */
                                }
                                navController.navigate(R.id.seleccionButacasFragment);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("NO");
                    }
                });


            }
        });
    }
}