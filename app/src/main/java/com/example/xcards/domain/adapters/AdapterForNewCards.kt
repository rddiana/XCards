package com.example.xcards.domain.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.xcards.R
import com.example.xcards.data.NewCardData

class AdapterForNewCards(
    var context: Context?,
    var displayingCardsArray: ArrayList<NewCardData>,
    var onCardDeletion: ((NewCardData) -> Unit)? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.plus_round_button, parent, false)
            return ButtonViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_card_for_creating_card, parent, false)
            return ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == displayingCardsArray.size || displayingCardsArray.size == 0) {
            val holderAsButtonsHolder = holder as? AdapterForRecyclerView.ButtonsViewHolder

            holderAsButtonsHolder?.plusButton?.setOnClickListener {
                displayingCardsArray.add(NewCardData("test", "test"))
            }

        } else {
            val displayingCard = displayingCardsArray[position]

            (holder as ViewHolder).answer.setText(displayingCard.answer)
            holder.question.setText(displayingCard.question)
            holder.positionNumberText.text = (position + 1).toString()

            holder.deletionCard.setOnClickListener {
                onCardDeletion?.invoke(displayingCard)
            }

            holder.answer.doOnTextChanged { text, _, _, _ ->
                displayingCardsArray[position] =
                    displayingCardsArray[position].copy(answer = text as String)
            }

            holder.question.doOnTextChanged { text, _, _, _ ->
                displayingCardsArray[position] =
                    displayingCardsArray[position].copy(question = text as String)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == displayingCardsArray.size || displayingCardsArray.size == 0) 0 else 1
        /*
            0 - plus round button
            1 - card for creating card
         */
    }

    override fun getItemCount(): Int {
        return displayingCardsArray.size
    }

    inner class ButtonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var plusButton: View

        init {
            plusButton = view.findViewById(R.id.plusRoundButton)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val question: EditText
        val answer: EditText
        val positionNumberText: TextView
        val deletionCard: CardView

        init {
            question = view.findViewById(R.id.editTextForQuestion)
            answer = view.findViewById(R.id.editTextForAnswer)
            positionNumberText = view.findViewById(R.id.positionText)
            deletionCard = view.findViewById(R.id.removeCard)
        }
    }
}