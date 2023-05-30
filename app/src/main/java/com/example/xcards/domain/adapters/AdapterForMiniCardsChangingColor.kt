package com.example.xcards.domain.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xcards.R
import com.example.xcards.data.CardData
import com.example.xcards.data.CardDataLite
import com.google.android.material.card.MaterialCardView

class AdapterForMiniCardsChangingColor(
    var context: Context?,
    var data: ArrayList<CardData>,
    var cardDataLites: ArrayList<CardDataLite>,
    var resources: Int,
    private var normalColor: String,
    private var darkerColor: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var selectedCollection: CardData? = null

    private var selectedCards = List(cardDataLites.size) { false } as ArrayList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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

        card.setOnClickListener {
            val selectedIndex = selectedCards.indexOf(true)

            if (selectedIndex != -1) {
                cardDataLites[selectedIndex].color = normalColor
                selectedCards[selectedIndex] = false
                selectedCollection = null
                this.notifyItemChanged(selectedIndex)
            }

            selectedCards[position] = true
            cardDataLites[position].color = darkerColor
            selectedCollection = data[position]

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