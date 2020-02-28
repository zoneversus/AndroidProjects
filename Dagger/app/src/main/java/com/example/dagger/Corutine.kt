package com.example.dagger

import android.R.attr
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.google.android.gms.tasks.Task
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_corutine.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class Corutine : AppCompatActivity(), LifecycleObserver {
    private lateinit var tmdbViewModel: UserViewModel
    private lateinit var callbackManager :CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corutine)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        tmdbViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        tmdbViewModel.userLiveData.observe(this, Observer {
            texto.text=tmdbViewModel.userLiveData.value.toString()
        })
        tap.setOnClickListener {
            tmdbViewModel.initMovies()
            tmdbViewModel.fetchMovies()
        }
        kill.setOnClickListener {
            tmdbViewModel.cancelAllRequests()
        }
        next.setOnClickListener {
            val intent = Intent(applicationContext,CorutineStop::class.java)
            startActivity(intent)

        }
        FacebookSdk.fullyInitialize()
        AppEventsLogger.activateApp(application)
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) { // App code
                Toast.makeText(this@Corutine,"Facebook login succesful",Toast.LENGTH_SHORT).show()
                }

                override fun onCancel() { // App code
                }

                override fun onError(exception: FacebookException) { // App code
                }
            })
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        sign_in_button.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, 1)
        }


    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
          val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
        handleSignInResult(result)

        }
    }

    override fun onRestart() {
        super.onRestart()
        tmdbViewModel.initMovies()
        tmdbViewModel.fetchMovies()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun Stop(){
        tmdbViewModel.cancelAllRequests()
    }
    fun printHashKey(pContext: Context) {
        try {
            val info: PackageInfo = pContext.getPackageManager()
                .getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.i("key", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("key", "printHashKey()", e)
        } catch (e: Exception) {
            Log.e("key", "printHashKey()", e)
        }
    }


private fun handleSignInResult(result: GoogleSignInResult) {
    if (result.isSuccess) {
        // Signed in successfully, show authenticated UI.
        val acct = result.signInAccount
        Toast.makeText(this,"Google Login succesful: "+acct?.email ,Toast.LENGTH_SHORT).show()
    } else {

    }
}
}
