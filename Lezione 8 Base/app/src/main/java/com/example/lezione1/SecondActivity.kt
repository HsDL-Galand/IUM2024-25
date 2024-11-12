package com.example.lezione1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SecondActivity : AppCompatActivity() {
    var score = 0
    var current_vote = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val plusButton = findViewById<Button>(R.id.btnPlus)
        val minusButton = findViewById<Button>(R.id.btnMinus)
        val points = findViewById<TextView>(R.id.tvPoints)
        val seekbar = findViewById<SeekBar>(R.id.seekbar)
        val vote = findViewById<TextView>(R.id.tvVote)
        val logoutButton = findViewById<Button>(R.id.btnLogout)

        plusButton.setOnClickListener{
            add(points)
        }
        minusButton.setOnClickListener{
            minus(points)
        }

        logoutButton.setOnClickListener{
            logout()
        }

        seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                vote.text = "${progress-5}"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        val taskText = findViewById<EditText>(R.id.etTask)
        val buttonAddTask = findViewById<Button>(R.id.btnAddTask)
        val buttonClearTasks = findViewById<Button>(R.id.btnClearTasks)
        val buttonCompleteAll = findViewById<ImageButton>(R.id.ibTask)
        val taskList = mutableListOf<Task>()
        val recyclerView = findViewById<RecyclerView>(R.id.rvTasks)
        val taskCount = findViewById<TextView>(R.id.tvCount)
        val adapter = TaskAdapter(taskList,taskCount)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        buttonAddTask.setOnClickListener {
            val task = taskText.text.toString()
            taskText.text.clear()
            taskList.add(Task(task))
            adapter.notifyItemInserted(taskList.size - 1)
        }

        buttonClearTasks.setOnClickListener {
            taskList.removeAll { it.isCompleted }
            adapter.notifyDataSetChanged()
        }

        buttonCompleteAll.setOnClickListener{
            if(taskList.all{it.isCompleted}){
                taskList.forEach{it.isCompleted = false}
            }else {
                taskList.forEach { it.isCompleted = true }
            }
            adapter.notifyDataSetChanged()
        }

    }

    fun add(pts : TextView){
        score++
        pts.text = "Punti Esperienza: $score"
    }

    fun minus(pts: TextView){
        if (score - 1 > 0) {
            score--
        }
        else{
            score = 0
        }
        pts.text = "Punti Esperienza: $score"
    }

    fun logout(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}