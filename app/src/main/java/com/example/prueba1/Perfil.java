package com.example.prueba1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Objects;


public class Perfil extends AppCompatActivity {


    DisplayMetrics metrics;
    int ancho;
    int alto;
    RelativeLayout.LayoutParams params;
    RelativeLayout relative_Layout_Perfil;
    private HorizontalScrollView horizontalScrollView;
    private LinearLayout linear_Layout_Calculadoras_1, linear_Layout_Calculadoras_2;
    private ImageView logro_1, logro_2, logro_3, logro_4, logro_5, calculadora_Arimtetica, calculadora_Quimica, conversor_de_Magnitudes;
    private TextView nombre_de_Usuario, nivel, descripcion, textView_Prueba;
    private Button boton_Configuracion;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        relative_Layout_Perfil = (RelativeLayout) findViewById(R.id.Relative_Layout_Perfil);
        nombre_de_Usuario = findViewById(R.id.Nombre_de_Usuario);
        nivel = findViewById(R.id.Nivel);
        descripcion = findViewById(R.id.Descripcion);
        horizontalScrollView = findViewById(R.id.Horizontal_ScrollView_logros);
        logro_1 = findViewById(R.id.Logro_1);
        logro_2 = findViewById(R.id.Logro_2);
        logro_3 = findViewById(R.id.Logro_3);
        logro_4 = findViewById(R.id.Logro_4);
        logro_5 = findViewById(R.id.Logro_5);
        calculadora_Arimtetica = findViewById(R.id.Calculadora_Aritmetica);
        calculadora_Quimica = findViewById(R.id.Calculadora_Quimica);
        conversor_de_Magnitudes = findViewById(R.id.Conversor_de_Magnitudes);
        boton_Configuracion = findViewById(R.id.Boton_Configuracion);
        textView_Prueba = findViewById(R.id.TextView_Prueba);


        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        ancho = metrics.widthPixels; // ancho absoluto en pixels
        alto = metrics.heightPixels; // alto absoluto en pixels

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.width = (int) (ancho * 0.1851);
        params.height = (int) (alto * 0.03);
        params.leftMargin = (int) (ancho * 0.092592);
        params.topMargin = (int) (alto * 0.043859);
        nombre_de_Usuario.setLayoutParams(params);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.width = (int) (ancho * 0.166667);
        params.height = (int) (alto * 0.03);
        params.leftMargin = (int) (ancho * 0.601851);
        params.topMargin = (int) (alto * 0.043859);
        nivel.setLayoutParams(params);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.width = (int) (ancho * 0.814814);
        params.height = (int) (alto * 0.031578);
        params.leftMargin = (int) (ancho * 0.092592);
        params.topMargin = (int) (alto * 0.122807);
        descripcion.setLayoutParams(params);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.width = (int) (ancho * 0.16);
        params.height = (int) (ancho* 0.16);
        params.leftMargin = (int) (ancho * 0.092592);
        params.topMargin = (int) (alto * 0.122807);
        calculadora_Arimtetica.setImageResource(R.drawable.calculadora_icono);
        calculadora_Arimtetica.setLayoutParams(params);





        boton_Configuracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Perfil.this, Configuracion.class);
                startActivity(intent);
                finish();
            }
        });

    }






}