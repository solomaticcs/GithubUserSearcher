package com.tonyyang.github.users.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.paging.PagedList
import com.tonyyang.github.users.model.User
import com.tonyyang.github.users.repository.NetworkState
import com.tonyyang.github.users.repository.UserPagedListRepository


class UserViewModel(
    private val repository: UserPagedListRepository
) : ViewModel() {

    private val userLiveData = MutableLiveData<String>()
    private val repoResult = userLiveData.map {
        repository.postsOfGithubUsers(it)
    }

    var userPagedList: LiveData<PagedList<User>> = repoResult.switchMap { it.pagedList }
    var refreshState: LiveData<NetworkState> = repoResult.switchMap { it.refreshState }

    fun listIsEmpty(): Boolean {
        return userPagedList.value?.isEmpty() ?: true
    }

    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }

    fun showGithubUser(query: String): Boolean {
        if (userLiveData.value == query) {
            return false
        }
        userLiveData.value = query
        return true
    }
}
