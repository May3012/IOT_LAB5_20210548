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
import android.widget.Toast;

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

        // Gestor de notificaciones
       notificacionesManager = new NotifcacionesActivity(this); // Aquí puedes pasar el contexto si es necesario

        // Notificación inicial
        notificacionesManager.mostrarNotificacionRecordatorioComida("almuerzo");
        caloriasRecomendadas = getIntent().getDoubleExtra("caloriasRecomendadas", 0);

        // Vistas y adaptador
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
                String nombreComida = etComida.getText().toString().trim();
                String caloriasInput = etCalorias.getText().toString().trim();

                if (nombreComida.isEmpty() || caloriasInput.isEmpty()) {
                    Toast.makeText(ComidasActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    float calorias = Float.parseFloat(caloriasInput);
                    caloriasTotales += calorias;
                    listaComidas.add(new Comida(nombreComida, calorias));
                    adapter.notifyDataSetChanged();
                    tvCaloriasTotales.setText("Total Calorías Consumidas: " + caloriasTotales +
                            " / Recomendadas: " + caloriasRecomendadas);

                    if (caloriasTotales > caloriasRecomendadas) {
                        notificacionesManager.mostrarNotificacionExcesoCalorias(caloriasTotales, caloriasRecomendadas);
                    }

                    notificacionesManager.mostrarNotificacionRecordatorioComida("almuerzo");
                    etComida.setText("");
                    etCalorias.setText("");
                } catch (NumberFormatException e) {
                    Toast.makeText(ComidasActivity.this, "Por favor, ingresa un número válido para las calorías", Toast.LENGTH_SHORT).show();
                }
            }
        });
}}