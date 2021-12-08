package com.example.kotlinmessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_register).setOnClickListener {
            val email: String = findViewById<EditText>(R.id.editText_email).text.toString()
            val password: String = findViewById<EditText>(R.id.editText_password).text.toString()

            Log.d("MainActivity", "Email is: $email")
            Log.d("MainActivity", "Password: $password")
        }

        findViewById<TextView>(R.id.already_have_account_textView).setOnClickListener{
            Log.d("MainActivity", "Try to show login activity")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }



    }
}