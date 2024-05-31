package edu.upc.dsa.android_upz_apocalypse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TiendaActivity extends AppCompatActivity {
    RecyclerView lista;
    Button bt_back;
    TextView monedas;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
        lista = findViewById(R.id.lista);
        bt_back = findViewById(R.id.bt_back);
        monedas = findViewById(R.id.monedas);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        monedas.setText("Monedas : " + sharedPreferences.getInt("monedas",0));

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TiendaActivity.this,MainActivity.class));
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        lista.setLayoutManager(llm);

        Call<List<Object>> objectlistcall = ApiClient.getService().getObjects();
        objectlistcall.enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                if (response.isSuccessful()) {
                    List<Object> listaObjetos = response.body();
                    recycleadapter adapter = new recycleadapter(TiendaActivity.this,listaObjetos);
                    adapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), listaObjetos.get(lista.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TiendaActivity.this, DetailTiendaActivity.class);
                            intent.putExtra("Id", listaObjetos.get(lista.getChildAdapterPosition(view)).getId());
                            intent.putExtra("Nombre", listaObjetos.get(lista.getChildAdapterPosition(view)).getNombre());
                            intent.putExtra("Precio", String.valueOf(listaObjetos.get(lista.getChildAdapterPosition(view)).getPrecio()));
                            intent.putExtra("Valor", listaObjetos.get(lista.getChildAdapterPosition(view)).getValor());
                            startActivity(intent);
                        }
                    });
                    lista.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    class recycleadapter extends RecyclerView.Adapter<recycleadapter.MyViewHolder> implements View.OnClickListener{
        List<Object> list;
        private View.OnClickListener listener;
        private Context contexto;
        public recycleadapter(Context contexto, List<Object> list){
            this.contexto = contexto;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,null);
            MyViewHolder viewHolder = new MyViewHolder(view);
            view.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.id.setText("Id : " + list.get(position).getId());
            holder.nombre.setText("Nombre : " + list.get(position).getNombre());
            holder.valor.setText("Valor : " +String.valueOf(list.get(position).getValor()));
            holder.precio.setText("Precio : " + String.valueOf(list.get(position).getPrecio()));
            Picasso.with(contexto)
                    .load(list.get(position).getUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .fit()
                    .into(holder.image);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setOnClickListener(View.OnClickListener listener){
            this.listener=listener;
        }

        @Override
        public void onClick(View view) {
            if (listener!=null){
                listener.onClick(view);
            }
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView id, nombre, precio, valor;
            ImageView image;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                id = itemView.findViewById(R.id.id);
                nombre = itemView.findViewById(R.id.nombre);
                precio = itemView.findViewById(R.id.precio);
                valor = itemView.findViewById(R.id.valor);
                image = itemView.findViewById(R.id.image);
            }
        }
    }
}