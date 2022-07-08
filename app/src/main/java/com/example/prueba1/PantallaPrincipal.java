package com.example.prueba1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.Objects;

public class PantallaPrincipal extends AppCompatActivity {

    DisplayMetrics metrics;
    int width;
    int height;
    RelativeLayout.LayoutParams params;
    DrawerLayout drawerLayout;
    private Intent intent;
    private Toolbar toolbar;
    private NavigationView naView;
    private String ruta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);


        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        naView = findViewById(R.id.navView);

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        ruta = getIntent().getStringExtra("Ruta");
        File file = new File(ruta);



        ImageView imagen = new ImageView(PantallaPrincipal.this);
        imagen.setImageURI(Uri.fromFile(file));


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setHomeAsUpIndicator(imagen.getDrawable());
            getSupportActionBar().setTitle("App");
        }


        naView.setItemIconTintList(null);


        naView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.opcion_perfil:
                    intent = new Intent(PantallaPrincipal.this, Perfil.class);
                    intent.putExtra("Ruta", ruta);
                    startActivity(intent);
                    finish();
                    break;
            }
            return false;
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}