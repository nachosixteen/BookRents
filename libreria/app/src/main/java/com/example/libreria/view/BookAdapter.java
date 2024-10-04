package com.example.libreria.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libreria.R;
import com.example.libreria.model.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> books;
    private OnBookClickListener onBookClickListener;
    private OnBookDeleteListener onBookDeleteListener;

    public interface OnBookClickListener {
        void onBookClick(Book book);
    }

    public interface OnBookDeleteListener {
        void onBookDelete(Book book);
    }

    public BookAdapter(List<Book> books, OnBookClickListener onBookClickListener, OnBookDeleteListener onBookDeleteListener) {
        this.books = books;
        this.onBookClickListener = onBookClickListener;
        this.onBookDeleteListener = onBookDeleteListener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.bind(book, onBookClickListener, onBookDeleteListener);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView txtBookName, txtAvailable, txtCost;
        private Button btnEdit, btnDelete;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBookName = itemView.findViewById(R.id.txtName);
            txtAvailable = itemView.findViewById(R.id.txtAvailable);
            txtCost = itemView.findViewById(R.id.txtCost);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        public void bind(final Book book, OnBookClickListener clickListener, OnBookDeleteListener deleteListener) {
            txtBookName.setText(book.getName());
            txtAvailable.setText(book.isAvailable() ? "Disponible" : "No Disponible");
            txtCost.setText(String.valueOf(book.getCoste()));

            btnEdit.setOnClickListener(v -> clickListener.onBookClick(book));
            btnDelete.setOnClickListener(v -> deleteListener.onBookDelete(book));
        }
    }
}
