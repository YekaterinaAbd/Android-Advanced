package com.example.jokes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jokes.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFragment()
    }

    fun setActionBarTitle(title: String){
        supportActionBar?.title = title
    }

    private fun setFragment(){
        val jokesFragment = JokesFragment()
        supportFragmentManager.beginTransaction().add(R.id.main, jokesFragment).commit()
    }
}
