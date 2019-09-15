package com.tonyyang.github.users.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.tonyyang.github.users.api.GithubService
import com.tonyyang.github.users.model.User


class UserDataSourceFactory(
        private val githubService: GithubService,
        private val query: String
) : DataSource.Factory<Int, User>() {

    val usersLiveData by lazy {
        MutableLiveData<UserDataSource>()
    }

    override fun create(): DataSource<Int, User> {
        return UserDataSource(githubService, query).also {
            usersLiveData.postValue(it)
        }
    }
}
