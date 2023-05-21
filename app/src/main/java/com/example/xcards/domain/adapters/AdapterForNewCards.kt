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
    var newCardsArray: ArrayList<NewCardData>,
    var onCardDeletion: ((NewCardData) -> Unit)? = null
): RecyclerView.Adapter<AdapterForNewCards.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterForNewCards.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_card_for_creating_card, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AdapterForNewCards.ViewHolder, position: Int) {
       val displayingCard = newCardsArray[position]
        holder.answer.setText(displayingCard.answer)
        holder.question.setText(displayingCard.question)
        holder.positionNumberText.text = (position + 1).toString()

        holder.deletionCard.setOnClickListener {
            onCardDeletion?.invoke(displayingCard)
        }

        holder.answer.doOnTextChanged { text, _, _, _ ->
            newCardsArray[position] = newCardsArray[position].copy(answer = text as String)
        }

        holder.question.doOnTextChanged { text, _, _, _ ->
            newCardsArray[position] = newCardsArray[position].copy(question = text as String)
        }
    }

    override fun getItemCount(): Int {
        return newCardsArray.size
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