package com.example.jokes.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.jokes.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFragment()
    }

    private fun setFragment(){
        val jokesFragment = JokesFragment()
        supportFragmentManager.beginTransaction().add(R.id.main, jokesFragment).commit()
    }
}
