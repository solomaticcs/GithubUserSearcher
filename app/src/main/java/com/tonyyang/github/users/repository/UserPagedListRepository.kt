package com.tonyyang.github.users.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.tonyyang.github.users.api.GithubService
import com.tonyyang.github.users.model.User
import com.tonyyang.github.users.repository.UserDataSource.Companion.PER_PAGE
import io.reactivex.disposables.CompositeDisposable


class UserPagedListRepository(private val apiService: GithubService) {
    private lateinit var userDataSourceFactory: UserDataSourceFactory

    fun fetchLiveUserPagedList(query: String,
                               compositeDisposable: CompositeDisposable)
            : LiveData<PagedList<User>> {
        userDataSourceFactory = UserDataSourceFactory(apiService, query, compositeDisposable)

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(4)
                .setPageSize(PER_PAGE)
                .build()

        return LivePagedListBuilder(userDataSourceFactory, config).build()
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<UserDataSource, NetworkState>(
                userDataSourceFactory.usersLiveData, UserDataSource::networkState
        )
    }
}
