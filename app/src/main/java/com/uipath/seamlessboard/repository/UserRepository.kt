package com.uipath.seamlessboard.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

interface UserRepository {

    fun hasUser(): Boolean

    fun getUser(): FirebaseUser?

    fun logout()
}

class UserRepositoryImpl : UserRepository {

    override fun hasUser(): Boolean = FirebaseAuth.getInstance().currentUser != null

    override fun getUser(): FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

}