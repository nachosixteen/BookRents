package com.example.libreria.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libreria.R;
import com.example.libreria.controller.BookCRUD;
import com.example.libreria.controller.RentCRUD;
import com.example.libreria.controller.UserCRUD;
import com.example.libreria.model.Book;
import com.example.libreria.model.Rent;
import com.example.libreria.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RentBookActivity extends AppCompatActivity {

    private Spinner spinnerUsers, spinnerBooks;
    private Button btnRentBook;
    private RecyclerView recyclerViewRentedBooks;

    private UserCRUD userCRUD;
    private BookCRUD bookCRUD;
    private RentCRUD rentCRUD;
    private RentedBooksAdapter rentedBooksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_book);

        userCRUD = new UserCRUD(this);
        bookCRUD = new BookCRUD(this);
        rentCRUD = new RentCRUD(this);

        spinnerUsers = findViewById(R.id.spinnerUsers);
        spinnerBooks = findViewById(R.id.spinnerBooks);
        btnRentBook = findViewById(R.id.btnRentBook);
        recyclerViewRentedBooks = findViewById(R.id.recyclerViewRentedBooks);
        recyclerViewRentedBooks.setLayoutManager(new LinearLayoutManager(this));

        loadUsers();
        loadAvailableBooks();
        loadRentedBooks();

        btnRentBook.setOnClickListener(v -> rentBook());
    }

    private void loadUsers() {
        List<User> users = userCRUD.getAllUsers();
        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUsers.setAdapter(adapter);
    }

    private void loadAvailableBooks() {
        List<Book> availableBooks = bookCRUD.getAvailableBooks();
        ArrayAdapter<Book> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, availableBooks);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBooks.setAdapter(adapter);
    }

    private void loadRentedBooks() {
        List<Rent> rents = rentCRUD.getAllRents();
        rentedBooksAdapter = new RentedBooksAdapter(rents, this::returnBook);
        recyclerViewRentedBooks.setAdapter(rentedBooksAdapter);
    }


    private void rentBook() {
        User selectedUser = (User) spinnerUsers.getSelectedItem();
        Book selectedBook = (Book) spinnerBooks.getSelectedItem();

        if (selectedUser == null || selectedBook == null) {
            Toast.makeText(this, "Seleccione un usuario y un libro.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedUser.getStatus().equals("Sancionado")) {
            showStatusConfirmation ();
            return;
        }

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Rent rent = new Rent(0, selectedUser, selectedBook, date);
        rentCRUD.addRent(rent);

        bookCRUD.updateBookAvailability(selectedBook.getIdbook(), 0);

        Toast.makeText(this, "Libro rentado exitosamente.", Toast.LENGTH_SHORT).show();

        loadAvailableBooks();
        loadRentedBooks();
    }

    private void showStatusConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Usuario Sancionado")
                .setMessage("Este usuario no tiene permitido rentar libros")
                .setPositiveButton("OK", null)
                .show();
    }

    private void returnBook(Rent rent) {
        rentCRUD.deleteRent(rent.getIdrent());
        bookCRUD.updateBookAvailability(rent.getBook().getIdbook(), 1);
        loadRentedBooks();
        Toast.makeText(this, "Libro devuelto exitosamente.", Toast.LENGTH_SHORT).show();
        loadAvailableBooks();
    }
}
