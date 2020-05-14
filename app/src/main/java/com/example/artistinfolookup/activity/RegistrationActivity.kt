package com.example.artistinfolookup.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.artistinfolookup.Constants
import com.example.artistinfolookup.R
import com.example.artistinfolookup.networking.Artist
import com.example.artistinfolookup.networking.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    private val auth by lazy{FirebaseAuth.getInstance()}
    private val db by lazy{FirebaseFirestore.getInstance()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        (this as AppCompatActivity).supportActionBar?.title = "Sign up"

        signUp()
    }



    private fun signUp(){
        sing_up_btn.setOnClickListener {
            if (sign_up_email.text!!.isEmpty() || sign_up_username.text!!.isEmpty()|| sign_up_psw.text!!.isEmpty() || sign_up_psw_repeat.text!!.isEmpty())
            {
                if (sign_up_email.text!!.isEmpty()) {
                    sign_up_email.error = Constants.ERROR_MSG_IS_EMPTY
                }
                if (sign_up_username.text!!.isEmpty()) {
                    sign_up_username.error = Constants.ERROR_MSG_IS_EMPTY
                }
                if (sign_up_psw.text!!.isEmpty()) {
                    sign_up_psw.error = Constants.ERROR_MSG_IS_EMPTY
                }
                if (sign_up_psw_repeat.text!!.isEmpty()) {
                    sign_up_psw_repeat.error = Constants.ERROR_MSG_IS_EMPTY
                }
            } else {
                if (sign_up_psw.text.toString() == sign_up_psw_repeat.text.toString()) {
                    auth.createUserWithEmailAndPassword(sign_up_email.text.toString(), sign_up_psw.text.toString())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user = User(
                                    task.result?.user?.uid!!, sign_up_email.text.toString(), sign_up_username.text.toString(), emptyList()
                                )
                                successReg()
                                saveUserData(task.result?.user?.uid!!,user)
                                return@addOnCompleteListener
                            }
                            Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()

                        }
                } else {
                    Toast.makeText(this, "Passwords do not match!!!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun successReg(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun saveUserData(
        id: String,
        user: User
    ){
        db.collection(Constants.USER_COLLECTION)
            .document(id)
            .set(user).addOnSuccessListener {
                Log.d("tagtag",Constants.SUCCESS_MSG)
            }
    }
}
