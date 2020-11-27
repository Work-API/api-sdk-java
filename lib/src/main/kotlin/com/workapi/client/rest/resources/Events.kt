package com.workapi.client.rest.resources

import com.workapi.serializers.CalendarSerializer
import com.workapi.serializers.EventSerializer
import com.workapi.model.Calendar
import com.workapi.model.Event
import com.workapi.client.rest.Response
import com.workapi.client.rest.WorkApiClient
import com.mashape.unirest.http.HttpResponse

class Events(client: WorkApiClient): BaseResource(client) {
    private val eventAdapter = EventSerializer()
    private val calendarAdapter =
        CalendarSerializer()

    fun listCalendars(): Response<Calendar> {
        val path = "/event/calendars"
        val response : HttpResponse<String> = makeRetryableRequest({ client.httpGet(path)!! })

        return Response(
            status = response.code,
            data = calendarAdapter.deserializeCalendars(response.body)
        )
    }

    fun list(
        eventIds: List<String>? = null,
        calendarIds: List<String>? = null,
        recurringEventId: String? = null,
        dateFrom: String? = null,
        dateUntil: String? = null,
        page: String? = null,
        limit: Int? = 10,
        searchText: String? = null
    ): Response<Event> {
        var queryParams: Array<String> = arrayOf()

        if (eventIds != null) { queryParams += buildArrayQueryParam("ids", eventIds) }
        if (calendarIds != null) { queryParams += buildArrayQueryParam("calendar_ids", calendarIds) }
        if (recurringEventId != null) { queryParams += "recurring_event_id=$recurringEventId" }

        if (dateFrom != null) { queryParams += "date_from=${dateFrom}" }
        if (dateUntil != null) { queryParams += "date_until=${dateUntil}" }
        if (page != null) { queryParams += "page=$page" }
        if (limit != null) { queryParams += "limit=$limit" }
        if (searchText != null) { queryParams += "search_text=$searchText" }

        val path = "/event/events"
        val response : HttpResponse<String> = makeRetryableRequest({ client.httpGet(path, queryParams)!! })

        return Response<Event>(
            status = response.code,
            data = eventAdapter.deserializeEvents(response.body)
        )
    }

    fun fetch(eventId: String): Response<Event> {
        val path = "/event/events/$eventId"
        val response : HttpResponse<String> = makeRetryableRequest({ client.httpGet(path)!! })

        return Response<Event>(
            status = response.code,
            data = eventAdapter.deserializeEvent(response.body)
        )
    }

    fun listCalendarIds(integrationId: String?): List<String>? {
        val calendars = listCalendars().asMany()

        return calendars
            ?.filter { integrationId == null || it.getIntegrationId() == integrationId }
            ?.map { it.id }
    }
}
