package com.example.xcards.presentation.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.xcards.databinding.ActivityAuthorizationBinding
import com.example.xcards.domain.repositories.AuthorizationRepository
import com.google.firebase.auth.FirebaseAuth

class AuthorizationActivity : AppCompatActivity(), AuthorizationRepository {
    private lateinit var binding: ActivityAuthorizationBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging in...")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()


        binding.materialButtonNext.setOnClickListener {
            validateData()
        }

        binding.buttonForBeginners.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
            finish()
        }
    }

    override fun validateData() {
        email = binding.emailAddressEditText.text.toString().trim()
        password = binding.editTextPassword.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailAddressEditText.error = "Invalid email format"
        } else if (TextUtils.isEmpty(password)) {
            binding.editTextPassword.error = "Please, enter password"
        } else {
            firebaseLogin()
        }
    }

    override fun firebaseLogin() {
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener {e ->
                progressDialog.dismiss()
                Toast.makeText(this, "Authorization failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}