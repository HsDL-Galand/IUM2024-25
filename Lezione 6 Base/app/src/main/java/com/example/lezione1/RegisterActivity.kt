package com.example.lezione1

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

class RegisterActivity : AppCompatActivity() {
    object GlobalData{
        var user_list = mutableListOf<User>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val button = findViewById<Button>(R.id.btnRegister)
        val usernameEt = findViewById<EditText>(R.id.etUsername)
        val mailEt = findViewById<EditText>(R.id.etMail)
        val passwordEt = findViewById<EditText>(R.id.etPassword)
        val checkbox = findViewById<CheckBox>(R.id.checkBox)
        Log.i("Matteo",String.format("%08X", usernameEt.currentTextColor))

        val date = findViewById<EditText>(R.id.etDate)
        date.setOnClickListener {
            datePicker(date)
        }

        button.setOnClickListener{
            if (checkbox.isChecked) {
                register(
                    usernameEt.text.toString(),
                    mailEt.text.toString(),
                    passwordEt.text.toString(),
                    date.text.toString()
                )
            }
        }
    }

    fun datePicker(date: EditText){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    fun register(username: String, mail: String, password:String, date: String){
        var new_user = User(username,mail,password, date)
        GlobalData.user_list.add(new_user)
        Log.i("myTag",GlobalData.user_list.toString())
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}