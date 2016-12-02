package com.hamburgo.tecnoparque.hamburgo;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LlenarBaseDatosPrueba llenar;
    Context cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        cnt = this;
//        llenar = new LlenarBaseDatosPrueba();
//        llenar.Llenar(cnt);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragmento= new InicioFragment();
        FragmentManager fragmentManager = getFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragmento)
                    .commit();

        drawer.closeDrawer(GravityCompat.START);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment f = getFragmentManager().findFragmentById(R.id.content_frame);
            if (!(f instanceof InicioFragment)){
               Fragment fragmento = new InicioFragment();
               FragmentManager fragmentManager = getFragmentManager();

               fragmentManager.beginTransaction()
                       .replace(R.id.content_frame, fragmento)
                       .commit();
           }
                else{
               super.onBackPressed();
           }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean fragmentTransaction = false;
        Fragment fragmento= null;
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_inicio) {
            fragmento = new InicioFragment();
            fragmentTransaction=true;
        } else if (id == R.id.nav_clientes) {
            fragmento = new ClientesFragment();
            fragmentTransaction=true;
        } else if (id == R.id.nav_productos) {
            fragmento = new ProductosFragment();
            fragmentTransaction=true;
        } else if (id == R.id.nav_vender) {
            fragmento = new VentasFragment();
            fragmentTransaction=true;
        } else if (id == R.id.nav_cartera) {
            fragmento = new CarteraFragment();
            fragmentTransaction=true;
        } else if (id == R.id.nav_herramientas) {

            exportDB();

        } else if (id == R.id.nav_ayuda) {

        } else if (id == R.id.Salir) {

            new AlertDialog.Builder(cnt)
                    .setTitle("Cerrar Sesión")
                    .setMessage("¿Está seguro de salir?")
                    .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            CerrarSesion();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .show();


        }

        if(fragmentTransaction) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragmento)
                    .commit();

            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void CerrarSesion(){

        SharedPreferences preferencias = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.remove("email");
        editor.remove("cedula");
        editor.remove("nombre");
        editor.remove("apellido");
        editor.remove("empresa");
        editor.remove("celular");
        editor.commit();

        Intent intent = new Intent(Principal.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void exportDB(){

        final ProgressDialog progressDialog = new ProgressDialog(cnt);
        progressDialog.setMessage("Realizando Backup");
        progressDialog.show();



        FirebaseStorage storage = FirebaseStorage.getInstance();

        File data = Environment.getDataDirectory();
        String currentDBPath = "/data/com.hamburgo.tecnoparque.hamburgo/databases/HamburgoDB";
        File currentDB = new File(data, currentDBPath);
        Uri file = Uri.fromFile(currentDB);


        StorageReference storageRef = storage.getReference().child(currentDB.getAbsolutePath());
        UploadTask uploadTask = storageRef.putFile(file);


// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                progressDialog.dismiss();
                Toast.makeText(cnt,"Error",Toast.LENGTH_SHORT).show();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                progressDialog.dismiss();
                Toast.makeText(cnt,"Backup realizado con éxito",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
