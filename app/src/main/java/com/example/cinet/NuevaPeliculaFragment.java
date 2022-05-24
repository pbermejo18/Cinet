package com.example.cinet;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class NuevaPeliculaFragment extends Fragment {

    public AppViewModel appViewModel;
    NavController navController;
    Button publishButton;
    EditText tituloEditText, descripcionEditText;
    String titulo, descripcion;
    public Uri mediaUri;
    public String mediaTipo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nueva_pelicula, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        navController = Navigation.findNavController(view);

        publishButton = view.findViewById(R.id.publishButton);
        tituloEditText = view.findViewById(R.id.tituloEditText);
        descripcionEditText = view.findViewById(R.id.postContentEditText);

        titulo = tituloEditText.getText().toString();
        descripcion = descripcionEditText.getText().toString();

        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publicar();
            }
        });

        view.findViewById(R.id.camara_fotos).setOnClickListener(v -> tomarFoto());
        view.findViewById(R.id.imagen_galeria).setOnClickListener(v -> seleccionarImagen());
        appViewModel.mediaSeleccionado.observe(getViewLifecycleOwner(), media -> {
            this.mediaUri = media.uri;
            this.mediaTipo = media.tipo;
            Glide.with(this).load(media.uri).into((ImageView) view.findViewById(R.id.previsualizacion));
        });
    }

    private final ActivityResultLauncher<String> galeria =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                appViewModel.setMediaSeleccionado(uri, mediaTipo);
            });
    private final ActivityResultLauncher<Uri> camaraFotos =
            registerForActivityResult(new ActivityResultContracts.TakePicture(),
                    isSuccess -> {
                        appViewModel.setMediaSeleccionado(mediaUri, "image");
                    });
    private final ActivityResultLauncher<Uri> camaraVideos =
            registerForActivityResult(new ActivityResultContracts.TakeVideo(), isSuccess
                    -> {
                appViewModel.setMediaSeleccionado(mediaUri, "video");
            });
    private final ActivityResultLauncher<Intent> grabadoraAudio =
            registerForActivityResult(new
                    ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    appViewModel.setMediaSeleccionado(result.getData().getData(),
                            "audio");
                }
            });
    private void seleccionarImagen() {
        mediaTipo = "image";
        galeria.launch("image/*");
    }

    private void tomarFoto() {
        try {
            mediaUri = FileProvider.getUriForFile(requireContext(),
                    BuildConfig.APPLICATION_ID + ".fileprovider", File.createTempFile("img",
                            ".jpg",
                            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)));
            camaraFotos.launch(mediaUri);
        } catch (IOException e) { System.out.println(e.getMessage()); }
    }


    private void publicar() {
        String postTitulo = tituloEditText.getText().toString();
        String postDescription = descripcionEditText.getText().toString();

        if(TextUtils.isEmpty(postTitulo) || TextUtils.isEmpty(postDescription)){
            tituloEditText.setError("Required");
            descripcionEditText.setError("Required");
            return;
        }
        publishButton.setEnabled(false);
        if (mediaTipo == null) {
            guardarEnFirestore(postTitulo, postDescription, null);
        }
        else
        {
            pujaIguardarEnFirestore(postTitulo, postDescription);
        }
    }

    private void guardarEnFirestore(String titulo, String descripcion, String mediaUrl) {
        Post post = new Post(mediaUrl, titulo, descripcion);
        FirebaseFirestore.getInstance().collection("peliculas")
                .add(post)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        navController.popBackStack();
                        appViewModel.setMediaSeleccionado( null, null);
                    }
                });
    }

    private void pujaIguardarEnFirestore(final String titulo, String descripcion) {
        FirebaseStorage.getInstance().getReference(mediaTipo + "/" +
                UUID.randomUUID())
                .putFile(mediaUri)
                .continueWithTask(task ->
                        task.getResult().getStorage().getDownloadUrl())
                .addOnSuccessListener(url -> guardarEnFirestore(titulo, descripcion, url.toString()));
    }
}