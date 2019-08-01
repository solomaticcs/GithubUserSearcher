@file:Suppress("unused")

package com.tonyyang.github.users

import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun String?.parcel(): String = this ?: ""

fun Double?.parcel(): Double = this ?: 0.0

fun Float?.parcel(): Float = this ?: 0F

fun Long?.parcel(): Long = this ?: 0

fun Int?.parcel(): Int = this ?: 0

fun Boolean?.parcel(): Boolean = this ?: false

fun AppCompatActivity.setupActionBar(@IdRes toolbarId: Int, action: ActionBar.() -> Unit) {
    setSupportActionBar(findViewById(toolbarId))
    supportActionBar?.run {
        action()
    }
}

fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, frameId: Int) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
    }
}

private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}