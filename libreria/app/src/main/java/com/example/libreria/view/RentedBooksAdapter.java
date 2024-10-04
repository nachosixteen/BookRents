package com.example.libreria.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libreria.R;
import com.example.libreria.model.Rent;

import java.util.List;

public class RentedBooksAdapter extends RecyclerView.Adapter<RentedBooksAdapter.ViewHolder> {

    private List<Rent> rentedBooks;
    private Context context;
    private OnReturnClickListener onReturnClickListener;

    public RentedBooksAdapter(List<Rent> rentedBooks, OnReturnClickListener onReturnClickListener) {
        this.rentedBooks = rentedBooks;
        this.onReturnClickListener = onReturnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rented_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Rent rent = rentedBooks.get(position);

        if (rent != null) {
            holder.textViewBook.setText(rent.getBook() != null ? rent.getBook().getName() : "Libro no disponible");
            holder.textViewUser.setText(rent.getUser() != null ? rent.getUser().getName() : "Usuario no disponible");
            holder.textViewDate.setText(rent.getDate());

            holder.btnReturnBook.setOnClickListener(v -> onReturnClickListener.onReturnClick(rent));
        } else {
            holder.textViewBook.setText("Renta no disponible");
            holder.textViewUser.setText("");
            holder.textViewDate.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return rentedBooks.size();
    }

    public interface OnReturnClickListener {
        void onReturnClick(Rent rent);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBook, textViewUser, textViewDate;
        Button btnReturnBook;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBook = itemView.findViewById(R.id.textViewBook);
            textViewUser = itemView.findViewById(R.id.textViewUser);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            btnReturnBook = itemView.findViewById(R.id.btnReturnBook);
        }
    }
}
