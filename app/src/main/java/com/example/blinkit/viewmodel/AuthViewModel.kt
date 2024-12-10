package com.example.blinkit.viewmodel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blinkit.Firebase
import com.example.blinkit.model.User
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.TimeUnit

class AuthViewModel : ViewModel() {
    private var _verificationId : String? = null
    private val _otpSent =  MutableLiveData(false)
    val otpSent : LiveData<Boolean> = _otpSent
    private val _isLoginSuccessful =  MutableStateFlow<Boolean?>(null)
    val isLoginSuccessful = _isLoginSuccessful

    fun sendOtp(phoneNumber : String, activity : Activity) {
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(e: FirebaseException) {

            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                _verificationId = verificationId
                _otpSent.value = true
            }
        }

        val options = PhoneAuthOptions.newBuilder(Firebase.getAuthInstance())
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun signInWithPhoneAuthCredential(otp: String, user: User) {
        val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(_verificationId!!, otp)

        Firebase.getAuthInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _isLoginSuccessful.value = true
                    Firebase.getDatabaseInstance()
                        .getReference("AllUsers")
                        .child("Users")
                        .child(user.uId!!)
                        .setValue(user)
                } else {
                    _isLoginSuccessful.value = false
                }
            }
    }
}