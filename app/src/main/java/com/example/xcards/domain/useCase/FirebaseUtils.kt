package com.example.xcards.domain.useCase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseUtils {
//    val fireStoreDatabase = FirebaseFirestore.getInstance()
      val fireStoreDatabase = Firebase.firestore
}