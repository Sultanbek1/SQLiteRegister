package com.example.singupinsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final AppCompatActivity activity = MainActivity.this;

    private ImageView imgGoToRegister;
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    public void initViews() {
        imgGoToRegister = findViewById(R.id.goToRegister);
        imgGoToRegister.setOnClickListener(this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        databaseHelper = new DatabaseHelper(activity);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                check();
                verifyFromSQlite();
                break;
            case R.id.goToRegister:
                Intent dsp = new Intent(getApplicationContext(), RegisterActivity.class);
                // можно вместо this сделать getApplicationContext() или MainActivity.this
                startActivity(dsp);
                break;
        }
    }

    private void check() {
        if (etEmail == null) {
            Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_SHORT).show();
        }
        if (etPassword == null) {
            Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
        }
    }

    private void verifyFromSQlite() {
        if (databaseHelper.checkUser(etEmail.getText().toString().trim(),
                etPassword.getText().toString().trim())) {

            Intent accountsIntent = new Intent(activity, Finish.class);
            accountsIntent.putExtra("EMAIL", etEmail.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);
        } else {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.error_valid_email_password), Toast.LENGTH_SHORT).show();
        }
    }

    private void emptyInputEditText() {
        etEmail.setText(null);
        etPassword.setText(null);
    }
}