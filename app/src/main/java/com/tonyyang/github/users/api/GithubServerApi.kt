package com.tonyyang.interview.m17pretest.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface GithubServerApi {
    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Single<Response<UserSearchResponse>>
}
