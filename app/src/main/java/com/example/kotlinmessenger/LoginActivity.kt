package com.example.kotlinmessenger

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.button_login).setOnClickListener {
            val email: String = findViewById<EditText>(R.id.editText_login_email).text.toString()
            val password: String = findViewById<EditText>(R.id.editText_login_password).text.toString()

            Log.d("Login", "Attempt login with email/password: $email/***")

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        }

        findViewById<TextView>(R.id.back_to_registration).setOnClickListener{
            finish()
        }


    }
}