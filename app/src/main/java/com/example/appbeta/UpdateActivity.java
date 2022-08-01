package com.example.appbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {



    Button btnU;
    EditText ApellidoM,ApellidoP,contraseña,nombre;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String id = getIntent().getStringExtra("id_user");
        firebaseFirestore = FirebaseFirestore.getInstance();

        ApellidoP=findViewById(R.id.txtApellidoPU);
        ApellidoM=findViewById(R.id.txtApellidoMU);
        contraseña=findViewById(R.id.txtPswU);
        nombre=findViewById(R.id.txtNombreU);
        btnU=findViewById(R.id.btn_updateU);

        getUser(id);
        btnU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nombre.getText().toString().trim();
                String ApeP = ApellidoP.getText().toString().trim();
                String ApeM = ApellidoM.getText().toString().trim();
                String pswUp = contraseña.getText().toString().trim();

                if (name.isEmpty() && ApeP.isEmpty() && ApeM.isEmpty() && pswUp.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingresar los datos", Toast.LENGTH_LONG).show();
                }else{
                    updateUser(name,ApeP,ApeM,pswUp,id);
                }

            }
        });
    }

    private void updateUser(String name, String apeP, String apeM, String pswUp, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", name);
        map.put("ApellidoP", apeP);
        map.put("ApellidoM", apeM);
        map.put("contraseña", pswUp);

        firebaseFirestore.collection("users").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Actualizado correctamente", Toast.LENGTH_LONG).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al actualizar", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getUser(String id){
        firebaseFirestore.collection("users").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nameU = documentSnapshot.getString("nombre");
                String apePU = documentSnapshot.getString("ApellidoP");
                String apeMU = documentSnapshot.getString("ApellidoM");
                String pswUR = documentSnapshot.getString("contraseña");

                nombre.setText(nameU);
                ApellidoP.setText(apePU);
                ApellidoM.setText(apeMU);
                contraseña.setText(pswUR);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al obtener datos", Toast.LENGTH_LONG).show();
            }
        });

    }

}   