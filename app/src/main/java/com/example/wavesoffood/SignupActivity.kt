package com.example.wavesoffood

import android.accounts.Account
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wavesoffood.databinding.ActivitySignUpBinding
import com.example.wavesoffood.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignupActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var username: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        // initialize Firebase Auth
        auth = Firebase.auth
        // initialize firebase database
        database = Firebase.database.reference
        // initialize firebase database for google
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)


        binding.createAccountButton.setOnClickListener {
            username = binding.userName.text.toString()
            email = binding.emailAddress.text.toString().trim()
            password = binding.password.text.toString().trim()


            if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all the Details ", Toast.LENGTH_SHORT).show()

            } else {
                createAccount(email, password)

            }
            var alreadyHaveButton =
                findViewById<TextView>(R.id.alreadyhavebutton) // its not button its text you ... remember its name is just written as button
            alreadyHaveButton.setOnClickListener {
                intent = Intent(
                    this,
                    LoginActivity::class.java
                )  // no need to define "intent" with val/var because its already (inbuilt variable) or (already defined keyword) in android studio[ Saumya Singh told in one of his video].
                startActivity(intent)

            }
            binding.googleButton.setOnClickListener {
                val signInIntent = googleSignInClient.signInIntent
                launcher.launch(signInIntent)
            }

        }

    }

    // launcher for googlr sign-in
    private var launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account: GoogleSignInAccount? = task.result
                    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                    auth.signInWithCredential(credential).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Sign-In Successful🙂", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Sign In Failed 😒 ", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Sign In Failed 😕 ", Toast.LENGTH_SHORT).show()
            }
        }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                saveUserData()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Account Creation failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createAccount: Failure", task.exception)


            }

        }
    }


    private fun saveUserData() {
        // retrive data from input filled
        username = binding.userName.text.toString()
        email = binding.emailAddress.text.toString().trim()
        password = binding.password.text.toString().trim()


        val user = UserModel(username, email, password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        // save data to firebase database
        database.child("user").child(userId).setValue(user)
    }
}



