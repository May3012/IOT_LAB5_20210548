package com.example.lab5.Notificaciones;
import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
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
import com.example.lab5.Activitys.MainActivity;
import com.example.lab5.R;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;


public class NotifcacionesActivity {
    private Context context;
    private NotificationManager notificationManager;
    private final String CHANNEL_ID = "calories_notifications_channel";

    public NotifcacionesActivity(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Calories Notifications";
            String description = "Notifications for calorie tracking";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Notificación de exceso de calorías
    public void mostrarNotificacionExcesoCalorias(double caloriasTotales, double caloriasRecomendadas) {
        String title = "¡Exceso de Calorías!";
        String message = "Has consumido " + caloriasTotales + " calorías, superando tu límite de " + caloriasRecomendadas +
                ". Prueba hacer ejercicio o reducir calorías en la próxima comida.";

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_fastfood_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true) // Para que se cancele al tocar
                .build();

        notificationManager.notify(1, notification);
    }

    // Recordatorio personalizado de comidas
    public void mostrarNotificacionRecordatorioComida(String comida) {
        String title = "Recordatorio de Comida";
        String message = "Es hora de registrar tu " + comida + "!";

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_fastfood_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true) // Para que se cancele al tocar
                .build();

        notificationManager.notify(2, notification);
    }

    // Notificación de motivación cada cierto intervalo
    public void iniciarNotificacionMotivacion(int intervaloMinutos) {
        String title = "¡Sigue adelante!";
        String message = "¡Estás haciendo un gran trabajo! Mantente enfocado en tus objetivos.";

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_fastfood_24)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true) // Para que se cancele al tocar
                .build();

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long intervaloMillis = intervaloMinutos * 60 * 1000;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), intervaloMillis, pendingIntent);
        } else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), intervaloMillis, pendingIntent);
        }
    }
}