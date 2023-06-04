package com.example.xcards.domain.useCases

import android.content.Context
import android.widget.Toast
import com.example.xcards.R
import com.example.xcards.data.Constants
import com.example.xcards.data.dataClasses.CardContentData
import com.example.xcards.data.dataClasses.CardData
import com.example.xcards.data.dataClasses.CardInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class FirebaseDatabaseUtils(val applicationContext: Context) {
    private var database: FirebaseDatabase =
        Firebase.database("https://xcards-26b91-default-rtdb.asia-southeast1.firebasedatabase.app")
    val uid: String
        get() = FirebaseAuth.getInstance().currentUser?.uid.toString()

    fun savePersonalData(key: String, data: String) {
        database.getReference("${uid}/${Constants.PERSONAL_DATA}").child(key).setValue(data)
    }

    fun getPersonalData(key: String): String {
        return database.getReference("${uid}/${Constants.PERSONAL_DATA}").child(key).get().toString()
    }

    fun getCardsCollection(
        nameCollection: String,
        onDataLoaded: (List<CardContentData>) -> Unit
    ) {
        database.getReference("${uid}/${Constants.CARD_COLLECTIONS}/${nameCollection}")
            .child("cards").get().addOnSuccessListener {
                it.getValue<List<CardContentData>>()?.let { loadedData ->
                    onDataLoaded(loadedData)
                }
            }
    }

    fun getAllCardsInfo(onDataLoaded: (List<CardData>) -> Unit) {
        database.getReference(uid).child(Constants.CARD_COLLECTIONS)
            .get().addOnSuccessListener {
                val receivedValues = it.getValue<HashMap<String, CardInfo>>()

                receivedValues?.map { value ->
                    CardData(value.key, value.value.info.cardsCount, value.value.info.color)
                }?.let { collectedData ->
                    onDataLoaded(collectedData)
                }
            }
    }

    fun saveNewCollectionInfo(cardData: CardData) {
        database.getReference("${uid}/${Constants.CARD_COLLECTIONS}/${cardData.nameModule}")
            .child("info").setValue(cardData)
    }

    fun saveNewCardsData(
        context: Context,
        nameCollection: String,
        data: ArrayList<CardContentData>
    ) {
        database.getReference("${uid}/${Constants.CARD_COLLECTIONS}/${nameCollection}")
            .child("cards").setValue(data)
            .addOnFailureListener {
                Toast.makeText(context, R.string.on_collection_creation_failed, Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    R.string.on_successful_creation_collection,
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    fun updateCollectionInfo(cardData: CardData) {
        val databaseRef = database.getReference("${uid}/${Constants.CARD_COLLECTIONS}/${cardData.nameModule}").child("info")
        databaseRef.removeValue()
        databaseRef.setValue(cardData)
            .addOnSuccessListener {
                Toast.makeText(applicationContext, R.string.updates_saved_successfully, Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, R.string.save_updates_failed, Toast.LENGTH_SHORT).show()
            }
    }

    fun updateCollectionName(nameCollection: String, newNameCollection: String) {
        val data = database.getReference("${uid}/${Constants.CARD_COLLECTIONS}/${nameCollection}").get()

        database.getReference("${uid}/${Constants.CARD_COLLECTIONS}/${nameCollection}").setValue(null)
        database.getReference("${uid}/${Constants.CARD_COLLECTIONS}/${newNameCollection}").setValue(data)
    }

    fun updateCardsData(
        context: Context,
        nameCollection: String,
        data: ArrayList<CardContentData>
    ) {
        val databaseRef =
            database.getReference("${uid}/${Constants.CARD_COLLECTIONS}/${nameCollection}").child("cards")
        val updates = mapOf<String, String>()

        for (i in data) {
            updates.plus(Pair("question", i.question))
            updates.plus(Pair("answer", i.answer))
        }

        databaseRef.updateChildren(updates)
            .addOnFailureListener {
                Toast.makeText(context, R.string.save_updates_failed, Toast.LENGTH_SHORT).show()
            }
            .addOnSuccessListener {
                Toast.makeText(context, R.string.updates_saved_successfully, Toast.LENGTH_SHORT)
                    .show()
            }
    }
}