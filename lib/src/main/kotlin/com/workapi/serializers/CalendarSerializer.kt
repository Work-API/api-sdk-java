package com.workapi.serializers

import com.workapi.model.Calendar
import moe.banana.jsonapi2.ArrayDocument
import moe.banana.jsonapi2.ObjectDocument

class CalendarSerializer: BaseSerializer(typeClass = Calendar::class.java) {
    fun deserializeCalendar(body: String): ObjectDocument<Calendar> {
        return adapter().fromJson(body)!!.asObjectDocument()
    }

    fun deserializeCalendars(body: String): ArrayDocument<Calendar> {
        return adapter().fromJson(body)!!.asArrayDocument()
    }
}
