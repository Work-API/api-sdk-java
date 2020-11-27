package com.workapi.serializers

import com.workapi.model.Email
import com.workapi.model.Event
import moe.banana.jsonapi2.ArrayDocument
import moe.banana.jsonapi2.ObjectDocument

/**
 * @see EventSerializerTest for sample JSON
 */
class EventSerializer: BaseSerializer(typeClass = Event::class.java) {
    fun deserializeEvent(body: String): ObjectDocument<Event> {
        return adapter().fromJson(body)!!.asObjectDocument()
    }

    fun deserializeEvents(body: String): ArrayDocument<Event> {
        return adapter().fromJson(body)!!.asArrayDocument()
    }

    fun serializeEvent(event: Event): String {
        val document: ObjectDocument<Event> = ObjectDocument()
        document.set(event)
        return serializeDocument(document)
    }

    fun serializeEvents(events: List<Event>): String {
        val document: ArrayDocument<Event> = ArrayDocument()
        document.addAll(events)
        return serializeDocument(document)
    }
}
