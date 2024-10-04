package com.example.libreria.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libreria.R;
import com.example.libreria.controller.UserCRUD;
import com.example.libreria.model.User;

import java.util.List;

public class ManageUserActivity extends AppCompatActivity {

    private EditText editTextId, editTextName, editTextEmail, editTextPassword;
    private Spinner spinnerStatus;
    private Button btnAddUser, btnUpdateUser;
    private RecyclerView recyclerViewUsers;
    private UserCRUD userCRUD;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        userCRUD = new UserCRUD(this);

        editTextId = findViewById(R.id.editTextId);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        btnAddUser = findViewById(R.id.btnAddUser);
        btnUpdateUser = findViewById(R.id.btnUpdateUser);
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);

        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(new UserAdapter.OnUserClickListener() {
            @Override
            public void onEdit(User user) {
                editUser(user);
            }

            @Override
            public void onDelete(User user) {
                confirmDeleteUser(user);
            }
        });
        recyclerViewUsers.setAdapter(userAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        btnAddUser.setOnClickListener(v -> addUser());
        btnUpdateUser.setOnClickListener(v -> updateUser());
        loadUsers();
    }

    private void loadUsers() {
        List<User> users = userCRUD.getAllUsers();
        userAdapter.updateUserList(users);
    }

    private void addUser() {
        int id = Integer.parseInt(editTextId.getText().toString());
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String status = spinnerStatus.getSelectedItem().toString();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(id, name, email, password, status);
        userCRUD.addUser(user);
        Toast.makeText(this, "Usuario creado: " + name, Toast.LENGTH_LONG).show();
        clearFields();
        loadUsers();
    }

    private void updateUser() {
        int id;
        try {
            id = Integer.parseInt(editTextId.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = userCRUD.getUserById(id);
        if (user != null) {
            user.setName(editTextName.getText().toString());
            user.setEmail(editTextEmail.getText().toString());
            user.setPass(editTextPassword.getText().toString());
            user.setStatus(spinnerStatus.getSelectedItem().toString());
            userCRUD.updateUser(user);
            loadUsers();
            Toast.makeText(this, "Usuario actualizado", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void editUser(User user) {
        editTextId.setText(String.valueOf(user.getIdusar()));
        editTextName.setText(user.getName());
        editTextEmail.setText(user.getEmail());
        editTextPassword.setText(user.getPass());
        int spinnerPosition = ((ArrayAdapter<String>) spinnerStatus.getAdapter()).getPosition(user.getStatus());
        spinnerStatus.setSelection(spinnerPosition);
    }

    private void confirmDeleteUser(User user) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Usuario")
                .setMessage("¿Está seguro de eliminar al usuario " + user.getName() + " (ID: " + user.getIdusar() + ")?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    userCRUD.deleteUser(user.getIdusar());
                    Toast.makeText(this, "Usuario eliminado", Toast.LENGTH_SHORT).show();
                    loadUsers();
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    private void clearFields() {
        editTextId.setText("");
        editTextName.setText("");
        editTextEmail.setText("");
        editTextPassword.setText("");
        spinnerStatus.setSelection(0);
    }
}
