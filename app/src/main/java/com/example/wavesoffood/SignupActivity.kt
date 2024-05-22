package com.example.wavesoffood

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wavesoffood.databinding.ActivitySignUpBinding

class SignupActivity : AppCompatActivity() {
    private val binding : ActivitySignUpBinding by lazy {
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

        var alreadyHaveButton = findViewById<TextView>(R.id.alreadyhavebutton) // its not button its text you ... remember its name is just written as button
        alreadyHaveButton.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java )  // no need to define "intent" with val/var because its already (inbuilt variable) or (already defined keyword) in android studio[ Saumya Singh told in one of his video].
            startActivity(intent)
        }


    }
}