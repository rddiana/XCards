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
import com.example.xcards.domain.useCase.SharedPreference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class AuthorizationActivity : AppCompatActivity(), AuthorizationRepository {
    private lateinit var binding: ActivityAuthorizationBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreference: SharedPreference
    private lateinit var database: DatabaseReference

    private lateinit var progressDialog: ProgressDialog

    private var userEmail = ""
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
        sharedPreference = SharedPreference(applicationContext)

        binding.materialButtonNext.setOnClickListener {
            validateData()
        }

        binding.buttonForBeginners.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
            finish()
        }
    }

    override fun validateData() {
        userEmail = binding.emailAddressEditText.text.toString().trim()
        password = binding.editTextPassword.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            binding.emailAddressEditText.error = "Invalid email format"
        } else if (TextUtils.isEmpty(password)) {
            binding.editTextPassword.error = "Please, enter password"
        } else {
            initDataBase()
            firebaseLogin()
        }
    }

    override fun firebaseLogin() {
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(userEmail, password)
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

    private fun initDataBase() {
        firebaseAuth.currentUser?.let {
            val fullName = database
                .child("usersPersonalData")
                .child(it.uid).child("name")
                .get().toString()

            val userEmail = database
                .child("usersPersonalData")
                .child(it.uid)
                .child("email")
                .get().toString()

            sharedPreference.save("uid", it.uid)
            sharedPreference.save("email", userEmail)
            sharedPreference.save("userName", fullName)
        }
    }
}