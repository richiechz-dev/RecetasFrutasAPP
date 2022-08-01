package com.example.appbeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {

    ImageButton anterior;
    Button btnBuscar;
    TextView textView3;
    EditText Ingrediente;
    Button admin1;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        anterior=(ImageButton)findViewById(R.id.imageButton2);

        anterior.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(i);
            }
        });

        textView3=(TextView)findViewById(R.id.textView3);
        anterior=(ImageButton)findViewById(R.id.imageButton2);
        Ingrediente=(EditText)findViewById(R.id.Ingrediente);
        admin1=(Button) findViewById(R.id.btnadmin);
        btnBuscar= (Button) findViewById(R.id.btnBuscar);

        btnBuscar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                buscar1("http://192.168.1.66/appBeta/fetch.php?fruto="+Ingrediente.getText().toString());
            }

        });
        admin1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity2.this, mainAdmin.class);
                startActivity(i);
            }

        });

    }

    private void buscar1(String URL){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray=null;
                for(int i=0;i<response.length();i++){
                    try{
                        jsonArray=response.getJSONArray(i);
                        textView3.setText(jsonArray.getString(Integer.parseInt("receta")));
                    }catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

}