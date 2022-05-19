package com.example.cinet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInFragment extends Fragment {

    protected SignInButton googleSignInButton;
    protected ActivityResultLauncher<Intent> activityResultLauncher;

    NavController navController;

    protected EditText emailEditText, passwordEditText;
    protected Button emailSignInButton;
    protected ConstraintLayout signInForm;

    protected FirebaseAuth mAuth;

    public SignInFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        view.findViewById(R.id.gotoCreateAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.registerFragment);
            }
        });

        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        emailSignInButton = view.findViewById(R.id.emailSignInButton);
        signInForm = view.findViewById(R.id.signInForm);

        emailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accederConEmail();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        googleSignInButton = view.findViewById(R.id.googleSignInButton);
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
// There are no request codes
                            Intent data = result.getData();
                            try {
                                firebaseAuthWithGoogle(GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException.class));
                            } catch (ApiException e) {
                                Log.e("ABCD", "signInResult:failed code=" +
                                        e.getStatusCode());
                            }
                        }
                        else {
                            Log.e("ABCD","FALLO");
                        }
                    }
                });
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accederConGoogle();
            }
        });

    }

    private void accederConEmail() {
        signInForm.setVisibility(View.GONE);

        if (emailEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "Introduce la información necesaria", Toast.LENGTH_LONG);
            toast.show();
            signInForm.setVisibility(View.VISIBLE);
        } else {
            mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                actualizarUI(mAuth.getCurrentUser());
                            } else {
                                Snackbar.make(requireView(), "Error: " + task.getException(), Snackbar.LENGTH_LONG).show();
                            }
                            signInForm.setVisibility(View.VISIBLE);
                        }
                    });
        }
    }

    private void actualizarUI(FirebaseUser currentUser) {
        if (currentUser != null && currentUser.isEmailVerified()) {
            navController.navigate(R.id.homeFragment);
        } else {
            Toast toast = Toast.makeText(getActivity(), "Comprueba tu correo electrónico para poder acceder", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void accederConGoogle() {
        GoogleSignInClient googleSignInClient =
                GoogleSignIn.getClient(requireActivity(), new
                        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build());
        activityResultLauncher.launch(googleSignInClient.getSignInIntent());
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        if(acct == null) return;
        signInForm.setVisibility(View.GONE);
        mAuth.signInWithCredential(GoogleAuthProvider.getCredential(acct.getIdToken(
        ), null))
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e("ABCD", "signInWithCredential:success");
                            actualizarUI(mAuth.getCurrentUser());
                        } else {
                            Log.e("ABCD", "signInWithCredential:failure",
                                    task.getException());
                            signInForm.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}