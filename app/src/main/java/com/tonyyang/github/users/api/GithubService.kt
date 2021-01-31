package com.tonyyang.github.users.api

import com.tonyyang.github.users.model.SearchUserResponse
import com.tonyyang.github.users.retrofit.AppClientManager
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface GithubService {
    @GET("search/users")
    fun searchUsers(
            @Query("q") query: String,
            @Query("per_page") perPage: Int,
            @Query("page") page: Int
    ): Observable<SearchUserResponse>

    companion object {
        fun getService(): GithubService {
            return AppClientManager.client.create(GithubService::class.java)
        }
    }
}
