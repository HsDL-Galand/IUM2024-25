package com.example.lezione1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class HabitFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val plusButton = view.findViewById<Button>(R.id.btnPlus)
        val minusButton = view.findViewById<Button>(R.id.btnMinus)

        plusButton.setOnClickListener{
            add(SecondActivity.ScoreData.points)
        }
        minusButton.setOnClickListener{
            minus(SecondActivity.ScoreData.points)
        }
    }


    fun add(pts : TextView){
        SecondActivity.ScoreData.score++
        pts.text = "Punti Esperienza: ${SecondActivity.ScoreData.score}"
    }

    fun minus(pts: TextView){
        if (SecondActivity.ScoreData.score - 1 > 0) {
            SecondActivity.ScoreData.score--
        }
        else{
            SecondActivity.ScoreData.score = 0
        }
        pts.text = "Punti Esperienza: ${SecondActivity.ScoreData.score}"
    }
}