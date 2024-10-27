package com.example.lab5.Activitys;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab5.Notificaciones.NotifcacionesActivity;
import com.example.lab5.Objetos.Usuario;
import com.example.lab5.R;

public class MainActivity extends AppCompatActivity {
    private EditText etPeso, etAltura, etEdad;
    private Spinner spGenero, spNivelActividad, spObjetivo;
    private Button btnGuardar;
    private TextView tvCaloriasDiarias;
    private double caloriasDiarias;
    private Usuario usuario;
    private NotifcacionesActivity notificacionesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuración para el ajuste de pantalla completa
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializacion de notificaciones
        notificacionesManager = new NotifcacionesActivity(this);
        int intervaloMinutos = 2; // Intervalo de notificación en minutos
        notificacionesManager.iniciarNotificacionMotivacion(intervaloMinutos);
        double caloriasTotales = 2500;
        double caloriasRecomendadas = 2000;
        notificacionesManager.mostrarNotificacionExcesoCalorias(caloriasTotales, caloriasRecomendadas);

        // Inicializacion de vistas
        etPeso = findViewById(R.id.etPeso);
        etAltura = findViewById(R.id.etAltura);
        etEdad = findViewById(R.id.etEdad);
        spGenero = findViewById(R.id.spGenero);
        spNivelActividad = findViewById(R.id.spNivelActividad);
        spObjetivo = findViewById(R.id.spObjetivo);
        btnGuardar = findViewById(R.id.btnGuardar);
        tvCaloriasDiarias = findViewById(R.id.tvCaloriasDiarias);

        // Spinners con adaptadores de Array
        configurarSpinners();

        // Botón de guardar
        btnGuardar.setOnClickListener(v -> {
            if (guardarDatos()) {
                calcularCaloriasDiarias();
                Intent intent = new Intent(MainActivity.this, ComidasActivity.class);
                intent.putExtra("caloriasRecomendadas", caloriasDiarias);
                startActivity(intent);
            }
        });
    }

    private void configurarSpinners() {
        ArrayAdapter<CharSequence> adapterGenero = ArrayAdapter.createFromResource(this,
                R.array.genero_array, android.R.layout.simple_spinner_item);
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenero.setAdapter(adapterGenero);

        ArrayAdapter<CharSequence> adapterNivelActividad = ArrayAdapter.createFromResource(this,
                R.array.nivel_actividad_array, android.R.layout.simple_spinner_item);
        adapterNivelActividad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNivelActividad.setAdapter(adapterNivelActividad);

        ArrayAdapter<CharSequence> adapterObjetivo = ArrayAdapter.createFromResource(this,
                R.array.objetivo_array, android.R.layout.simple_spinner_item);
        adapterObjetivo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spObjetivo.setAdapter(adapterObjetivo);
    }

    private boolean guardarDatos() {
        // Obtenemos los valores de los campos
        String peso = etPeso.getText().toString();
        String altura = etAltura.getText().toString();
        String edad = etEdad.getText().toString();
        String genero = spGenero.getSelectedItem().toString();
        String nivelActividad = spNivelActividad.getSelectedItem().toString();
        String objetivo = spObjetivo.getSelectedItem().toString();

        // Verificamos que los campos no estén vacíos
        if (peso.isEmpty() || altura.isEmpty() || edad.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return false; // No se guardaron los datos
        }

        // Creamos y almacenamos el  Usuario
        usuario = new Usuario(peso, altura, edad, genero, nivelActividad, objetivo);
        Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show();
        return true; // Se guardaron los datos correctamente
    }

    private void calcularCaloriasDiarias() {
        if (usuario == null) {
            Toast.makeText(this, "No se han guardado los datos del usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        double peso = Double.parseDouble(usuario.getPeso());
        double altura = Double.parseDouble(usuario.getAltura());
        int edad = Integer.parseInt(usuario.getEdad());
        String genero = usuario.getGenero();
        String nivelActividad = usuario.getNivelActividad();
        String objetivo = usuario.getObjetivo();

        double tmb;

        // Fórmula
        if (genero.equals("Masculino")) {
            tmb = 5 + (10 * peso) + (6.25 * altura) - (5 * edad);
        } else {
            tmb = -161 + (10 * peso) + (6.25 * altura) - (5 * edad);
        }

        // Cálculo de calorías diarias
        double factorActividad = obtenerFactorActividad(nivelActividad);
        caloriasDiarias = tmb * factorActividad;

        // Según el objetivo
        if (objetivo.equals("Subir de peso")) {
            caloriasDiarias += 500;
        } else if (objetivo.equals("Bajar de peso")) {
            caloriasDiarias -= 300;
        }

        tvCaloriasDiarias.setText("Calorías diarias recomendadas: " + Math.round(caloriasDiarias) + " kcal");
    }

    private double obtenerFactorActividad(String nivelActividad) {
        switch (nivelActividad) {
            case "Sedentario":
                return 1.2;
            case "Ligero":
                return 1.375;
            case "Moderado":
                return 1.55;
            case "Activo":
                return 1.725;
            case "Muy activo":
                return 1.9;
            default:
                return 1.2; // Por defecto, asumimos un estilo de vida sedentario
        }
    }
}