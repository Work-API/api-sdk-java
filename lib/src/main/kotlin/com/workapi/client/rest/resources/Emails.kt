package com.workapi.client.rest.resources

import com.workapi.serializers.EmailSerializer
import com.workapi.serializers.MailboxSerializer
import com.workapi.model.Email
import com.workapi.model.Mailbox
import com.workapi.client.rest.Response
import com.workapi.client.rest.WorkApiClient
import com.mashape.unirest.http.HttpResponse

class Emails(client: WorkApiClient): BaseResource(client) {
    private val emailAdapter = EmailSerializer()
    private val mailboxAdapter = MailboxSerializer()

    fun listMailboxes(): Response<Mailbox> {
        val path = "/email/mailboxes"
        val response = makeRetryableRequest({ client.httpGet(path)!! })

        return Response(
            status = response.code,
            data = mailboxAdapter.deserializeMailboxes(response.body)
        )
    }

    /**
     * By default will fetch messages from inboxes only
     */
    fun list(
        emailIds: List<String>? = null,
        mailboxIds: List<String>? = inboxIds(),
        dateFrom: String? = null,
        dateUntil: String? = null,
        page: String? = null,
        limit: Int? = 10,
        searchText: String? = null
    ): Response<Email> {
        // TODO : filter by date = today as default - in applicaton controller?
        var queryParams: Array<String> = arrayOf()

        if (emailIds != null) { queryParams += buildArrayQueryParam("ids", emailIds) }
        if (mailboxIds != null) { queryParams += buildArrayQueryParam("mailbox_ids", mailboxIds) }

        if (dateFrom != null) { queryParams += "date_from=${dateFrom}" }
        if (dateUntil != null) { queryParams += "date_until=${dateUntil}" }
        if (page != null) { queryParams += "page=$page" }
        if (limit != null) { queryParams += "limit=$limit" }
        if (searchText != null) { queryParams += "search_text=$searchText" }

        val path = "/email/emails"
        var response : HttpResponse<String> = makeRetryableRequest({ client.httpGet(path, queryParams)!! })

        return Response(
            status = response.code,
            data = emailAdapter.deserializeEmails(response.body)
        )
    }

    fun inboxIds(integrationId: String? = null): List<String>? {
        val mailboxes = listMailboxes().asMany()

        return mailboxes
            ?.filter { mailbox: Mailbox ->
                integrationId == null || mailbox.getIntegrationId() == integrationId
            }
            ?.filter { mailbox: Mailbox ->
                mailbox.name.matches(Regex("inbox", RegexOption.IGNORE_CASE))
            }?.map { mailbox: Mailbox ->
                mailbox.id
            }
    }


    fun fetch(emailId: String): Response<Email> {
        val path = "/email/emails/$emailId"
        val response : HttpResponse<String> = makeRetryableRequest({ client.httpGet(path)!! })

        return Response(
            status = response.code,
            data = emailAdapter.deserializeEmail(response.body)
        )
    }

    companion object {
        const val TAG: String = "Emails"
    }
}
