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
import com.example.xcards.data.CardContentData

class AdapterForNewCards(
    var context: Context?,
    var cardsArray: ArrayList<CardContentData>,
    var onCardDeletion: ((CardContentData) -> Unit)? = null
): RecyclerView.Adapter<AdapterForNewCards.ViewHolder>() {
    var cardContentArray = cardsArray

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterForNewCards.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_card_for_creating_card, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AdapterForNewCards.ViewHolder, position: Int) {
        val displayingCard = cardContentArray[position]
        holder.answer.setText(displayingCard.answer)
        holder.question.setText(displayingCard.question)
        holder.positionNumberText.text = (position + 1).toString()

        holder.answer.doOnTextChanged { _, _, _, _ ->
            val newText = holder.answer.text.toString()
            cardContentArray[position] = cardContentArray[position].copy(answer = newText)
        }

        holder.question.doOnTextChanged { _, _, _, _ ->
            val newText = holder.question.text.toString()
            cardContentArray[position] = cardContentArray[position].copy(question = newText)
        }

        holder.deletionCard.setOnClickListener {
            cardContentArray.removeAt(position)
            this.notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return cardContentArray.size
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