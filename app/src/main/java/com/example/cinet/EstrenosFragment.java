package com.example.cinet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cinet.databinding.FragmentCarteleraBinding;
import com.example.cinet.databinding.FragmentEstrenosBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EstrenosFragment extends Fragment {

    NavController navController;
    RecyclerView.LayoutManager mLayoutManager;
    PeliculasViewModel peliculasViewModel;
    FragmentEstrenosBinding binding;

    public EstrenosFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentEstrenosBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // navController = Navigation.findNavController(view);  // <-----------------
        peliculasViewModel = new ViewModelProvider(requireActivity()).get(PeliculasViewModel.class);

        if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("admincinet@yopmail.com")) {
            binding.addFloatButton.setVisibility(View.VISIBLE);
            binding.addFloatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    navController = Navigation.findNavController(view);
                    navController.navigate(R.id.nueva_peli_estrenosFragment);
                }
            });
        }

        RecyclerView postsRecyclerView = view.findViewById(R.id.peliculasRecyclerView);
        // posici??n de las pel??culas
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        postsRecyclerView.setLayoutManager(mLayoutManager);

        Query query = FirebaseFirestore.getInstance().collection("estrenos").limit(50);

        FirestoreRecyclerOptions<Post> options = new FirestoreRecyclerOptions.Builder<Post>()
                .setQuery(query, Post.class)
                .setLifecycleOwner(this)
                .build();

        postsRecyclerView.setAdapter(new EstrenosFragment.PostsAdapter(options));
    }

    class PostsAdapter extends FirestoreRecyclerAdapter<Post, EstrenosFragment.PostsAdapter.PostViewHolder> {
        public PostsAdapter(@NonNull FirestoreRecyclerOptions<Post> options) {super(options);}

        @NonNull
        @Override
        public EstrenosFragment.PostsAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new EstrenosFragment.PostsAdapter.PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_pelicula, parent, false));
        }

        @Override
        protected void onBindViewHolder(@NonNull EstrenosFragment.PostsAdapter.PostViewHolder holder, int position, @NonNull Post post) {
            Glide.with(getContext()).load(post.imagen).into(holder.peliculaPhotoImageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    peliculasViewModel.seleccionar(post, getSnapshots().getSnapshot(holder.getAdapterPosition()).getId().toString(), "estrenos"); //
                    navController = Navigation.findNavController(v);
                    navController.navigate(R.id.mostrarPeliculaFragment);
                }
            });
        }

        class PostViewHolder extends RecyclerView.ViewHolder {
            ImageView peliculaPhotoImageView;
            PostViewHolder(@NonNull View itemView) {
                super(itemView);
                peliculaPhotoImageView = itemView.findViewById(R.id.peliculaphotoImageView);
            }
        }
    }
}