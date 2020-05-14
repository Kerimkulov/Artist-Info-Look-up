package com.example.artistinfolookup.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.artistinfolookup.Constants
import com.example.artistinfolookup.R
import com.example.artistinfolookup.networking.Artist
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val db by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        (this as AppCompatActivity).supportActionBar?.title = "Login"
        goReg()
        signIn()
    }
    private fun signIn(){
        login_btn.setOnClickListener {
            if (login_email.text!!.isEmpty() || login_psw.text!!.isEmpty()) {
                if (login_email.text!!.isEmpty()) {
                    login_email.error = Constants.ERROR_MSG_IS_EMPTY
                }
                if (login_psw.text!!.isEmpty()) {
                    login_psw.error = Constants.ERROR_MSG_IS_EMPTY
                }
            } else {
                auth.signInWithEmailAndPassword(
                    login_email.text.toString(),
                    login_psw.text.toString()
                )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            successLogin()
                            return@addOnCompleteListener
                        }
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()

                    }
            }
        }
    }
    private fun successLogin(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
       finish()
    }

    private fun goReg(){
        reg_textView.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

}
