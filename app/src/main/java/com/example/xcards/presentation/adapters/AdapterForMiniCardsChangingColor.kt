package com.example.xcards.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xcards.R
import com.example.xcards.data.dataClasses.CardData
import com.example.xcards.data.dataClasses.CardDataLite
import com.example.xcards.data.sharedPref.SharedPreference
import com.google.android.material.card.MaterialCardView

class AdapterForMiniCardsChangingColor(
    var context: Context?,
    var applicationContext: Context,
    var data: ArrayList<CardData>,
    var cardDataLites: ArrayList<CardDataLite>,
    var resources: Int,
    private var normalColor: String,
    private var darkerColor: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var sharedPreference: SharedPreference

    private var selectedCards = List(cardDataLites.size) { false } as ArrayList
    var chosenTest: String = ""

    init {
        selectedCards[0] = true
        chosenTest = data[0].nameModule
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        sharedPreference = SharedPreference(applicationContext)
//
//        if (sharedPreference.getValueString("chosenTestForHR") != null) {
//            if (sharedPreference.getValueString("chosenTestForHR")!!.isNotEmpty()) {
//                chosenTest = sharedPreference.getValueString("chosenTestForHR")!!
////            selectedCards[cardDataLites.indexOf(CardDataLite(chosenTest, normalColor))] = true
//            }
//        }

        val view = LayoutInflater.from(parent.context).inflate(resources, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cardDataLites.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        (holder as ViewHolder).miniCardName.text = cardDataLites[position].nameModule

        val card = holder.itemView as MaterialCardView
        card.setCardBackgroundColor((cardDataLites[position].color).toLong(radix = 16).toInt())

//        var selectedIndex = selectedCards.indexOf(true)

//        if (selectedIndex != -1) {
//            cardDataLites[selectedIndex].color = normalColor
//            selectedCards[selectedIndex] = false
//            chosenTest = ""
////            this.notifyItemChanged(selectedIndex)
//        }

        card.setOnClickListener {
            var selectedIndex = selectedCards.indexOf(true)

            if (selectedIndex != -1) {
                cardDataLites[selectedIndex].color = normalColor
                selectedCards[selectedIndex] = false
                chosenTest = ""
                this.notifyItemChanged(selectedIndex)
            }

            selectedCards[position] = true
            cardDataLites[position].color = darkerColor
            chosenTest = data[position].nameModule

            sharedPreference.save("chosenTestForHR", chosenTest)

            this.notifyItemChanged(position)
        }

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var miniCardName: TextView

        init {
            miniCardName = view.findViewById(R.id.miniCardName)
        }
    }
}