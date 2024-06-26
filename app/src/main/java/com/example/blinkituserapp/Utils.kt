package com.example.blinkituserapp

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.example.blinkituserapp.databinding.ProgressDialogBinding
import com.google.firebase.auth.FirebaseAuth

object Utils {

    private var dialog : AlertDialog? = null
    private var firebaseAuthInstance : FirebaseAuth? = null

    fun getFirebaseAuthInstance() : FirebaseAuth {
        if(firebaseAuthInstance == null) {
            firebaseAuthInstance = FirebaseAuth.getInstance()
        }
        return firebaseAuthInstance!!
    }

    fun showDialog(context: Context, message : String) {
        val progress = ProgressDialogBinding.inflate(LayoutInflater.from(context))
        progress.tvLoading.text = message
        dialog = AlertDialog.Builder(context).setView(progress.root).setCancelable(false).create()
        dialog?.show()
    }

    fun hideDialog() {
        dialog?.dismiss()
    }

    fun showToast(context: Context, message : String) {
        Toast.makeText(context, "Hello World", Toast.LENGTH_SHORT).show()
    }

    fun getCurrentUserId() : String {
        return getFirebaseAuthInstance().currentUser!!.uid
    }

}