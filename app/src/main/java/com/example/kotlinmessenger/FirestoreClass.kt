package com.example.kotlinmessenger
import android.util.Log
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

    fun getCurrentUserId(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }
}