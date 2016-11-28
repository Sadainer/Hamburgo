package com.hamburgo.tecnoparque.hamburgo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences preferencias;
    Button btnRegistrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        btnRegistrar = (Button) findViewById(R.id.btnPrueba);
//        btnRegistrar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                GenerarPDF generar = new GenerarPDF(getApplicationContext());
////                generar.CrearPDF();
//            }
//        });





        preferencias = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }

        new Handler().postDelayed(new Runnable(){
            public void run(){

                String correo = preferencias.getString("email", "vacio");
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación
                Intent intent;
                if (correo.equals("vacio"))
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                else
                    intent = new Intent(SplashActivity.this, Principal.class);

                startActivity(intent);
                finish();
            };
        }, 1000);
    }
}
