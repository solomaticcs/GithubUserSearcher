package com.tonyyang.github.users.api

import com.tonyyang.github.users.model.SearchUserResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface GithubService {
    @GET("search/users")
    fun searchUsers(
            @Query("q") query: String,
            @Query("per_page") perPage: Int,
            @Query("page") page: Int
    ): Single<SearchUserResponse>

    companion object {
        fun getService(): GithubService {
            return Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(GithubService::class.java)
        }
    }
}
