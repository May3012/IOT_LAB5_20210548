package com.example.lab5.Activitys;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab5.Adapters.ComidaAdapter;
import com.example.lab5.Notificaciones.NotifcacionesActivity;
import com.example.lab5.Objetos.Comida;
import com.example.lab5.Objetos.Usuario;
import com.example.lab5.R;

import java.util.ArrayList;
import java.util.List;

public class ComidasActivity extends AppCompatActivity {
    private EditText etComida, etCalorias;
    private Button btnAgregarComida;
    private TextView tvCaloriasTotales;
    private RecyclerView rvComidas;
    private List<Comida> listaComidas;
    private ComidaAdapter adapter;
    private double caloriasTotales = 0;
    private double caloriasRecomendadas;
    private Usuario usuario;

    private NotifcacionesActivity notificacionesManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comidas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        notificacionesManager.mostrarNotificacionRecordatorioComida("almuerzo");
        caloriasRecomendadas = getIntent().getDoubleExtra("caloriasRecomendadas", 0);

        etComida = findViewById(R.id.etComida);
        etCalorias = findViewById(R.id.etCalorias);
        btnAgregarComida = findViewById(R.id.btnAgregarComida);
        tvCaloriasTotales = findViewById(R.id.tvCaloriasTotales);
        rvComidas = findViewById(R.id.rvComidas);

        listaComidas = new ArrayList<>();
        adapter = new ComidaAdapter(listaComidas);
        rvComidas.setLayoutManager(new LinearLayoutManager(this));
        rvComidas.setAdapter(adapter);

        btnAgregarComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreComida = etComida.getText().toString();
                float calorias = Float.parseFloat(etCalorias.getText().toString());
                caloriasTotales += calorias;
                listaComidas.add(new Comida(nombreComida, calorias));
                adapter.notifyDataSetChanged();
                tvCaloriasTotales.setText("Total Calorías Consumidas: " + caloriasTotales +
                        " / Recomendadas: " + caloriasRecomendadas);

                // Verificar si se exceden las calorías recomendadas
                if (caloriasTotales > caloriasRecomendadas) {
                    notificacionesManager.mostrarNotificacionExcesoCalorias(caloriasTotales, caloriasRecomendadas);
                }
                notificacionesManager.mostrarNotificacionRecordatorioComida("almuerzo");

// Para motivación, podrías establecer un temporizador que llame a esto en intervalos
                notificacionesManager.mostrarNotificacionMotivacion("Sigue adelante, ¡tú puedes!");
                etComida.setText("");
                etCalorias.setText("");
            }
        });
    }
}