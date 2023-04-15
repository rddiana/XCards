package com.example.xcards.presentation.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.xcards.R
import com.example.xcards.databinding.ActivityRegistrationBinding
import com.example.xcards.domain.repositories.RegistrationRepository
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : AppCompatActivity(), RegistrationRepository {
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    private var fullName = ""
    private var email = ""
    private var password = ""
    private var confirmedPassword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating account in...")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.materialButtonNext.setOnClickListener {
            validateData()
        }

        binding.buttonForOwnersOfAccounts.setOnClickListener {
            startActivity(Intent(this, AuthorizationActivity::class.java))
            finish()
        }
    }

    override fun validateData() {
        fullName = binding.editTextPersonName.text.toString().trim()
        email = binding.editTextEmailAddress.text.toString().trim()
        password = binding.editTextNewPassword.text.toString().trim()
        confirmedPassword = binding.editTextConfirmedPassword.text.toString().trim()

        if (TextUtils.isEmpty(fullName)){
            binding.editTextPersonName.error = "Please, enter your name"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmailAddress.error = "Invalid email format"
        } else if (TextUtils.isEmpty(password)) {
            binding.editTextNewPassword.error = "Please, enter password"
        } else if (password.length < 6) {
            binding.editTextNewPassword.error = "Password must at least 6 character long"
        } else if (password != confirmedPassword) {
            binding.textViewForMessage2.text = getString(R.string.no_matching_passwords)
        } else {
            firebaseSignUp()
        }
    }

    override fun firebaseSignUp() {
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()

                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener{e ->
                progressDialog.dismiss()
                Toast.makeText(this, "Registration failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}