package com.example.lezione1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TaskFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskText = view.findViewById<EditText>(R.id.etTask)
        val buttonAddTask = view.findViewById<Button>(R.id.btnAddTask)
        val buttonClearTasks = view.findViewById<Button>(R.id.btnClearTasks)
        val buttonCompleteAll = view.findViewById<ImageButton>(R.id.ibTask)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvTasks)
        val taskCount = view.findViewById<TextView>(R.id.tvCount)

        val taskList = mutableListOf<Task>()
        val adapter = TaskAdapter(taskList,taskCount)


        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

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
    }


    fun showClearCompletedDialog(taskList : MutableList<Task>, adapter: TaskAdapter){
        val dialog = AlertDialog.Builder(requireActivity())
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