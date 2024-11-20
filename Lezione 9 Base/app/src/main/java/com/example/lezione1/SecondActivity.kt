package com.example.lezione1

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SecondActivity : AppCompatActivity() {
    var score = 0
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

        plusButton.setOnClickListener{
            add(points)
        }
        minusButton.setOnClickListener{
            minus(points)
        }



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
            showClearCompletedDialog(taskList,adapter)
        }

        buttonCompleteAll.setOnClickListener{
            if(taskList.all{it.isCompleted}){
                taskList.forEach{it.isCompleted = false}
            }else {
                taskList.forEach { it.isCompleted = true }
            }
            adapter.notifyDataSetChanged()
        }
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
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

    fun logout() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val logoutItem = menu?.findItem(R.id.actionLogout)
        val spannableString = SpannableString(logoutItem?.title)
        spannableString.setSpan(ForegroundColorSpan(Color.RED), 0, spannableString.length, 0)
        logoutItem?.title = spannableString
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionVote -> {
                showSeekbarDialog()
            }
            R.id.actionLogout -> {
                logout()
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    fun showSeekbarDialog(){
        val seekbarDialog = layoutInflater.inflate(R.layout.dialog_seekbar, null)
        val seekbar = seekbarDialog.findViewById<SeekBar>(R.id.seekbar)
        val vote = seekbarDialog.findViewById<TextView>(R.id.tvVote)

        seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                vote.text = "${progress-5}"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        val dialog = AlertDialog.Builder(this)
            .setTitle("Quanto sei soddisfatto oggi da -5 a 5?")
            .setView(seekbarDialog)
            .setPositiveButton("OK", null)
            .setNegativeButton("Annulla", null)
            .create()

        dialog.show()
    }

    fun showClearCompletedDialog(taskList : MutableList<Task>, adapter: TaskAdapter){
        val dialog = AlertDialog.Builder(this)
            .setTitle("Sei sicuro di voler eliminare tutte le attività completate?")
            .setPositiveButton("OK", { _, _ ->
                taskList.removeAll { it.isCompleted }
                adapter.notifyDataSetChanged()
            })
            .setNegativeButton("Annulla", null)
            .create(
            ).show()
    }

}
