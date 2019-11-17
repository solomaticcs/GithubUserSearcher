package com.tonyyang.github.users.repository

import androidx.lifecycle.Transformations
import androidx.paging.Config
import androidx.paging.toLiveData
import com.tonyyang.github.users.api.GithubService
import com.tonyyang.github.users.model.User
import com.tonyyang.github.users.repository.UserDataSource.Companion.PER_PAGE_SIZE


class UserPagedListRepository(private val apiService: GithubService) {

    fun postsOfGithubUsers(query: String): Listing<User> {
        val sourceFactory = UserDataSourceFactory(apiService, query)
        val livePagedList = sourceFactory.toLiveData(
                config = Config(
                        pageSize = PER_PAGE_SIZE,
                        enablePlaceholders = false,
                        prefetchDistance = 4
                )
        )
        return Listing(
                pagedList = livePagedList,
                refreshState = Transformations.switchMap(sourceFactory.usersLiveData) {
                    it.initialLoad
                },
                refresh = {
                    sourceFactory.usersLiveData.value?.invalidate()
                }
        )
    }

}
