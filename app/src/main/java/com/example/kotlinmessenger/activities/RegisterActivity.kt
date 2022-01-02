package com.example.kotlinmessenger.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.kotlinmessenger.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var edtUsername: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var txtviewAlreadyHaveAcc: TextView
    private lateinit var btnRegister: Button
    private lateinit var imageSelect: ImageView
    private lateinit var mAuth: FirebaseAuth

    var selectedPhotoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.editText_login_email)
        edtPassword = findViewById(R.id.editText_login_password)
        edtUsername = findViewById(R.id.editText_username)
        imageSelect = findViewById(R.id.select_photo_image)
        txtviewAlreadyHaveAcc = findViewById(R.id.already_have_account_textView)
        btnRegister = findViewById(R.id.button_register)

        txtviewAlreadyHaveAcc.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

      imageSelect.setOnClickListener{
            Log.d("RegisterActivity", "Try to show select photo selector")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        btnRegister.setOnClickListener {
            uploadImageToFirebaseStorage()

            val email = edtEmail.toString()
            val password = edtPassword.toString()

            userRegisteredSuccess(email, password)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == RESULT_OK && data != null) {
            Log.d("RegisterActivity", "Photo was selected")

            selectedPhotoUri = data.data

            findViewById<CircleImageView>(R.id.select_photo_imageView).setImageURI(selectedPhotoUri)

            findViewById<ImageView>(R.id.select_photo_image).alpha = 0f
            findViewById<TextView>(R.id.select_photo_text).alpha = 0f
        }
    }

    private fun userRegisteredSuccess(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    Log.d("RegisterActivity", "Successfully Registered New User")

                    val intent = Intent(this@RegisterActivity, MessagesActivity::class.java)
                    startActivity(intent)
                }else {
                    Log.d("RegisterActivity", "Error Trying To Register New User")

                    Toast.makeText(this@RegisterActivity, "Error Trying To Register New User", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun uploadImageToFirebaseStorage() {
        if(selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener { it ->
                Log.d("RegisterActivity","Successfully uploaded image: ${it.metadata?.path}")
            }
            .addOnFailureListener{
                // Do some logging here
            }
    }
}