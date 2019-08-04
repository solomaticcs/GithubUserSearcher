package com.tonyyang.github.users.model

import com.google.gson.annotations.SerializedName

class SearchUserResponse {
    @SerializedName("total_count")
    val totalCount: Int = 0

    @SerializedName("items")
    var items: List<User> = mutableListOf()

    override fun toString(): String {
        return "SearchUserResponse(totalCount=$totalCount, items=$items)"
    }
}
