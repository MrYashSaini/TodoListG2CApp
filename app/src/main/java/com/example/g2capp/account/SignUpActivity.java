package com.example.g2capp.account;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.g2capp.MainActivity;
import com.example.g2capp.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        onClickFun();
    }

    private void onClickFun() {
        binding.tvSignUpLoginAccount.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            finish();
        });

        binding.btnSignUp.setOnClickListener(v -> {

            if (binding.etSignUpGmailBox.getText().toString().isEmpty()){
                Toast.makeText(this, "Enter Gmail Id", Toast.LENGTH_SHORT).show();
            }
            else if (binding.etSignUpPasswordBox.getText().toString().isEmpty()){
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            }
            else if (binding.etSignUpConformPasswordBox.getText().toString().isEmpty()){
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            }
            else if (!binding.etSignUpPasswordBox.getText().toString().equals(binding.etSignUpConformPasswordBox.getText().toString())){
                Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show();
            }
            else {
                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setTitle("Create Account");
                dialog.setMessage("Create Your Account Please Wait...");
                dialog.show();
                auth.createUserWithEmailAndPassword(binding.etSignUpGmailBox.getText().toString(),binding.etSignUpPasswordBox.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                dialog.dismiss();
                                finish();
                            }
                            else {
                                dialog.dismiss();
                                Toast.makeText(this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        });
    }
}