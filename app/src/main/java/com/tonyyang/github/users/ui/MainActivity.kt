package com.tonyyang.github.users.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tonyyang.github.users.R
import com.tonyyang.github.users.replaceFragmentInActivity
import com.tonyyang.github.users.setupActionBar

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
                ?: replaceFragmentInActivity(GithubUserFragment.newInstance(),
                    R.id.contentFrame
                )
    }
}
