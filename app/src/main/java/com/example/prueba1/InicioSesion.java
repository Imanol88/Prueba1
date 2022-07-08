package com.example.prueba1;


import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class InicioSesion extends AppCompatActivity {

    FirebaseAuth autenticar;
    DisplayMetrics metrics;
    int width;
    int height;
    RelativeLayout.LayoutParams params;
    File localFile = null;
    String ruta;
    private StorageReference storageReference;
    DatabaseReference base_de_datos;
    private EditText editTextCorreo;
    private EditText editTextContraseña;
    private Button boton_iniciar_sesion, boton_restablecer_contraseña;
    private ImageButton boton_contraseña;
    private Intent intent;
    private String eye = "";
    private String correo = "";
    private String contraseña = "";

    private StorageReference storage_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);


        autenticar = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        base_de_datos = FirebaseDatabase.getInstance().getReference();


        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextContraseña = findViewById(R.id.editTextContraseña);
        boton_iniciar_sesion = findViewById(R.id.botonIniciarSesion);
        boton_restablecer_contraseña = findViewById(R.id.botonRestablecerContraseña);
        boton_contraseña = findViewById(R.id.botonContraseña);


        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels; // ancho absoluto en pixels
        height = metrics.heightPixels; // alto absoluto en pixels


        eye = "close";
        boton_contraseña.setBackgroundResource(R.drawable.visibility_none);
        editTextContraseña.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        // Edit Text Correo
        params.width = (int) (width * 0.8);
        params.height = (int) (height * 0.07);
        params.topMargin = (int) (height * 0.18);
        params.leftMargin = (int) (width * 0.1);
        editTextCorreo.setLayoutParams(params);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        // Edit Text Contraseña
        params.width = (int) (width * 0.8);
        params.height = (int) (height * 0.07);
        params.topMargin = (int) (height * 0.26);
        params.leftMargin = (int) (width * 0.1);
        editTextContraseña.setLayoutParams(params);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        // Boton contraseña
        params.width = (int) (width * 0.08);
        params.height = (int) (width * 0.08);
        params.topMargin = (int) (height * 0.26 + width * 0.02);
        params.leftMargin = (int) (width * 0.8);
        boton_contraseña.setLayoutParams(params);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        // Iniciar Sesion
        params.width = (int) (width * 0.8);
        params.height = (int) (height * 0.07);
        params.topMargin = (int) (height * 0.7);
        params.leftMargin = (int) (width * 0.1);
        boton_iniciar_sesion.setLayoutParams(params);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        // Restablecer contraseña
        params.width = (int) (width * 0.8);
        params.height = (int) (height * 0.07);
        params.topMargin = (int) (height * 0.8);
        params.leftMargin = (int) (width * 0.1);
        boton_restablecer_contraseña.setLayoutParams(params);


        boton_contraseña.setOnClickListener(v -> {
            if (Objects.equals(eye, "close")) {
                editTextContraseña.setInputType(InputType.TYPE_CLASS_TEXT);
                boton_contraseña.setBackgroundResource(R.drawable.visibility);
                editTextContraseña.setSelection(editTextContraseña.length());
                eye = "open";
            } else {
                editTextContraseña.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                boton_contraseña.setBackgroundResource(R.drawable.visibility_none);
                editTextContraseña.setSelection(editTextContraseña.length());
                eye = "close";
            }
        });


        boton_iniciar_sesion.setOnClickListener(v -> {
            correo = editTextCorreo.getText().toString();
            contraseña = editTextContraseña.getText().toString();
            if (!correo.isEmpty() && !contraseña.isEmpty()) {

                try{
                    storage_img = storageReference.child("Fotos_de_Perfil").child(autenticar.getUid()).child("1");
                }catch (Exception e){
                    storageReference.child("Fotos_de_Perfil").child("Default").child("1");
                }


                try {
                    localFile = File.createTempFile("images", "jpg");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                storage_img.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                    Toast.makeText(InicioSesion.this, "Sí", Toast.LENGTH_SHORT).show();
                    ruta = localFile.getAbsolutePath();
                    intent = new Intent(InicioSesion.this, PantallaPrincipal.class);
                    intent.putExtra("Ruta", ruta);
                    startActivity(intent);
                    finish();
                }).addOnFailureListener(exception -> Toast.makeText(InicioSesion.this, "No", Toast.LENGTH_SHORT).show());


            } else {
                Toast.makeText(InicioSesion.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

        boton_restablecer_contraseña.setOnClickListener(v -> {
            intent = new Intent(InicioSesion.this, RestablecerContrasenia.class);
            startActivity(intent);
            finish();
        });


    }


    private void iniciarSesion() {
        autenticar.signInWithEmailAndPassword(correo, contraseña).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(InicioSesion.this, "No se pudo Iniciar Sesion", Toast.LENGTH_SHORT).show();
            } else {
                intent = new Intent(InicioSesion.this, Perfil.class);
                startActivity(intent);
                finish();
            }
        });
    }








}