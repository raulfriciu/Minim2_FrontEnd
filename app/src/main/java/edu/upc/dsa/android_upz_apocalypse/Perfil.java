package edu.upc.dsa.android_upz_apocalypse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil extends AppCompatActivity {

    TextView editName, editMail, editPassword;
    SharedPreferences sharedPreferences;
    Button button_logout, button_borrar, button_update, button_volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        editName = findViewById(R.id.namePerfil);
        editMail = findViewById(R.id.mailPerfil);
        editPassword = findViewById(R.id.passwordPerfil);
        button_logout = findViewById(R.id.btn_logOut);
        button_borrar = findViewById(R.id.btn_borrar);
        button_update = findViewById(R.id.btn_actualizar);
        button_volver = findViewById(R.id.btn_volverPerfl);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);

        editName.setText("Nombre: " + sharedPreferences.getString("name", null));
        editMail.setText("Mail: " + sharedPreferences.getString("email", null));
        editPassword.setText("Password: " + sharedPreferences.getString("password", null));

        button_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Perfil.this, MainActivity.class));
            }
        });

        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(Perfil.this, HomeActivity.class));
                finish();
            }
        });

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Perfil.this, ActualizarPerfil.class));
                finish();
            }
        });

        button_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog(view);
            }
        });
    }
    public void showConfirmationDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Seguro que desea elminar esta cuenta? Todos sus datos serán eliminados.")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        eliminar(sharedPreferences.getString("email",null),sharedPreferences.getString("password",null));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void eliminar(String email,String password) {
        Call<Void> deleteResponseCall = ApiClient.getService().deleteUsers(email, password);
        deleteResponseCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    String message = "Éxito";
                    Toast.makeText(Perfil.this, message, Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    startActivity(new Intent(Perfil.this,HomeActivity.class));
                    finish();
                } else {
                    String message = "Ha ocurrido un error";
                    Toast.makeText(Perfil.this, message, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String message = "Error: " + t.getMessage();
                Log.e("Borrar usuario", message);
                Toast.makeText(Perfil.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}