package com.example.blinkit

import com.google.firebase.auth.FirebaseAuth

object Firebase {
    private var firebaseAuthInstance : FirebaseAuth? = null

    fun getAuthInstance() : FirebaseAuth {
        if(firebaseAuthInstance == null) {
            firebaseAuthInstance = FirebaseAuth.getInstance()
        }
        return firebaseAuthInstance!!
    }
}