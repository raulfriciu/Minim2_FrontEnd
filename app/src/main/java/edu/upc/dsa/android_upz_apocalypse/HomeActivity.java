package edu.upc.dsa.android_upz_apocalypse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button iniciarPrincipal;
    Button registrarPrincipal;
    Button button_FAQs;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        iniciarPrincipal = (Button) findViewById(R.id.iniciarHome);
        registrarPrincipal = (Button) findViewById(R.id.registrarHome);
        button_FAQs = (Button) findViewById(R.id.btn_preguntas_frequentes);

        iniciarPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        registrarPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, RegistrarActivity.class);
                startActivity(i);
            }
        });

        button_FAQs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, FAQsActivity.class));
            }
        });
    }
}