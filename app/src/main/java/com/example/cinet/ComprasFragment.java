package com.example.cinet;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cinet.databinding.FragmentComprasBinding;
import com.example.cinet.databinding.ViewholderCompraBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class ComprasFragment extends Fragment {
    FragmentComprasBinding binding;
    RecyclerView recyclerView;
    DatabaseReference reference;
    public List<Compra> list;
    // List<String> elementos = new ArrayList<>();
    ElementosAdapter elementosAdapter = new ElementosAdapter();


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public ComprasFragment() {
        //elementos.add("Elemento químicoeden existir dos átomos de un mismo elemento con características distintas y, en el caso de que estos posean número másico distinto, pertenecen al mismo elemento pero en lo que se conoce como uno de sus isótopos.");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        reference = FirebaseDatabase.getInstance("https://cinet-cc0f5-default-rtdb.europe-west1.firebasedatabase.app/").getReference("compras");

        list = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Compra us = snapshot.getValue(Compra.class);
                        assert us != null;
                        System.out.println(us);
                        System.out.println(user.getUid());
                        if (snapshot.child("uid").getValue() != null && user.getUid() != null) {
                            if (us.getUid().equals(user.getUid())) {
                                list.add(us);
                                System.out.println(list.get(0));
                                elementosAdapter.establecerLista(list);
                            } else {
                                System.out.println("N");
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { System.out.println("NO"); }
        });

        return (binding = FragmentComprasBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerView.setAdapter(elementosAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    class ElementoViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderCompraBinding binding;

        public ElementoViewHolder(ViewholderCompraBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class ElementosAdapter extends RecyclerView.Adapter<ElementoViewHolder> {

        List<Compra> elementos;

        @NonNull
        @Override
        public ElementoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ElementoViewHolder(ViewholderCompraBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ElementoViewHolder holder, int position) {

            Compra elemento = elementos.get(position);

            holder.binding.titulo.setText(elemento.getTitulo());
            holder.binding.entradas.setText("Entradas: " + elemento.getEntradas());
            holder.binding.hora.setText(elemento.getHora_entradas());
            holder.binding.precio.setText(elemento.getPrecio());
        }

        @Override
        public int getItemCount() {
            return elementos != null ? elementos.size() : 0;
        }

        public void establecerLista(List<Compra> elementos){
            this.elementos = elementos;
            notifyDataSetChanged();
        }
    }
}