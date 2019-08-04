package com.tonyyang.github.users.repository

import com.tonyyang.github.users.MainApp
import com.tonyyang.github.users.R


class NetworkState(val status: Status, val message: Message) {

    enum class Status {
        LOADING,
        LOADED,
        FAILED
    }

    class Message(var msg: String)

    companion object {
        private val context by lazy {
            MainApp.applicationContext()
        }
        val LOADING = NetworkState(Status.LOADING,
                Message(context.getString(R.string.network_state_loading))
        )
        val LOADED = NetworkState(Status.LOADED,
                Message(context.getString(R.string.network_state_loaded))
        )
        val ERROR = NetworkState(Status.FAILED,
                Message(context.getString(R.string.network_state_error))
        )
    }
}
