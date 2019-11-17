package com.tonyyang.github.users.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.tonyyang.github.users.model.User
import com.tonyyang.github.users.repository.NetworkState
import com.tonyyang.github.users.repository.UserPagedListRepository


class UserViewModel(
        private val repository: UserPagedListRepository
) : ViewModel() {

    private val userLiveData = MutableLiveData<String>()
    private val repoResult = map(userLiveData) {
        repository.postsOfGithubUsers(it)
    }

    var userPagedList: LiveData<PagedList<User>> = switchMap(repoResult) { it.pagedList }
    var refreshState: LiveData<NetworkState> = switchMap(repoResult) { it.refreshState }

    fun listIsEmpty(): Boolean {
        return userPagedList?.value?.isEmpty() ?: true
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
