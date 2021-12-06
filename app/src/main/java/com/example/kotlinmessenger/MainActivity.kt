package com.example.kotlinmessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonRegister: Button = findViewById(R.id.button_register)

        buttonRegister.setOnClickListener {
            val email: EditText = findViewById(R.id.editText_email)
            val password: EditText = findViewById(R.id.editText_password)

            email.text.toString()

            Log.d("MainActivity", "Email is: $email")
            Log.d("MainActivity", "Password: $password")
        }

    }
}