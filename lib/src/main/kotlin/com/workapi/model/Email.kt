package com.workapi.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import moe.banana.jsonapi2.HasMany
import moe.banana.jsonapi2.JsonApi

/**
 * @see EmailSerializerTest for sample JSON
 */
@JsonApi(type = "email")
data class Email(
    @field:Json(name = "thread_id") var threadId: String? = "",
    @field:Json(name = "subject") var subject: String = "",
    @field:Json(name = "sender") var sender: Recipient? = Recipient(),
    @field:Json(name = "to_recipients") var toRecipients: MutableList<Recipient> = mutableListOf(),
    @field:Json(name = "cc_recipients") var ccRecipients: MutableList<Recipient> = mutableListOf(),
    @field:Json(name = "bcc_recipients") var bccRecipients: MutableList<Recipient> = mutableListOf(),
    @field:Json(name = "flags") var flags: Flags = Flags(),
    @field:Json(name = "body") var body: EmailBody = EmailBody(),
    @field:Json(name = "labels") var labels: MutableList<String> = mutableListOf(),
    @field:Json(name = "received_at") var receivedAt: Long = 0,

    @field:Json(name = "mailboxes") var mailboxes: HasMany<Mailbox>? = HasMany(),
    @field:Json(name = "email_attachments") var emailAttachments: HasMany<EmailAttachment>? = HasMany()
) : WorkApiResource()

@JsonClass(generateAdapter = true)
data class Flags(
    @Json(name = "seen") var seen: Boolean = false,
    @Json(name = "flagged") var flagged: Boolean = false
)

@JsonClass(generateAdapter = true)
data class EmailBody(
    @Json(name = "plain_text") var plainText: MutableList<String>? = null,
    @Json(name = "html") var html: MutableList<String>? = null
)
