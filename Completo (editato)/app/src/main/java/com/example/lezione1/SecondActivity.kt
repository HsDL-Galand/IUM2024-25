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
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SecondActivity : AppCompatActivity() {

    object ScoreData{
        var score = 0
        lateinit var points : TextView
    }
    var userVote = 0
    var voted = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        ScoreData.points = findViewById(R.id.tvPoints)

        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Attività"
                1 -> "Abitudini"
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }.attach()
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
        var alertText = ""

        if (voted){
            seekbar.progress = userVote
            vote.text = "${userVote-5}"
            alertText = "Oggi hai già votato. Puoi modificare il tuo voto di soddisfazione."
        }
        else{
            alertText = "Quanto sei soddisfatto oggi?"
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

        val dialog = AlertDialog.Builder(this)
            .setTitle(alertText)
            .setView(seekbarDialog)
            .setPositiveButton("OK", {
                _, _ ->
                userVote = seekbar.progress
                voted = true
            })
            .setNegativeButton("Annulla", null)
            .create()

        dialog.show()
    }


}
