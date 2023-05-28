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
    var data: ArrayList<CardData>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var selectedCollection: CardData = data[0]
    var indexSelectedCollection = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mini_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).miniCardName.text = data[position].nameModule

        val card = holder.itemView as MaterialCardView
        card.setCardBackgroundColor((data[position].color).toLong(radix = 16).toInt())

        if (position != indexSelectedCollection) {
            val newColor = shadeColor(data[position].color, -2)
            data[position].color = newColor
        }

        card.setOnClickListener {
            indexSelectedCollection = position
            selectedCollection = data[position]

            val newColor = shadeColor(data[position].color, 2)
            data[position].color = newColor
            this.notifyDataSetChanged()
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var miniCardName: TextView

        init {
            miniCardName = view.findViewById(R.id.miniCardName)
        }
    }

    fun shadeColor(color: String, percent: Int): String {
        var R = Integer.parseInt(color.substring(0, 2), 16)
        var G = Integer.parseInt(color.substring(2, 4), 16)
        var B = Integer.parseInt(color.substring(4, 6), 16)
        R = Integer.parseInt((R * (100 + percent) / 100).toString())
        G = Integer.parseInt((G * (100 + percent) / 100).toString())
        B = Integer.parseInt((B * (100 + percent) / 100).toString())
        R = (if (R < 255) R else 255).toFloat().roundToInt()
        G = (if (G < 255) G else 255).toFloat().roundToInt()
        val RR = if (R.toString(16).length == 1) "0" + R.toString(16) else R.toString(16)
        val GG = if (G.toString(16).length == 1) "0" + G.toString(16) else G.toString(16)
        val BB = if (B.toString(16).length == 1) "0" + B.toString(16) else B.toString(16)
        return "$RR$GG$BB"
    }
}