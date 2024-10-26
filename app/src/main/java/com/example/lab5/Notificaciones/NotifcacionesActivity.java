package com.example.lab5.Notificaciones;
import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import com.example.lab5.Activitys.ComidasActivity;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.lab5.R;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;


public class NotifcacionesActivity extends Worker {
    private Context context;
    private static final String CHANNEL_ID = "canal_calorias";
    public NotifcacionesActivity(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        createNotificationChannel();
    }


    @NonNull
    @Override
    public Result doWork() {
        // Obtener el tipo de notificación desde los datos de entrada
        String tipoNotificacion = getInputData().getString("tipoNotificacion");

        switch (tipoNotificacion) {
            case "excesoCalorias":
                // Obtener las calorías consumidas y permitidas desde los datos de entrada
                double caloriasConsumidas = getInputData().getDouble("caloriasConsumidas", 0.0); // Valor predeterminado
                double caloriasPermitidas = getInputData().getDouble("caloriasPermitidas", 0.0); // Valor predeterminado
                mostrarNotificacionExcesoCalorias(caloriasConsumidas, caloriasPermitidas);
                break;
            case "recordatorioComida":
                String comida = getInputData().getString("comida"); // "desayuno", "almuerzo", o "cena"
                mostrarNotificacionRecordatorioComida(comida);
                break;
            case "objetivoMotivacion":
                String mensajeMotivacion = getInputData().getString("¡Sigue adelante!"); // Valor predeterminado
                mostrarNotificacionMotivacion(mensajeMotivacion);
                break;
            default:
                return Result.failure();
        }

        return Result.success();
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Notificaciones de Calorías",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }
    public void mostrarNotificacionExcesoCalorias(double caloriasTotales, double caloriasRecomendadas) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                //.setSmallIcon(R.drawable.ic_notification) // Cambia esto por tu icono
                .setContentTitle("Exceso de Calorías")
                .setContentText("Has consumido " + caloriasTotales + " calorías. Se recomienda hacer ejercicio o reducir las calorías en la próxima comida.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // Intención para abrir ComidasActivity
        Intent intent = new Intent(context, ComidasActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, builder.build());
        }
    }

    public void mostrarNotificacionRecordatorioComida(String comida) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                //.setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Recordatorio de Comida")
                .setContentText("Es hora de registrar tu " + comida + ".")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        Intent intent = new Intent(context, ComidasActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(2, builder.build());
        }
    }

    public void mostrarNotificacionMotivacion(String mensaje) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                //.setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Motivación")
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setAutoCancel(true);

        Intent intent = new Intent(context, ComidasActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(3, builder.build());
        }
    }

}