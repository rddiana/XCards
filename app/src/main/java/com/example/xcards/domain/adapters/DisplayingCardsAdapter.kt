package com.example.xcards.domain.adapters

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.example.xcards.R
import com.example.xcards.data.CardContentData

class DisplayingCardsAdapter(
    val context: Context,
    val cardContentList: ArrayList<CardContentData>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return DisplayingCardHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cardContentData = cardContentList[position]

        (holder as DisplayingCardHolder).textQuestion.text = cardContentData.question
        holder.textAnswer.text = cardContentData.answer

        holder.viewAnswer.setOnClickListener {
            flipCard(context, holder.viewQuestion, it)
        }

        holder.viewQuestion.setOnClickListener {
            flipCard(context, holder.viewAnswer, it)
        }
    }

    override fun getItemCount(): Int {
        return cardContentList.size
    }

    inner class DisplayingCardHolder(view: View) : RecyclerView.ViewHolder(view) {
        var viewQuestion: ViewGroup
        var textQuestion: TextView
        var viewAnswer: ViewGroup
        var textAnswer: TextView

        init {
            viewQuestion = view.findViewById(R.id.cardWithQuestion)
            textQuestion = view.findViewById(R.id.cardQuestionText)
            viewAnswer = view.findViewById(R.id.cardWithAnswer)
            textAnswer = view.findViewById(R.id.cardAnswerText)
        }
    }

    fun removeSwipedItem() {
        cardContentList.removeAt(0)
        notifyDataSetChanged()
    }

    private fun flipCard(context: Context, visibleView: View, inVisibleView: View) {
        try {
            visibleView.visibility = View.VISIBLE

            val scale = context.resources.displayMetrics.density
            val cameraDist = 8000 * scale
            visibleView.cameraDistance = cameraDist
            inVisibleView.cameraDistance = cameraDist

            val flipOutAnimatorSet =
                AnimatorInflater.loadAnimator(
                    context,
                    R.animator.flip_out
                ) as AnimatorSet

            flipOutAnimatorSet.setTarget(inVisibleView)

            val flipInAnimationSet =
                AnimatorInflater.loadAnimator(
                    context,
                    R.animator.flip_in
                ) as AnimatorSet

            flipInAnimationSet.setTarget(visibleView)
            flipOutAnimatorSet.start()
            flipInAnimationSet.start()
            flipInAnimationSet.doOnEnd {
                inVisibleView.isGone = true
            }
        } catch (e: Exception) {
            Log.d("animator exception", e.message.toString())
        }
    }
}