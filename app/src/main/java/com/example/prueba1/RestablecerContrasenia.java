package com.example.prueba1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class RestablecerContrasenia extends AppCompatActivity {

    DisplayMetrics metrics;
    int width;
    int height;
    RelativeLayout.LayoutParams params;
    DrawerLayout drawerLayout;
    private Intent intent;
    private EditText editTextCorreo;
    private Button boton_restablecer;
    private String correo = "";
    private ProgressDialog progreso_dialog;

    private FirebaseAuth autenticar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer_contrasenia);

        autenticar = FirebaseAuth.getInstance();

        editTextCorreo = findViewById(R.id.editTextCorreo);
        boton_restablecer = findViewById(R.id.botonRestablecerContraseña);
        progreso_dialog = new ProgressDialog(this);
        drawerLayout = findViewById(R.id.drawerLayout);


        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels; // ancho absoluto en pixels
        height = metrics.heightPixels; // alto absoluto en pixels

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        // Edit Text Correo
        params.width = (int) (width * 0.8);
        params.height = (int) (height * 0.07);
        params.topMargin = (int) (height * 0.2);
        params.leftMargin = (int) (width * 0.1);
        editTextCorreo.setLayoutParams(params);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        // Restablecer
        params.width = (int) (width * 0.8);
        params.height = (int) (height * 0.07);
        params.topMargin = (int) (height * 0.4);
        params.leftMargin = (int) (width * 0.1);
        boton_restablecer.setLayoutParams(params);


        boton_restablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo = editTextCorreo.getText().toString();
                if (!correo.isEmpty()) {
                    progreso_dialog.setMessage("Espere un momento");
                    progreso_dialog.setCanceledOnTouchOutside(false);
                    progreso_dialog.show();
                    restablecerContraseña();
                } else {

                }
            }
        });

    }


    private void restablecerContraseña() {
        autenticar.setLanguageCode("es");
        autenticar.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RestablecerContrasenia.this, "Se ha enviado un correo de restablecimiento", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RestablecerContrasenia.this, "No se ha podido restablecer", Toast.LENGTH_SHORT).show();
                }
                progreso_dialog.dismiss();
            }
        });
    }




}