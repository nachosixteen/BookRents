package com.example.libreria.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.libreria.R;

public class MainActivity extends AppCompatActivity {
    private Button btnGoToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Mensaje de bienvenida
        TextView txtWelcome = findViewById(R.id.txtWelcome);
        txtWelcome.setText("¡Bienvenidos a Navegantes de Páginas!");

        btnGoToMenu = findViewById(R.id.btnGoToMenu);

        // Navegar al menú al hacer clic en el botón
        btnGoToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
