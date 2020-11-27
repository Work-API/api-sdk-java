package com.workapi.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Recipient (
    @Json(name = "name") var name: String = "",
    @Json(name = "address") var address: String = ""
)
