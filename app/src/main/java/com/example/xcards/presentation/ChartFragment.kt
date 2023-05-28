package com.example.xcards.presentation

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.xcards.R
import com.example.xcards.databinding.FragmentChartBinding
import com.example.xcards.domain.useCase.SharedPreference

class ChartFragment : Fragment() {
    private lateinit var binding: FragmentChartBinding
    private var canvas: Canvas = Canvas()

    private lateinit var sharedPreference: SharedPreference

    companion object {
        fun newInstance() = ChartFragment()
    }

    private lateinit var viewModel: ChartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChartBinding.inflate(layoutInflater)

        sharedPreference = SharedPreference(requireContext().applicationContext)

        binding.spendingHoursText.text = ((sharedPreference.getValueInt("time").toFloat() / 60f * 10).toInt().toFloat() / 10).toString()

//        val pieChart = binding.pieChart
//
//        val paint = Paint()
//        paint.color = Color.WHITE
//        paint.strokeWidth = 5f
//        paint.style = Paint.Style.FILL
//        paint.isAntiAlias = true
//
//        val rectF = RectF()
//        rectF.set(pieChart.left.toFloat(), pieChart.top.toFloat(),
//            pieChart.right.toFloat(), pieChart.bottom.toFloat())
//
//        canvas.drawArc(rectF, 0F, 90F, true, paint)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChartViewModel::class.java)
        // TODO: Use the ViewModel
    }

}