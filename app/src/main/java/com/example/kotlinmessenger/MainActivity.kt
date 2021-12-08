package com.example.kotlinmessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_register).setOnClickListener {
            preformRegister()
        }

        findViewById<TextView>(R.id.already_have_account_textView).setOnClickListener{
            Log.d("MainActivity", "Try to show login activity")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun preformRegister() {
        val email: String = findViewById<EditText>(R.id.editText_email).text.toString()
        val password: String = findViewById<EditText>(R.id.editText_password).text.toString()

        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter in your email/password", Toast.LENGTH_SHORT).show()
        }

        Log.d("MainActivity", "Email is: $email")
        Log.d("MainActivity", "Password: $password")

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if(!it.isSuccessful) return@addOnCompleteListener

                Log.d("Main", "Successfully created a user with uid: ${it.result?.user?.uid}")
            }
            .addOnFailureListener{
                Log.d("Main", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }




}