package edu.upc.dsa.android_upz_apocalypse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarActivity extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextUsername, editTextPassword, editTextPasswordRep;
    Button  bt_registrar2, bt_login2;
    ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        editTextUsername  = findViewById(R.id.nameRegistrar);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.passwordRegistrar);
        editTextPasswordRep = findViewById(R.id.contrasenaRep);
        bt_login2 = findViewById(R.id.bt_login2);
        spinner= findViewById(R.id.progressBar2);
        spinner.setVisibility(View.GONE);

        bt_login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrarActivity.this,LoginActivity.class));
            }
        });

        bt_registrar2 = findViewById(R.id.bt_registrar2);
        bt_registrar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(editTextEmail.getText().toString()) || TextUtils.isEmpty(editTextPassword.getText().toString()) || TextUtils.isEmpty(editTextPasswordRep.getText().toString()) || TextUtils.isEmpty(editTextUsername.getText().toString())){
                    String message = "Rellene todos los campos";
                    Toast.makeText(RegistrarActivity.this,message,Toast.LENGTH_LONG).show();
                    spinner.setVisibility(View.GONE);
                }else if (! editTextPassword.getText().toString().equals(editTextPasswordRep.getText().toString())){
                    String message = "Las contraseñas deben coincidir";
                    Toast.makeText(RegistrarActivity.this,message,Toast.LENGTH_LONG).show();
                    spinner.setVisibility(View.GONE);
                }
                else {
                    RegistrarRequest registrarRequest = new RegistrarRequest();
                    registrarRequest.setName(editTextUsername.getText().toString());
                    registrarRequest.setEmail(editTextEmail.getText().toString());
                    registrarRequest.setPassword(editTextPassword.getText().toString());
                    registrarUser(registrarRequest);}
            }
        });
    }

    public void registrarUser(RegistrarRequest registrarRequest){
        Call<RegistrarResponse> registrarResponseCall = ApiClient.getService().registrarUsers(registrarRequest);
        registrarResponseCall.enqueue(new Callback<RegistrarResponse>() {
            @Override
            public void onResponse(Call<RegistrarResponse> call, Response<RegistrarResponse> response) {
                if (response.isSuccessful()){
                    String message = "Éxito";
                    Toast.makeText(RegistrarActivity.this,message,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegistrarActivity.this,LoginActivity.class));
                    finish();
                } else {
                    String message = "Ha ocurrido un error";
                    Toast.makeText(RegistrarActivity.this,message,Toast.LENGTH_LONG).show();
                    spinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<RegistrarResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(RegistrarActivity.this,message,Toast.LENGTH_LONG).show();
                spinner.setVisibility(View.GONE);
            }
        });
    }
}