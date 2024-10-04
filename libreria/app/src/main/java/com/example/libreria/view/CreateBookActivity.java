package com.example.libreria.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libreria.R;
import com.example.libreria.controller.BookCRUD;
import com.example.libreria.model.Book;

import java.util.List;

public class CreateBookActivity extends AppCompatActivity {
    private EditText edtBookId, edtBookName, edtCost;
    private Spinner spinnerAvailable;
    private Button btnCreateBook, btnUpdateBook;
    private RecyclerView recyclerBooks;

    private BookCRUD bookCRUD;
    private BookAdapter bookAdapter;
    private List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);

        edtBookId = findViewById(R.id.edtBookId);
        edtBookName = findViewById(R.id.edtBookName);
        edtCost = findViewById(R.id.edtCost);
        spinnerAvailable = findViewById(R.id.spinnerAvailable);
        btnCreateBook = findViewById(R.id.btnCreateBook);
        btnUpdateBook = findViewById(R.id.btnUpdateBook);
        recyclerBooks = findViewById(R.id.recyclerBooks);
        bookCRUD = new BookCRUD(this);

        // Configura el Spinner para seleccionar la disponibilidad
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.availability_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAvailable.setAdapter(adapter);

        btnCreateBook.setOnClickListener(v -> createBook());
        btnUpdateBook.setOnClickListener(v -> updateBook());

        loadBooks();
    }

    private void createBook() {
        if (validateFields()) {
            int idbook = Integer.parseInt(edtBookId.getText().toString());
            String name = edtBookName.getText().toString();
            double cost = Double.parseDouble(edtCost.getText().toString());
            boolean available = spinnerAvailable.getSelectedItemPosition() == 0;

            Book book = new Book();
            book.setIdbook(idbook);
            book.setName(name);
            book.setCoste(cost);
            book.setAvailable(available);

            bookCRUD.addBook(book);
            Toast.makeText(this, "Libro creado con éxito", Toast.LENGTH_SHORT).show();
            loadBooks();
            clearFields();
        }
    }

    private void updateBook() {
        if (validateFields()) {
            int idbook = Integer.parseInt(edtBookId.getText().toString());
            String name = edtBookName.getText().toString();
            double cost = Double.parseDouble(edtCost.getText().toString());
            boolean available = spinnerAvailable.getSelectedItemPosition() == 0;

            Book book = new Book();
            book.setIdbook(idbook);
            book.setName(name);
            book.setCoste(cost);
            book.setAvailable(available);

            bookCRUD.updateBook(book);
            Toast.makeText(this, "Libro actualizado con éxito", Toast.LENGTH_SHORT).show();
            loadBooks();
            clearFields();
            btnUpdateBook.setEnabled(false);
        }
    }

    private void loadBooks() {
        books = bookCRUD.getAllBooks();
        bookAdapter = new BookAdapter(books,
                this::loadBookDetails, // Callback para cargar detalles
                this::showDeleteConfirmation); // Callback para eliminar
        recyclerBooks.setLayoutManager(new LinearLayoutManager(this));
        recyclerBooks.setAdapter(bookAdapter);
    }


    private void loadBookDetails(Book book) {
        edtBookId.setText(String.valueOf(book.getIdbook()));
        edtBookName.setText(book.getName());
        edtCost.setText(String.valueOf(book.getCoste()));
        spinnerAvailable.setSelection(book.isAvailable() ? 0 : 1);
        btnUpdateBook.setEnabled(true);
    }

    private void showDeleteConfirmation(Book book) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar libro")
                .setMessage("¿Estás seguro de que deseas eliminar el libro " + book.getName() + "?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    bookCRUD.deleteBook(book.getIdbook());
                    Toast.makeText(this, "Libro eliminado con éxito", Toast.LENGTH_SHORT).show();
                    loadBooks();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private boolean validateFields() {
        if (edtBookId.getText().toString().isEmpty() ||
                edtBookName.getText().toString().isEmpty() ||
                edtCost.getText().toString().isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void clearFields() {
        edtBookId.setText("");
        edtBookName.setText("");
        edtCost.setText("");
        spinnerAvailable.setSelection(0);
    }
}
