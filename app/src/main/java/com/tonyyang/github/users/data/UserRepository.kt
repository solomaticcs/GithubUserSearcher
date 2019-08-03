package com.tonyyang.interview.m17pretest.data

import com.tonyyang.interview.m17pretest.api.GithubServerApi
import com.tonyyang.interview.m17pretest.api.UserSearchResponse
import com.tonyyang.interview.m17pretest.data.model.User
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object UserRepository {

    private val githubServerApi: GithubServerApi

    init {
        val baseUrl = "https://api.github.com"
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        githubServerApi = retrofit.create(GithubServerApi::class.java)
    }

    fun searchUsers(query: String): Single<Response<UserSearchResponse>> = githubServerApi.searchUsers(query)
}
