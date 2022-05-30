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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Nueva_peli_carteleraFragment extends Fragment {

    public AppViewModel appViewModel;
    NavController navController;
    Button publishButton;
    EditText tituloEditText, descripcionEditText, postTipoEditText, postProductoresEditText, postRepartoEditText;
    String titulo, descripcion;
    public Uri mediaUri;
    public String mediaTipo;

    DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nueva_peli_cartelera, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        navController = Navigation.findNavController(view);

        publishButton = view.findViewById(R.id.publishButton);
        tituloEditText = view.findViewById(R.id.tituloEditText);
        descripcionEditText = view.findViewById(R.id.postContentEditText);
        postTipoEditText = view.findViewById(R.id.postTipo);
        postProductoresEditText = view.findViewById(R.id.postProductores);
        postRepartoEditText = view.findViewById(R.id.postReparto);

        titulo = tituloEditText.getText().toString();
        descripcion = descripcionEditText.getText().toString();


        mDatabase = FirebaseDatabase.getInstance("https://cinet-cc0f5-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

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
        String tipo = postTipoEditText.getText().toString();
        String productores = postProductoresEditText.getText().toString();
        String reparto = postRepartoEditText.getText().toString();

        if(TextUtils.isEmpty(postTitulo) || TextUtils.isEmpty(postDescription)){
            tituloEditText.setError("Required");
            descripcionEditText.setError("Required");
            return;
        }

        if (mediaTipo == null) {
            Toast toast = Toast.makeText(getActivity(), "Escoge una imagen de portada", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            publishButton.setEnabled(false);
            pujaIguardarEnFirestore(postTitulo, postDescription, tipo, productores, reparto);
        }
    }

    private void guardarEnFirestore(String titulo, String descripcion, String mediaUrl, String tipo, String productores, String reparto) {
        Post post = new Post(mediaUrl, titulo, descripcion, tipo, productores, reparto);
        FirebaseFirestore.getInstance().collection("cartelera")
                .add(post)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        navController.popBackStack();
                        appViewModel.setMediaSeleccionado( null, null);

                        mDatabase.child("entradas").child(documentReference.getId()).child("14:00 - 15:50").setValue(38);
                        mDatabase.child("entradas").child(documentReference.getId()).child("16:00 - 17:50").setValue(38);
                        mDatabase.child("entradas").child(documentReference.getId()).child("18:05 - 19:55").setValue(38);
                        mDatabase.child("entradas").child(documentReference.getId()).child("20:10 - 22:00").setValue(38);
                        mDatabase.child("entradas").child(documentReference.getId()).child("22:05 - 23:55").setValue(38);
                    }
                });
    }

    private void pujaIguardarEnFirestore(final String titulo, String descripcion, String tipo, String productores, String reparto) {
        FirebaseStorage.getInstance().getReference(mediaTipo + "/" +
                UUID.randomUUID())
                .putFile(mediaUri)
                .continueWithTask(task ->
                        task.getResult().getStorage().getDownloadUrl())
                .addOnSuccessListener(url -> guardarEnFirestore(titulo, descripcion, url.toString(), tipo, productores, reparto));
    }
}