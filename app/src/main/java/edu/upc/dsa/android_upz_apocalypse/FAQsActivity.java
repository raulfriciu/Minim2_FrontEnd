package edu.upc.dsa.android_upz_apocalypse;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;



public class FAQsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);

        List<String> preguntasFrequentes = obtenerPreguntasFrequentes();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, preguntasFrequentes);

        ListView listViewFAQs = findViewById(R.id.listViewFAQs);
        listViewFAQs.setAdapter(adapter);
    }

    private List<String> obtenerPreguntasFrequentes() {
        List<String> preguntasfrequentes = new ArrayList<>();
        preguntasfrequentes.add("¿Como se llaman los creadores? Pere, Andrea y Raul");
        preguntasfrequentes.add("¿Porque crearon este juego? Nos gusta programar");
        return preguntasfrequentes;
    }
}
