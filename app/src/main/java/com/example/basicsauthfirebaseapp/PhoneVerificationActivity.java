package com.example.basicsauthfirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneVerificationActivity extends AppCompatActivity {

    private EditText editTextNumber;
    private EditText editTextPin;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String verificationId;
    private Button btnSendOTP;
    private Button verifyOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);
        init();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        editTextNumber = findViewById(R.id.text_view_id);
        editTextPin = findViewById(R.id.edit_text_otp);
        btnSendOTP = findViewById(R.id.btnSendOtp);
        editTextPin.setVisibility(View.GONE);
        verifyOTP = findViewById(R.id.verify_otp);


        btnSendOTP.setOnClickListener(v -> {
            // User clicked "Send OTP" button
            String phoneNumber = editTextNumber.getText().toString().trim();

            if (!phoneNumber.isEmpty()) {
                // Show OTP EditText for manual entry
                editTextPin.setVisibility(View.VISIBLE);
                // Trigger the OTP verification process
                verification(phoneNumber);
            } else {
                showToast("Please enter a valid mobile number.");
            }
        });
        verifyOTP.setOnClickListener(v -> {
            // User clicked "Verify OTP" button
            String enteredOTP = editTextPin.getText().toString().trim();

            if (!enteredOTP.isEmpty()) {
                // Verify the entered OTP
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, enteredOTP);
                signInWithPhoneAuthCredential(credential);
            } else {
                showToast("Please enter the OTP.");
            }
        });

    }


    private void verification(String phoneNumber) {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            // ... (unchanged)
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // Verification failed
                // Handle the error

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // e.g., the phone number format is incorrect
                    showToast("Invalid phone number. Please enter a valid phone number.");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    showToast("Quota exceeded. Please try again later.");
                } else {
                    // General error
                    showToast("Verification failed. Please try again.");
                }
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                verificationId = s;
            }
        };

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(PhoneVerificationActivity.this)
                .setCallbacks(mCallbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Authentication success
                        Intent intent = new Intent(PhoneVerificationActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Authentication failed
                        showToast("The verification failed. Can't take you to the Main Screen.");
                    }
                });
    }

    private void showToast(String message) {
        Toast.makeText(PhoneVerificationActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}




;