package com.tonyyang.github.users.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.tonyyang.github.users.model.User
import com.tonyyang.github.users.repository.NetworkState
import com.tonyyang.github.users.repository.UserPagedListRepository
import io.reactivex.disposables.CompositeDisposable


class UserViewModel(
        private val userPagedListRepository: UserPagedListRepository
) : ViewModel() {

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    var userPagedList: LiveData<PagedList<User>>? = null

    var networkState: LiveData<NetworkState>? = null

    fun listIsEmpty(): Boolean {
        return userPagedList?.value?.isEmpty() ?: true
    }

    fun replaceSubscription(lifecycleOwner: LifecycleOwner, query: String) {
        userPagedList?.removeObservers(lifecycleOwner)
        userPagedList = userPagedListRepository.fetchLiveUserPagedList(query, compositeDisposable)
        networkState?.removeObservers(lifecycleOwner)
        networkState = userPagedListRepository.getNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
