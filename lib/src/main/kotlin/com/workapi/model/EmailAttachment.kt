package com.workapi.model

import com.squareup.moshi.Json
import moe.banana.jsonapi2.JsonApi

@JsonApi(type = "email_attachment")
data class EmailAttachment(
    @field:Json(name = "inline") var inline: Boolean = false,
    @field:Json(name = "filename") var filename: String = "",
    @field:Json(name = "size") var size: Int = 0,
    @field:Json(name = "mime_type") var mimeType: String = ""
) : WorkApiResource()
