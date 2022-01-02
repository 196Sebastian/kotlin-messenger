package com.example.kotlinmessenger.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinmessenger.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity:AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtRegister: EditText
    private lateinit var btnLogin: Button
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.editText_login_email)
        edtPassword = findViewById(R.id.editText_login_password)
        edtRegister = findViewById(R.id.register_text)
        btnLogin = findViewById(R.id.button_login)



        edtRegister.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java ))
        }

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            login(email, password)
        }
    }

    private fun login(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    Log.d("LoginActivity", "User Successfully Logged in")

                    val intent = Intent(this@LoginActivity, MessagesActivity::class.java)
                    startActivity(intent)

                }else {
                    Toast.makeText(this@LoginActivity, "User Does Not Exist", Toast.LENGTH_SHORT).show()
                }
            }
    }
}