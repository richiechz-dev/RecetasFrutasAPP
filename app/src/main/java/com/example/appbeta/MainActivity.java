package com.example.appbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.an.biometric.BiometricCallback;
import com.an.biometric.BiometricManager;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements BiometricCallback {

    Button access;
    Button regis;
    EditText psw, email;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        access=(Button)findViewById(R.id.btnlogin);
        regis=(Button)findViewById(R.id.registro);
        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.txtEmail);
        psw = findViewById(R.id.txtpsw);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        new BiometricManager.BiometricBuilder(MainActivity.this)
                .setTitle("AppBeta")
                .setSubtitle("huella digital")
                .setDescription("ingrese su huella")
                .setNegativeButtonText("-")
                .build()
                .authenticate(MainActivity.this);

        access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1 = email.getText().toString().trim();
                String psw1 = psw.getText().toString().trim();

                if(email1.isEmpty() && psw1.isEmpty()){
                    Toast.makeText(MainActivity.this, "Ingresa los datos", Toast.LENGTH_SHORT).show();
                }else{
                    loginUser(email1, psw1);
                }
            }
        });

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private void loginUser(String email1, String psw1){
        auth.signInWithEmailAndPassword(email1, psw1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, MainActivity2.class));
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "ERROR, email o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "ERROR AL INICIAR SESIÓN", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSdkVersionNotSupported() {

    }

    @Override
    public void onBiometricAuthenticationNotSupported() {

    }

    @Override
    public void onBiometricAuthenticationNotAvailable() {

    }

    @Override
    public void onBiometricAuthenticationPermissionNotGranted() {

    }

    @Override
    public void onBiometricAuthenticationInternalError(String error) {

    }

    @Override
    public void onAuthenticationFailed() {

    }

    @Override
    public void onAuthenticationCancelled() {

    }

    @Override
    public void onAuthenticationSuccessful() {
        Toast.makeText(this, "Inicio correcto", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {

    }

    /*protected void onStart(){
        super.onStart();
        FirebaseUser usuario = auth.getCurrentUser();
        if (usuario != null){
            startActivity(new Intent(MainActivity.this, principal.class));
            finish();
        }
    }*/

}