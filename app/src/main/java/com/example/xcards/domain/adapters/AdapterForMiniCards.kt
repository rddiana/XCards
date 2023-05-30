package com.example.xcards.domain.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xcards.R
import com.example.xcards.data.CardData
import com.google.android.material.card.MaterialCardView
import kotlin.math.roundToInt

class AdapterForMiniCards(
    var context: Context?,
    var data: ArrayList<CardData>,
    var resources: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(resources, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).miniCardName.text = data[position].nameModule

        val card = holder.itemView as MaterialCardView
        card.setCardBackgroundColor((data[position].color).toLong(radix = 16).toInt())
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var miniCardName: TextView

        init {
            miniCardName = view.findViewById(R.id.miniCardName)
        }
    }
}