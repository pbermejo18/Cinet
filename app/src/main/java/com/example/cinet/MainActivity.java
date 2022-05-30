package com.example.cinet;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.example.cinet.databinding.ActivityMainBinding;
import com.example.cinet.databinding.FragmentSeleccionButacasBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    DatabaseReference reference;
    FirebaseDatabase database;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference_entradas;
    FirebaseDatabase database_entradas;
    PeliculasViewModel elementosViewModel;
    boolean aceptado;
    EntradasHoraViewModel entradasHoraViewModel;

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    private static final String CONFIG_CLIENT_ID = "AZS0Lo6_wtFnpeYf1K6NpF1Pe6W4gIOqpiy3UyPraIWRh8N4zRE1CNgqGfKISuFuEBqQfOgC6io9JCt_";
    private static final int REQUEST_CODE_PAYMENT = 1;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)

            // configuracion minima del ente
            .merchantName("Cinet")
            .merchantPrivacyPolicyUri(
                    Uri.parse("https://www.sandbox.paypal.com/cgi-bin/webscr"))
            .merchantUserAgreementUri(
                    Uri.parse("https://www.sandbox.paypal.com/cgi-bin/webscr"));

    PayPalPayment thingToBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = binding.inflate(getLayoutInflater())).getRoot());

        // Cargar Paypal
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        setSupportActionBar(binding.toolbar);

        NavController navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();

        // ocultar y ver toolbar
        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.signInFragment || navDestination.getId() == R.id.registerFragment /* || navDestination.getId() == R.id.seleccionEntradasFragment */) {
                // getSupportActionBar().hide();
                binding.toolbar.setVisibility(View.GONE);
            } else {
                binding.toolbar.setVisibility(View.VISIBLE);
            }
        });

        // draw menu
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                // Top-level destinations:
                R.id.homeFragment, R.id.perfilFragment, R.id.comprasFragment, R.id.cinesFragment, R.id.sobreNosotrosFragment2
        )
                .setOpenableLayout(binding.drawerLayout)
                .build();

        NavigationUI.setupWithNavController(binding.navView, navController);
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);

        // imagen + info del usuario
        NavigationView navigationView = binding.navView;
        View header = navigationView.getHeaderView(0);
        final ImageView photo = header.findViewById(R.id.photoImageView);
        final TextView name = header.findViewById(R.id.displayNameTextView);
        final TextView email = header.findViewById(R.id.emailTextView);

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    if (FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() != null) {
                        Glide.with(MainActivity.this)
                                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString())
                                .circleCrop()
                                .into(photo);
                        name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    } else {
                        Glide.with(MainActivity.this)
                                .load(R.drawable.perfil)
                                .circleCrop()
                                .into(photo);
                        name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    }
                }
            }
        });
        /*getSupportFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                nentradas = bundle.getString("nentradas");
                hentradas = bundle.getString("horaentradas");
            }
        });*/
    }

    // Metodos para Paypal
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        elementosViewModel = new ViewModelProvider(this).get(PeliculasViewModel.class);
        entradasHoraViewModel = new ViewModelProvider(this).get(EntradasHoraViewModel.class);

        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data
                    .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    // informacion transación paypal
                    JSONObject jsonObject_Response = confirm.toJSONObject();
                    System.out.println(confirm.toJSONObject().toString(4));

                    // información de la compra (titulo,precio ...)
                    JSONObject jsonObject_Pedido = confirm.getPayment().toJSONObject();
                    System.out.println(confirm.getPayment().toJSONObject().toString(4));

                    database = FirebaseDatabase.getInstance("https://cinet-cc0f5-default-rtdb.europe-west1.firebasedatabase.app/");
                    reference = database.getReference("compras");

                    Query query = reference;//.orderByChild("entrada");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                try {

                                    double d = Double.parseDouble(jsonObject_Pedido.getString("amount").toString());
                                    String precio_final = String.format("%.2f", d);

                                    reference.child(jsonObject_Response.getJSONObject("response").getString("id")).child("titulo").setValue(jsonObject_Pedido.getString("short_description").toString());
                                    reference.child(jsonObject_Response.getJSONObject("response").getString("id")).child("entradas").setValue(entradasHoraViewModel.entradas_seleccionadas().getValue());
                                    reference.child(jsonObject_Response.getJSONObject("response").getString("id")).child("hora_entradas").setValue(entradasHoraViewModel.hora_seleccionada().getValue());
                                    reference.child(jsonObject_Response.getJSONObject("response").getString("id")).child("precio").setValue(precio_final + " " + jsonObject_Pedido.getString("currency_code").toString());
                                    reference.child(jsonObject_Response.getJSONObject("response").getString("id")).child("fecha").setValue(jsonObject_Response.getJSONObject("response").getString("create_time"));
                                    reference.child(jsonObject_Response.getJSONObject("response").getString("id")).child("uid").setValue(user.getUid().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) { System.out.println("NO"); }
                    });

                    Toast.makeText(getApplicationContext(), "Orden procesada",
                            Toast.LENGTH_LONG).show();


                    database_entradas = FirebaseDatabase.getInstance("https://cinet-cc0f5-default-rtdb.europe-west1.firebasedatabase.app/");
                    reference_entradas = database.getReference("entradas").child(Objects.requireNonNull(elementosViewModel.id_doc_seleccionado().getValue()));

                    System.out.println(entradasHoraViewModel.hora_seleccionada().getValue());
                    System.out.println(entradasHoraViewModel.entradas_seleccionadas().getValue());

                    Query query_entradas = reference_entradas.orderByChild(entradasHoraViewModel.hora_seleccionada().getValue().toString());//.orderByChild("entrada");
                    query_entradas.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                    // entradas disponibles
                                    if (Objects.equals(issue.getKey(), entradasHoraViewModel.hora_seleccionada().getValue())) {
                                        String s = issue.getValue().toString();
                                        System.out.println(s);
                                        if (Integer.parseInt(s) >= Integer.parseInt(Objects.requireNonNull(entradasHoraViewModel.entradas_seleccionadas().getValue()))) {
                                            int rest = Integer.parseInt(s) - Integer.parseInt(Objects.requireNonNull(entradasHoraViewModel.entradas_seleccionadas().getValue()));
                                            Map<String, Object> childUpdates = new HashMap<>();
                                            childUpdates.put(entradasHoraViewModel.hora_seleccionada().getValue(), rest);

                                            reference_entradas.updateChildren(childUpdates);
                                            // navController.navigate(R.id.seleccionButacasFragment);
                                        } else {
                                            /*Toast toast = Toast.makeText(getActivity(), "Lo sentimos, no quedan entradas disponibles", Toast.LENGTH_LONG);
                                            toast.show();*/
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            System.out.println("El usuario canceló el pago");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}