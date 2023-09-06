package com.example.g2capp.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.g2capp.MainActivity;
import com.example.g2capp.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();


        onClickFun();

        if (auth.getCurrentUser()!=null){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }

    private void onClickFun() {
        binding.tvLoginCreateAccount.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            finish();
        });

        binding.btnLogin.setOnClickListener(v -> {
            if(binding.etLoginGmailBox.getText().toString().isEmpty()){
                Toast.makeText(this, "Enter Gmail Id", Toast.LENGTH_SHORT).show();
            }
            else if (binding.etLoginPasswordBox.getText().toString().isEmpty()){
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            }
            else {
                auth.signInWithEmailAndPassword(binding.etLoginGmailBox.getText().toString(),binding.etLoginPasswordBox.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}