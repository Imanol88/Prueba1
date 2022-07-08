package com.example.prueba1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Objects;

public class Configuracion extends AppCompatActivity {

    FirebaseAuth autenticar;
    DisplayMetrics metrics;
    int width;
    int height;
    RelativeLayout.LayoutParams params;
    File localFile = null;
    String ruta;
    private StorageReference storageReference;
    private Intent intent;
    private EditText contraseñaActual, contraseñaNueva, contraseñaNueva2;
    private TextView textViewCambiarFotoPerfil;
    private Button cerrarSesion, botonCambiarFotoPerfil, botonCambiarContraseña;
    private ImageView fotoPerfil;
    private String contraseña, correo;
    private AuthCredential credential;
    FirebaseUser user;


    private static final int PICK_IMAGE = 100;
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);


        autenticar = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();


        textViewCambiarFotoPerfil = (TextView) findViewById(R.id.textViewCambiarFotoPerfil);
        cerrarSesion = (Button) findViewById(R.id.cerrarSesion);
        botonCambiarFotoPerfil = (Button) findViewById(R.id.botonCambiarFotoPerfil);
        fotoPerfil = (ImageView) findViewById(R.id.fotoPerfil);
        contraseñaActual = (EditText) findViewById(R.id.contraseñaActual);
        contraseñaNueva = (EditText) findViewById(R.id.contraseñaNueva);
        contraseñaNueva2 = (EditText) findViewById(R.id.contraseñaNueva2);


        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        // Edit Text Correo
        params.width = (int) (width * 0.8);
        params.height = (int) (height * 0.07);
        params.topMargin = (int) (height * 0.8);
        params.leftMargin = (int) (width * 0.1);
        cerrarSesion.setLayoutParams(params);


        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        // Edit Text Correo
        params.width = (int) (width * 0.8);
        params.height = (int) (height * 0.07);
        params.topMargin = (int) (height * 0.3);
        params.leftMargin = (int) (width * 0.1);
        botonCambiarFotoPerfil.setLayoutParams(params);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        // Edit Text Correo
        params.width = (int) (width * 0.3);
        params.height = (int) (width * 0.3);
        params.topMargin = (int) (height * 0.1);
        params.leftMargin = (int) (width * 0.1);
        fotoPerfil.setLayoutParams(params);

        Uri uri = null;

        storageReference.child("Fotos_de_Perfil").child(autenticar.getUid()).child("1").getFile(uri);

        fotoPerfil.setImageURI(uri);


        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autenticar.signOut();
                finish();
            }
        });



        botonCambiarFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });

        botonCambiarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contraseñaNueva.getText().toString().equals(contraseñaNueva2.getText().toString()) && !Objects.equals(contraseña, "")){
                    if (contraseñaNueva.getText().toString().length() >= 8 && !contraseñaNueva.getText().toString().chars().allMatch(Character::isAlphabetic) && !contraseñaNueva.getText().toString().matches("[\\s]+")) {
                        cambiarContraseña();
                    }
                }

            }
        });


    }


    public void cambiarContraseña(){
        contraseña = contraseñaActual.getText().toString();
        correo = user.getEmail();
        credential = EmailAuthProvider.getCredential(correo, contraseña);
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(contraseñaNueva.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                    } else {

                                    }
                                }
                            });
                        } else {

                        }
                    }
                });
    }

    public void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        someActivityResultLauncher.launch(intent);
    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        imageUri = data.getData();
                        subirImagen();
                    }
                }
            });


    protected void subirImagen(){

        final StorageReference ref = storageReference.child("Fotos_de_Perfil").child(autenticar.getUid()).child("1"); // Referencia a donde queres subir el archivo
        UploadTask uploadTask = ref.putFile(imageUri);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continuamos con el task para obtener la url de descarga
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Configuracion.this, "Subida", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Configuracion.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }






}