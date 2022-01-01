package com.example.kotlinmessenger.firebase
import android.util.Log
import com.example.kotlinmessenger.utils.Constant
import com.example.kotlinmessenger.utils.User
import com.example.kotlinmessenger.activities.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {

    private val fireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, user: User) {
        fireStore.collection(Constant.USERS)
            .document(getCurrentUserId()).set(user, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()

            } .addOnFailureListener{

            Log.d(activity.javaClass.simpleName, "Error registering new user")
            }
    }

    private fun getCurrentUserId(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }
}