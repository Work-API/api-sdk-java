package com.workapi.model

import com.squareup.moshi.Json
import moe.banana.jsonapi2.JsonApi

@JsonApi(type = "mailbox")
data class Mailbox (
    @field:Json(name = "name") var name: String = "",
    @field:Json(name = "total_count") var totalCount: Int = 0,
    @field:Json(name = "unread_count") var unreadCount: Int = 0
) : WorkApiResource()
