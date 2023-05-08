package com.example.xcards.presentation.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.xcards.R
import com.example.xcards.data.User
import com.example.xcards.databinding.ActivityRegistrationBinding
import com.example.xcards.domain.repositories.RegistrationRepository
import com.example.xcards.domain.repositories.SharedPreferencesRepository
import com.example.xcards.domain.useCase.FirebaseUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity(), RegistrationRepository {
    private lateinit var binding: ActivityRegistrationBinding

    private lateinit var sharedPreferencesRepository: SharedPreferencesRepository


    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var progressDialog: ProgressDialog

    private var fullName = ""
    private var userEmail = ""
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
        database = FirebaseDatabase.getInstance().getReference("Users")

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
        userEmail = binding.editTextEmailAddress.text.toString().trim()
        password = binding.editTextNewPassword.text.toString().trim()
        confirmedPassword = binding.editTextConfirmedPassword.text.toString().trim()

        if (TextUtils.isEmpty(fullName)) {
            binding.editTextPersonName.error = "Please, enter your name"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
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

        val user = User(fullName, userEmail)
//        database.child(userEmail).setValue(user)

        firebaseAuth.createUserWithEmailAndPassword(userEmail, password)
            .addOnSuccessListener {
                progressDialog.dismiss()

                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email

//                sharedPreferencesRepository = SharedPreferencesRepositoryImpl(this)
//                sharedPreferencesRepository.saveString(
//                    Constants.NAME_KEY_PREF,
//                    binding.editTextPersonName.text.toString()
//                )
//                sharedPreferencesRepository.saveString(
//                    Constants.EMAIL_KEY_PREF,
//                    binding.editTextEmailAddress.text.toString()
//                )

//                val personalUserData = hashMapOf<String, Any>(
//                    "name" to fullName,
//                    "email" to userEmail
//                )
//
//                FirebaseUtils().fireStoreDatabase.collection("users")
//                    .document(userEmail)
//                    .collection("data")
//                    .add(personalUserData)
//                    .addOnFailureListener {
//                        progressDialog.dismiss()
//                        Toast.makeText(this, "Sorry, registration failed", Toast.LENGTH_SHORT)
//                            .show()
//                    }

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, "Registration failed due to ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}