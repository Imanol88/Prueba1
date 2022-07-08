package com.example.prueba1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Registrarse extends AppCompatActivity {

    DisplayMetrics metrics;
    int width;
    int height;
    RelativeLayout.LayoutParams params;
    DrawerLayout drawerLayout;
    FirebaseAuth autenticar;
    DatabaseReference base_de_datos;
    private EditText editTextNombre;
    private EditText editTextCorreo;
    private EditText editTextContraseña;
    private Button boton_registrar, boton_iniciar_sesion;
    private ImageButton boton_contraseña;
    private Intent intent;
    private String eye = "";
    private String nombre = "";
    private String correo = "";
    private String contra = "";




    private StringBuilder numero_append;
    private String numero_res;
    private int numero;
    private String nombre_usuario;
    private boolean flag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        autenticar = FirebaseAuth.getInstance();
        base_de_datos = FirebaseDatabase.getInstance().getReference();



        editTextNombre = findViewById(R.id.editTextNombre);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextContraseña = findViewById(R.id.editTextContraseña);
        boton_registrar = findViewById(R.id.botonRegistrar);
        boton_contraseña = findViewById(R.id.botonContraseña);
        boton_iniciar_sesion = findViewById(R.id.botonIniciarSesion);
        drawerLayout = findViewById(R.id.drawerLayout);


        numero = 0;
        numero_append = new StringBuilder();
        flag = false;


        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels; // ancho absoluto en pixels
        height = metrics.heightPixels; // alto absoluto en pixels

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        eye = "close";
        boton_contraseña.setBackgroundResource(R.drawable.visibility_none);
        editTextContraseña.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        // Edit Text Nombre
        params.width = (int) (width * 0.8);
        params.height = (int) (height * 0.07);
        params.topMargin = (int) (height * 0.1);
        params.leftMargin = (int) (width * 0.1);
        editTextNombre.setLayoutParams(params);

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
        // Registrase
        params.width = (int) (width * 0.8);
        params.height = (int) (height * 0.07);
        params.topMargin = (int) (height * 0.7);
        params.leftMargin = (int) (width * 0.1);
        boton_registrar.setLayoutParams(params);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        // Iniciar Sesion
        params.width = (int) (width * 0.8);
        params.height = (int) (height * 0.07);
        params.topMargin = (int) (height * 0.8);
        params.leftMargin = (int) (width * 0.1);
        boton_iniciar_sesion.setLayoutParams(params);

        boton_contraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eye == "close") {
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
            }
        });

        boton_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = editTextNombre.getText().toString();
                correo = editTextCorreo.getText().toString();
                contra = editTextContraseña.getText().toString();

                if (!nombre.isEmpty() && !correo.isEmpty() && !contra.isEmpty()) {

                    if (contra.length() >= 8 && !contra.chars().allMatch(Character::isAlphabetic) && !contra.matches("[\\s]+")) {
                        registrarUsuario();
                    } else {
                        Toast.makeText(Registrarse.this, "Minimo 8 caracteres", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Registrarse.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });


        boton_iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Registrarse.this, InicioSesion.class);
                startActivity(intent);
                finish();
            }
        });


    }





    private void registrarUsuario() {
        autenticar.createUserWithEmailAndPassword(correo, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Map<String, Object> map = new HashMap<>();
                    map.put("name", nombre);
                    map.put("email", correo);
                    map.put("password", contra);

                    String id = autenticar.getCurrentUser().getUid();
                    base_de_datos.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                do{
                                    nombreUsuario();
                                }while (flag);
                                base_de_datos.child("Nombres_de_Usuarios").push().setValue(nombre_usuario);
                                intent = new Intent(Registrarse.this, InicioSesion.class);
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    Toast.makeText(Registrarse.this, "No se pudo completar el registro", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }






    private void nombreUsuario() {

        flag = false;
        nombre_usuario = nombre + numero_random();

        base_de_datos.child("Nombres_de_Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapShot : snapshot.getChildren()){
                    if(Objects.equals(snapShot.getValue(), nombre_usuario)){
                        flag = true;
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




    private String numero_random(){
        for(int i = 0; i <= 6; i++){
            numero = (int)(Math.random()*((9-0)+1))+0;
            numero_append.append(numero);
        }
        numero_res = numero_append.toString();
        numero = 0;
        numero_append = new StringBuilder("");
        return numero_res;
    }





}