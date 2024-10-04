package com.example.libreria.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.libreria.R;

public class MenuActivity extends AppCompatActivity {
    private Button btnRent, btnCreateBook, btnEditBook, btnDeleteBook, btnListBooks, btnManageUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnRent = findViewById(R.id.btnRentBook);
        btnCreateBook = findViewById(R.id.btnCreateBook);

        btnManageUsers = findViewById(R.id.btnManageUsers); // Agregar esta línea

        btnRent.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, RentBookActivity.class);
            startActivity(intent);
        });

        btnCreateBook.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, CreateBookActivity.class);
            startActivity(intent);
        });


        // Añadir OnClickListener para el botón gestionar usuarios
        btnManageUsers.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ManageUserActivity.class);
            startActivity(intent);
        });
    }
}
