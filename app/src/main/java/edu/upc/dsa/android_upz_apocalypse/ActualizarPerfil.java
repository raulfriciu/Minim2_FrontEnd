package edu.upc.dsa.android_upz_apocalypse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.text.TextUtils;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActualizarPerfil extends AppCompatActivity {

    TextInputEditText editTextName, editTextPassword, editTextMail;
    Button button_save, button_volver;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_perfil);

        editTextName = findViewById(R.id.nameActualizar);
        editTextPassword = findViewById(R.id.passwordActualizar);
        editTextMail = findViewById(R.id.mailActualizar);
        button_save = findViewById(R.id.btn_save);
        button_volver = findViewById(R.id.btn_volverActualizar);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);

        String email = sharedPreferences.getString("email", null);

        button_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActualizarPerfil.this, Perfil.class));
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(editTextName.getText()) &&
                        !TextUtils.isEmpty(editTextPassword.getText()) &&
                        !TextUtils.isEmpty(editTextMail.getText())) {
                    String newName = editTextName.getText().toString();
                    String newPassword = editTextPassword.getText().toString();
                    String newMail = editTextMail.getText().toString();

                    updateUser(email, newPassword, newName, newMail);
                } else {
                    Toast.makeText(ActualizarPerfil.this, "Algunos elementos de la vista son nulos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void updateUser(String email, String newPassword, String newName, String newMail){
        Call<UsuarioResponse> updateResponseCall = ApiClient.getService().updateUsers(email, newPassword, newName, newMail);
        updateResponseCall.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if (response.isSuccessful()){
                    String message = "Éxito";
                    Toast.makeText(ActualizarPerfil.this,message,Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    // Verificar y actualizar el correo electrónico si no está vacío
                    if (!TextUtils.isEmpty(response.body().getEmail())) {
                        editor.putString("email", response.body().getEmail());
                    }

                    // Verificar y actualizar la contraseña si no está vacía
                    if (!TextUtils.isEmpty(response.body().getPassword())) {
                        editor.putString("password", response.body().getPassword());
                    }

                    // Verificar y actualizar el nombre si no está vacío
                    if (!TextUtils.isEmpty(response.body().getName())) {
                        editor.putString("name", response.body().getName());
                    }

                    editor.apply();

                    startActivity(new Intent(ActualizarPerfil.this, Perfil.class));
                    finish();
                } else {
                    String message = "Ha ocurrido un error";
                    Toast.makeText(ActualizarPerfil.this,message,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(ActualizarPerfil.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
}