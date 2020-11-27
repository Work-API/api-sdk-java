package com.workapi.serializers

import com.workapi.model.Mailbox
import moe.banana.jsonapi2.ArrayDocument
import moe.banana.jsonapi2.ObjectDocument

class MailboxSerializer: BaseSerializer(typeClass = Mailbox::class.java) {
    fun deserializeMailbox(body: String): ObjectDocument<Mailbox> {
        return adapter().fromJson(body)!!.asObjectDocument()
    }

    fun deserializeMailboxes(body: String): ArrayDocument<Mailbox> {
        return adapter().fromJson(body)!!.asArrayDocument()
    }
}
