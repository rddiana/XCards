package com.example.xcards.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xcards.R
import com.example.xcards.data.dataClasses.CardData
import com.google.android.material.card.MaterialCardView

class AdapterForRecyclerView(
    var context: Context?,
    data: ArrayList<CardData>,
    val onCardPressed: (CardData) -> Unit,
    val onPlusPressed: () -> Unit,
    val onStartPressed: () -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var cardsArray: ArrayList<CardData>

    init {
        cardsArray = data
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
        if (position - 1 >= 0) {
            (holder as ViewHolder).cardName.text = cardsArray[position - 1].nameModule
            holder.cardsCount.text = cardsArray[position - 1].cardsCount.toString()

            val card = holder.itemView as MaterialCardView
            card.setCardBackgroundColor((cardsArray[position - 1].color).toLong(radix = 16).toInt())

            holder.itemView.setOnClickListener {
                onCardPressed(cardsArray[position - 1])
            }
        } else {
            val holderAsButtonsHolder = holder as? ButtonsViewHolder

            holderAsButtonsHolder?.plusButton?.setOnClickListener {
                onPlusPressed()
            }

            holderAsButtonsHolder?.startButton?.setOnClickListener {
                onStartPressed()
            }
        }
    }

    override fun getItemCount(): Int {
        // Returns number of items currently available in Adapter
        return cardsArray.size + 1
    }

    inner class ButtonsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var plusButton: View
        var startButton: View

        init {
            plusButton = view.findViewById(R.id.cardViewWithBtNewCards)
            startButton = view.findViewById(R.id.cardViewWithBtStart)
        }
    }

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