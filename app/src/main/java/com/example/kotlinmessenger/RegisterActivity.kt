package com.example.kotlinmessenger

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.MediaColumns.RELATIVE_PATH
import android.text.TextUtils
import android.util.Log
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private var selectedPhotoUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        findViewById<TextView>(R.id.already_have_account_textView).setOnClickListener{
            onBackPressed()
        }

        findViewById<ImageView>(R.id.select_photo_image).setOnClickListener{
            Log.d("RegisterActivity", "Try to show select photo selector")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        findViewById<Button>(R.id.button_register).setOnClickListener {
            uploadImageToFirebaseStorage()
            
            when {
                TextUtils.isEmpty(findViewById<EditText>(R.id.editText_username).text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity, "Please enter Username.", Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(findViewById<EditText>(R.id.editText_email).text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity, "Please enter Username.", Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(findViewById<EditText>(R.id.editText_password).text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity, "Please enter Username.", Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {

                    val username: String = findViewById<EditText>(R.id.editText_username).text.toString().trim{ it <= ' '}
                    val email: String = findViewById<EditText>(R.id.editText_email).text.toString().trim{ it <= ' '}
                    val password: String = findViewById<EditText>(R.id.editText_password).text.toString().trim{ it <= ' '}

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(
                                    this@RegisterActivity,
                                    "You were registered successfully.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent (this@RegisterActivity, LoginActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("email_id",email)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == RESULT_OK && data != null) {
            Log.d("RegisterActivity", "Photo was selected")

            selectedPhotoUri = data.data

            findViewById<ImageView>(R.id.select_photo_image).setImageURI(selectedPhotoUri)
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