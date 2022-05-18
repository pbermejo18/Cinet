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

import com.bumptech.glide.Glide;
import com.example.cinet.databinding.FragmentProximosBinding;
import com.example.cinet.databinding.FragmentSeleccionButacasBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ProximosFragment extends Fragment {

    NavController navController;
    RecyclerView.LayoutManager mLayoutManager;
    PeliculasViewModel peliculasViewModel;
    FragmentProximosBinding binding;

    public ProximosFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentProximosBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // navController = Navigation.findNavController(view);  // <-----------------
        peliculasViewModel = new ViewModelProvider(requireActivity()).get(PeliculasViewModel.class);


        if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("admincinet@yopmail.com")) {
            binding.addFloatButton.setVisibility(View.VISIBLE);
        }

        RecyclerView postsRecyclerView = view.findViewById(R.id.peliculasRecyclerView);
        // posición de las películas
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        postsRecyclerView.setLayoutManager(mLayoutManager);

        Query query = FirebaseFirestore.getInstance().collection("peliculas").limit(50);

        FirestoreRecyclerOptions<Post> options = new FirestoreRecyclerOptions.Builder<Post>()
                .setQuery(query, Post.class)
                .setLifecycleOwner(this)
                .build();

        postsRecyclerView.setAdapter(new PostsAdapter(options));
    }

    class PostsAdapter extends FirestoreRecyclerAdapter<Post, PostsAdapter.PostViewHolder> {
        public PostsAdapter(@NonNull FirestoreRecyclerOptions<Post> options) {super(options);}

        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_pelicula, parent, false));
        }

        @Override
        protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Post post) {
            Glide.with(getContext()).load(post.imagen).into(holder.peliculaPhotoImageView);

           holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    peliculasViewModel.seleccionar(post); //
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
                /*itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        navController.navigate(R.id.homeFragment);
                    }
                });*/
            }
        }
    }
}