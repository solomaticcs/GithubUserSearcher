package com.tonyyang.github.users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tonyyang.github.users.ui.GithubUserFragment

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
                ?: replaceFragmentInActivity(GithubUserFragment.newInstance(), R.id.contentFrame)
    }
}
