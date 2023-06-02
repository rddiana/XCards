package com.example.xcards.domain.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xcards.R
import com.example.xcards.data.CardContentData

class AdapterForSummingUpTheResults(
    val context: Context,
    private val cardContentList: List<CardContentData>,
    private val isAnswersCorrectList: List<Boolean>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.summing_up_result, parent, false)
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cardContentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CardViewHolder).number.text = (position + 1).toString()
        holder.question.text = cardContentList[position].question

        if (isAnswersCorrectList[position]) {
            holder.image.setImageResource(R.drawable.icon_tick)
        } else {
            holder.image.setImageResource(R.drawable.icon_cross)
        }
    }

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var number: TextView
        var question: TextView
        var image: ImageView

        init {
            number = view.findViewById(R.id.number)
            question = view.findViewById(R.id.questionTextView)
            image = view.findViewById(R.id.image)
        }
    }
}