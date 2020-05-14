package com.example.simplechatapp.observer

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ForegroundBackgroundListener : LifecycleObserver {

    private val db by lazy { FirebaseFirestore.getInstance() }
    private val auth by lazy {FirebaseAuth.getInstance()}



    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun signOutOut(){
        auth.signOut()

    }
}