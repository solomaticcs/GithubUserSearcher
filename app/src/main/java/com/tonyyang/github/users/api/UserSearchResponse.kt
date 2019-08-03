package com.tonyyang.interview.m17pretest.api

import com.google.gson.annotations.SerializedName
import com.tonyyang.interview.m17pretest.data.model.User


class UserSearchResponse {
    @SerializedName("total_count")
    val totalCount: Int = 0

    @SerializedName("items")
    var items: List<User> = mutableListOf()

    override fun toString(): String {
        return "UserSearchResponse(totalCount=$totalCount, items=$items)"
    }
}
