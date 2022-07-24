package com.example.appbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    Button regresaR;
    EditText txtNombre;
    EditText txtApellidoP;
    EditText txtApellidoM;
    EditText txtEmail;
    EditText txtpsw;
    Button registro;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regresaR= findViewById(R.id.cierre);
        txtNombre = findViewById(R.id.txt_name);
        txtApellidoP = findViewById(R.id.txt_apellidoP);
        txtApellidoM = findViewById(R.id.txt_apellidoM);
        txtEmail = findViewById(R.id.txt_email);
        txtpsw = findViewById(R.id.psw3);
        registro = findViewById(R.id.Registrar);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        registro.setOnClickListener(view-> {
            createuser();
        });

        regresaR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });



    }//end onCrate

    public void createuser(){

        String nombre = txtNombre.getText().toString();
        String apellidoP = txtApellidoP.getText().toString();
        String apellidoM = txtApellidoM.getText().toString();
        String email = txtEmail.getText().toString();
        String psw = txtpsw.getText().toString();

        if (TextUtils.isEmpty(nombre)){
            txtNombre.setError("Ups! ingresa tu nombre");
            txtNombre.requestFocus();
        }else if (TextUtils.isEmpty(apellidoP)){
            txtApellidoP.setError("Ups! ingresa tu apellido paterno");
            txtApellidoP.requestFocus();
        }else if (TextUtils.isEmpty(apellidoM)){
            txtApellidoM.setError("Ups! ingresa tu apellido materno");
            txtApellidoM.requestFocus();
        }else if (TextUtils.isEmpty(email)){
            txtEmail.setError("Ups! ingresa tu Email");
            txtEmail.requestFocus();
        }else if (TextUtils.isEmpty(psw)){
            txtpsw.setError("Ups! ingresa una contraseña");
            txtpsw.requestFocus();
        }else{

            mAuth.createUserWithEmailAndPassword(email, psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task){
                    if (task.isSuccessful()){
                        userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                        DocumentReference documentReference = db.collection("users"). document(userID);

                        Map<String,Object> user=new HashMap<>();
                        user.put("nombre", nombre);
                        user.put("ApellidoP", apellidoP);
                        user.put("ApellidoM", apellidoM);
                        user.put("Email", email);
                        user.put("contraseña", psw);

                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG", "onSuccess: Datos registrados"+userID);
                            }
                        });
                        Toast.makeText(RegisterActivity.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(RegisterActivity.this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }


    }//end Createuser
}