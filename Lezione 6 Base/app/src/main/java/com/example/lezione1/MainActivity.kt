package com.example.lezione1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        RegisterActivity.GlobalData.user_list.add(User("admin","admin","1234", "12/12/1999"))
        val message = findViewById<TextView>(R.id.tvMessage)
        val button = findViewById<Button>(R.id.btnLogin)
        button.setOnClickListener{
            login()
        }
        val register = findViewById<TextView>(R.id.tvRegister)
        register.setOnClickListener{
            register()
        }

    }

    fun login(){
        val intent = Intent(this, SecondActivity::class.java)
        val username = findViewById<EditText>(R.id.etUsername).text.toString()
        val password = findViewById<EditText>(R.id.etPassword).text.toString()
        val userPresent = RegisterActivity.GlobalData.user_list.any {
            it.name == username && it.password == password}
        if(userPresent){
            startActivity(intent)
        }

    }

    fun register(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

}