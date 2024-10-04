package com.example.libreria.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.libreria.R;
import com.example.libreria.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;
    private final OnUserClickListener listener;


    public interface OnUserClickListener {
        void onEdit(User user);
        void onDelete(User user);
    }

    public UserAdapter(OnUserClickListener listener) {
        this.listener = listener;
        this.userList = new ArrayList<>();
    }

    public void updateUserList(List<User> users) {
        this.userList = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user, listener);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewEmail, textViewStatus;
        Button btnEdit, btnDelete;

        public UserViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        public void bind(final User user, final OnUserClickListener listener) {
            textViewName.setText(user.getName());
            textViewEmail.setText(user.getEmail());
            textViewStatus.setText(user.getStatus());

            btnEdit.setOnClickListener(v -> listener.onEdit(user));
            btnDelete.setOnClickListener(v -> listener.onDelete(user));
        }
    }
}
