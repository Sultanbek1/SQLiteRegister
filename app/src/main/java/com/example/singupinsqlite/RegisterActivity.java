package com.example.singupinsqlite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;
    private ImageView imgGoToLogin;

    private EditText etFullname, etEmail, etPassword, etConfirmPass;
    private Button btnJoinUs;

    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
    }

    public void initViews() {
        imgGoToLogin = findViewById(R.id.goToLogin);
        imgGoToLogin.setOnClickListener(this);

        etFullname = findViewById(R.id.etFullname);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPass = findViewById(R.id.etConfirmPass);

        btnJoinUs = findViewById(R.id.btnJoinUs);
        btnJoinUs.setOnClickListener(this);

        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnJoinUs:
                postDataToSQLite();

                break;
            case R.id.goToLogin:
                finish();
                break;
        }
    }

    private void postDataToSQLite() {
        if (!databaseHelper.checkUser(etEmail.getText().toString().trim())) {
            user.setName(etFullname.getText().toString().trim());
            user.setEmail(etEmail.getText().toString().trim());
            user.setPassword(etPassword.getText().toString().trim());
            databaseHelper.addUser(user);
            Toast.makeText(getApplicationContext(), getString(R.string.success_message), Toast.LENGTH_SHORT).show();
            Intent accountsIntent = new Intent(activity, MainActivity.class);
            emptyInputEditText();
            startActivity(accountsIntent);
        } else {
            Toast.makeText(this, "Email is not existed", Toast.LENGTH_SHORT).show();
        }
    }

    private void emptyInputEditText() {
        etFullname.setText(null);
        etEmail.setText(null);
        etPassword.setText(null);
        etConfirmPass.setText(null);
    }
}