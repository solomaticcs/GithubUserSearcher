package com.tonyyang.github.users.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.tonyyang.github.users.api.GithubService
import com.tonyyang.github.users.model.User
import io.reactivex.disposables.CompositeDisposable


class UserDataSourceFactory(
    private val apiService: GithubService,
    private val query: String,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, User>() {

    val usersLiveData by lazy {
        MutableLiveData<UserDataSource>()
    }

    override fun create(): DataSource<Int, User> {
        return UserDataSource(apiService, query, compositeDisposable).also {
            usersLiveData.postValue(it)
        }
    }
}
