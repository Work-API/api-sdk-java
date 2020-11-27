package com.workapi.serializers

import com.workapi.model.Email
import com.workapi.model.EmailAttachment
import com.workapi.model.Mailbox
import moe.banana.jsonapi2.ArrayDocument
import moe.banana.jsonapi2.ObjectDocument
import moe.banana.jsonapi2.ResourceAdapterFactory

/**
 * @see EmailSerializerTest for sample JSON
 */
class EmailSerializer: BaseSerializer(typeClass = Email::class.java) {
    fun deserializeEmail(body: String): ObjectDocument<Email> {
        return adapter().fromJson(body)!!.asObjectDocument()
    }

    fun deserializeEmails(body: String): ArrayDocument<Email> {
        return adapter().fromJson(body)!!.asArrayDocument()
    }

    fun serializeEmail(email: Email): String {
        val document: ObjectDocument<Email> = ObjectDocument()
        document.set(email)
        return serializeDocument(document)
    }

    fun serializeEmails(emails: List<Email>): String {
        val document: ArrayDocument<Email> = ArrayDocument()
        document.addAll(emails)
        return serializeDocument(document)
    }

    override fun adapterFactory(): ResourceAdapterFactory.Builder {
        return super
            .adapterFactory()
            .add(Mailbox::class.java)
            .add(EmailAttachment::class.java)
    }
}
