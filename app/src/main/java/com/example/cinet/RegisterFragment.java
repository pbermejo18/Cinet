package com.example.cinet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinet.databinding.ActivityMainBinding;
import com.example.cinet.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class RegisterFragment extends Fragment {

    NavController navController;   // <-----------------
    protected EditText emailEditText, passwordEditText, nombreEditText , apellidosEditText, phoneEditText, fecha_nacimientoEditText;
    private Button registerButton;
    private FirebaseAuth mAuth;

    ActivityMainBinding binding;

    public RegisterFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);  // <-----------------
        mAuth = FirebaseAuth.getInstance();

        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        nombreEditText = view.findViewById(R.id.nombreEditText);
        apellidosEditText = view.findViewById(R.id.apellidosEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        fecha_nacimientoEditText = view.findViewById(R.id.fecha_nacimientoEditText);

        registerButton = view.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearCuenta();
            }
        });
    }

    private void crearCuenta() {
        if (!validarFormulario()) {
            return;
        }

        registerButton.setEnabled(false);

        mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            actualizarUI(mAuth.getCurrentUser());
                        } else {
                            Snackbar.make(requireView(), "Error: " + task.getException(), Snackbar.LENGTH_LONG).show();

                        }

                        registerButton.setEnabled(true);
                    }
                });

    }

    private void actualizarUI(FirebaseUser currentUser) {
        currentUser.sendEmailVerification();
        // Dar nombre al user
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nombreEditText.getText().toString()+ " " + apellidosEditText.getText().toString())
                .build();
        currentUser.updateProfile(profileUpdates);

        crearUsuarioEnBDD(currentUser);
        /*binding = binding.inflate(getLayoutInflater());
        NavigationView navigationView = binding.navView;
        View header = navigationView.getHeaderView(0);
        final TextView name = header.findViewById(R.id.displayNameTextView);
        name.setText(nombreEditText.getText());*/

        navController.navigate(R.id.signInFragment);
        // mensaje de recuerdo para verificar
        Toast toast = Toast.makeText(getActivity(), "Verifica el correo electrónico para poder acceder", Toast.LENGTH_LONG);
        toast.show();
    }

    private boolean validarFormulario() {
        boolean valid = true;

        if (TextUtils.isEmpty(emailEditText.getText().toString())) {
            emailEditText.setError("Required.");
            valid = false;
        } else {
            emailEditText.setError(null);
        }

        if (TextUtils.isEmpty(passwordEditText.getText().toString())) {
            passwordEditText.setError("Required.");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }

        if (TextUtils.isEmpty(nombreEditText.getText().toString())) {
            nombreEditText.setError("Required.");
            valid = false;
        } else {
            nombreEditText.setError(null);
        }

        return valid;
    }

    private void  crearUsuarioEnBDD(FirebaseUser currentUser) {
        DatabaseReference reference;
        FirebaseDatabase database;

        database = FirebaseDatabase.getInstance("https://cinet-cc0f5-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = database.getReference("usuarios");

        Query query = reference;//.orderByChild("entrada");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        // entradas disponibles
                        if (!Objects.equals(issue.getKey(), currentUser.getUid())) {
                            reference.child(currentUser.getUid()).child("nombre").setValue(nombreEditText.getText().toString() + " " + apellidosEditText.getText().toString());
                            reference.child(currentUser.getUid()).child("email").setValue(currentUser.getEmail());
                            reference.child(currentUser.getUid()).child("movil").setValue(phoneEditText.getText().toString());
                            reference.child(currentUser.getUid()).child("fecha_de_nacimiento").setValue(fecha_nacimientoEditText.getText().toString());
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { System.out.println("NO"); }
        });
    }
}