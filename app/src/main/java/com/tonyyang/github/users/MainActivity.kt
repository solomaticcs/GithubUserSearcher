package com.tonyyang.github.users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tonyyang.github.users.MainFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(false)
        }

        setupViewFragment()
    }

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.contentFrame)
                ?: replaceFragmentInActivity(MainFragment.newInstance(), R.id.contentFrame)
    }
}
