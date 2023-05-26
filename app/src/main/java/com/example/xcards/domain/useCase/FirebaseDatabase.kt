package com.example.xcards.domain.useCase

import android.content.Context
import android.widget.Toast
import com.example.xcards.R
import com.example.xcards.data.CardContentData
import com.example.xcards.data.CardData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class FirebaseDatabase(val applicationContext: Context) {
    private var database: FirebaseDatabase = Firebase.database("https://xcards-26b91-default-rtdb.asia-southeast1.firebasedatabase.app")
    val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()

    fun savePersonalData(key: String, data: String) {
        database.getReference("${uid}/personalData").child(key).setValue(data)
    }

    fun getPersonalData(key: String): String {
        return database.getReference("${uid}/personalData").child(key).get().toString()
    }

    fun getCardsCollection(nameCollection: String): ArrayList<CardContentData> {
        return database.getReference("${uid}/cardCollections/${nameCollection}")
            .child("cards").get() as ArrayList<CardContentData>
    }

    data class CardInfo(
        val cards: Array<CardContentData> = arrayOf(),
        val info: CardData = CardData("", 0, "")
    )

    fun getAllCards() {
        database.reference.child("${uid}/cardCollections")
            .get().addOnSuccessListener {
                val receivedValues = it.getValue<HashMap<String, CardInfo>>()

                receivedValues?.forEach { entry ->
                    print(entry.key)
                    print(entry.value)

                    receivedValues[entry.key] = entry.value
                }
            }
    }

    fun saveNewCollectionInfo(nameCollection: String, color: Int, cardsCount: Int) {
        database.getReference("${uid}/cardCollections/${nameCollection}/info")
            .child("color").setValue(color)

        database.getReference("${uid}/cardCollections/${nameCollection}/info")
            .child("cardsCount").setValue(cardsCount)
    }

    fun saveNewCardsData(context: Context, nameCollection: String, data: ArrayList<CardContentData>) {
        database.getReference("${uid}/cardCollections/${nameCollection}")
            .child("cards").setValue(data)
            .addOnFailureListener {
                Toast.makeText(context, R.string.on_collection_creation_failed, Toast.LENGTH_SHORT).show()
            }
            .addOnSuccessListener {
                Toast.makeText(context, R.string.on_successful_creation_collection, Toast.LENGTH_SHORT).show()
            }
    }

    fun updateCollectionInfo(nameCollection: String, key: String, newData: Int): Boolean {
        val databaseRef = database.getReference("${uid}/cardCollections/${nameCollection}/info").child(key)
        val updates = mapOf<String, Int>(Pair(key, newData))
        var listener = false

        databaseRef.updateChildren(updates)
            .addOnSuccessListener {
                listener = true
            }

        return listener
    }

    fun updateCollectionName(nameCollection: String, newNameCollection: String) {
        val data = database.getReference("${uid}/cardCollections/${nameCollection}").get()

        database.getReference("${uid}/cardCollections/${nameCollection}").setValue(null)
        database.getReference("${uid}/cardCollections/${newNameCollection}").setValue(data)
    }

    fun updateCardsData(context: Context, nameCollection: String, data: ArrayList<CardContentData>) {
        val databaseRef = database.getReference("${uid}/cardCollections/${nameCollection}").child("cards")
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
                Toast.makeText(context, R.string.updates_saved_successfully, Toast.LENGTH_SHORT).show()
            }
    }
}