package com.tonyyang.github.users.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.tonyyang.github.users.addTo
import com.tonyyang.github.users.api.GithubService
import com.tonyyang.github.users.model.User
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserDataSource(
    private val apiService: GithubService,
    private val query: String,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, User>() {

    val networkState by lazy {
        MutableLiveData<NetworkState>()
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, User>) {
        networkState.postValue(NetworkState.LOADING)
        apiService.searchUsers(query, PER_PAGE, FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            callback.onResult(it.items, null, FIRST_PAGE + 1)
                            networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            networkState.postValue(NetworkState.ERROR)
                            Log.e(TAG, it.message)
                        }
                ).addTo(compositeDisposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        networkState.postValue(NetworkState.LOADING)
        apiService.searchUsers(query, PER_PAGE, params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            callback.onResult(it.items, params.key + 1)
                            networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            networkState.postValue(NetworkState.ERROR)
                            Log.e(TAG, it.message)
                        }
                ).addTo(compositeDisposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        // do nothing
    }

    companion object {
        private val TAG = UserDataSource::class.java.simpleName
        private const val FIRST_PAGE = 1
        const val PER_PAGE = 15
    }
}