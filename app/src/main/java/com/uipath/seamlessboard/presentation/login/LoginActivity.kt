package com.uipath.seamlessboard.presentation.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.uipath.seamlessboard.MainActivity
import com.uipath.seamlessboard.R
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModel()

    companion object {
        private const val SIGN_IN_REQUEST_CODE = 4023
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel.setContentViewLiveData().observe(this, Observer {
            setContentView(R.layout.activity_login)

            loginButton.setOnClickListener {
                loginViewModel.onLoginClicked()
            }
        })

        loginViewModel.navigationLiveData().observe(this, Observer {
            when (it) {
                is LoginNavigationCommands.StartLogin -> startLogin()
                is LoginNavigationCommands.StartBoardActivity -> startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    ).apply { flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK) })
            }
        })

        loginViewModel.onCreate()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                loginViewModel.onLoggedIn()
            } else {
                response?.error?.localizedMessage?.let {
                    Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun startLogin() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            SIGN_IN_REQUEST_CODE
        )
    }
}