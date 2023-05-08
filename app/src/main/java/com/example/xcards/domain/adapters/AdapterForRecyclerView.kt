package com.example.xcards.domain.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xcards.R
import com.example.xcards.data.CardData

class AdapterForRecyclerView(var context: Context?, images: ArrayList<*>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var cardsArray: ArrayList<CardData>

    init {
        cardsArray = images as ArrayList<CardData>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.buttons, parent, false)
            return ButtonsViewHolder(view)
        } else {
            // Inflating the Layout(Instantiates list_item.xml layout file into View object)
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)

            // Passing view to ViewHolder
            return ViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1
    }

    // Binding data to the into specified position
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position - 1 > 0) {
            val (nameModule, cardsCount) = cardsArray[position - 1]
            (holder as ViewHolder).cardName.text = nameModule
            holder.cardsCount.text = cardsCount.toString()
        }
    }

    override fun getItemCount(): Int {
        // Returns number of items currently available in Adapter
        return cardsArray.size + 1
    }

    inner class ButtonsViewHolder(view: View?) : RecyclerView.ViewHolder(
        view!!
    )

    // Initializing the Views
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cardName: TextView
        var cardsCount: TextView

        init {
            cardName = view.findViewById(R.id.textViewName)
            cardsCount = view.findViewById(R.id.textViewCount)
        }
    }
}