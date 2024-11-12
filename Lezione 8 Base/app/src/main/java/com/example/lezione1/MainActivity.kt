package com.example.lezione1

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
        // show toast - RegisterActivity
        val fromRegisterActivity = intent.getBooleanExtra("fromRegisterActivity", false)
        if (fromRegisterActivity){
            Toast.makeText(this, getString(R.string.success_register), Toast.LENGTH_SHORT).show()
        }

        RegisterActivity.GlobalData.user_list.add(User("admin","admin","1234", "12/12/1999"))
        val message = findViewById<TextView>(R.id.tvMessage)
        val button = findViewById<Button>(R.id.btnLogin)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        button.alpha = 0.3f
        button.isEnabled = false

        button.setOnClickListener{
            login()
        }


        val register = findViewById<TextView>(R.id.tvRegister)
        register.setOnClickListener{
            register()
        }

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(etUsername.text.isNotBlank() && etPassword.text.isNotBlank()){
                    button.alpha = 1f
                    button.isEnabled = true
                }else{
                    button.alpha = 0.3f
                    button.isEnabled = false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        }
        etUsername.addTextChangedListener(textWatcher)
        etPassword.addTextChangedListener(textWatcher)

        RegisterActivity.GlobalData.user_list.add(User("prova","prova","prova","prova"))
    }

    fun login(){
        val usernameInput = findViewById<EditText>(R.id.etUsername)
        val passwordInput = findViewById<EditText>(R.id.etPassword)

        val intent = Intent(this, SecondActivity::class.java)
        val username = usernameInput.text.toString()
        val password = passwordInput.text.toString()

        val userPresent = RegisterActivity.GlobalData.user_list.any {
            it.name == username && it.password == password}

        if(userPresent){
            startActivity(intent)
        }
        else{
            val errorLoginText = findViewById<TextView>(R.id.errorLoginText)
            errorLoginText.visibility = TextView.VISIBLE
            usernameInput.backgroundTintList = ContextCompat.getColorStateList(this, R.color.red)
            passwordInput.backgroundTintList = ContextCompat.getColorStateList(this, R.color.red)
            usernameInput.text.clear()
            passwordInput.text.clear()
        }

    }

    fun register(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

}