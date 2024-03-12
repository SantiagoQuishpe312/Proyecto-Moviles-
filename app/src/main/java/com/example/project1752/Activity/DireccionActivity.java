package com.example.project1752.Activity;

import com.example.project1752.R;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;




import java.util.Random;

public class DireccionActivity extends AppCompatActivity {
    private Button btnAceptar,btnMap;
    private static final String CHANNEL_ID = "channel_id";
    private static final int NOTIFICATION_ID = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion);
        btnAceptar = findViewById(R.id.btnAceptar1);
        crearCanal();
        btnAceptar.setOnClickListener(v -> {
            Intent intent = new Intent(DireccionActivity.this, ProcesoPedidoActivity.class);
            startActivity(intent);
            crearNotificacion();
        });

        btnMap=findViewById(R.id.btnUbicacion);
        btnMap.setOnClickListener(v -> {
            Intent intent = new Intent(DireccionActivity.this, MapActivity.class);
            startActivity(intent);
        });

    }

    @SuppressLint("MissingPermission")
    private void crearNotificacion() {
        Random random = new Random();

        // Generar un número aleatorio entre 1 y 100 (cambiar según tus necesidades)
        int numeroAleatorio = random.nextInt(100) + 1;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setContentTitle("Tu pedido se esta enviará con la orden : "+numeroAleatorio);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setColor(Color.GREEN);
        builder.setVibrate(new long[]{1000, 200, 1000, 2000});
        builder.setTicker("Tu pedido se esta preparando");
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

    }

    private void crearCanal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notificaciones";
            String description = "Canal para notificaciones";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
    }
}