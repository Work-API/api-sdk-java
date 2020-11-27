package com.workapi.client.rest.api

import com.workapi.model.Metadata
import com.workapi.model.WorkApiResource
import moe.banana.jsonapi2.Document

/**
 * Wrapper around response info from the backend
 *
 * @version 0.2
 */
interface IResponse<T : WorkApiResource> {

    /**
     * HTTP status code from the backend
     *
     * e.g. 200, 204, 418, etc.
     */
    val status: Int

    /**
     * `meta` will only be present on multi-result responses.
     * For singular responses it will be `null`
     */
    val meta: Metadata?

    /**
     * Document containing raw response from backend
     */
    var data: Document?

    /**
     * When returning multiple results, call `asMany()`
     *
     * <code>
     *    val response: Response<Email> = client.emails.list()
     *    val emails: List<Email> = response.asMany()
     * </code>
     *
     * Calling `asMany()` on a singular result will return an array
     * containing that single item
     */
    fun asMany(): List<T>?

    /**
     *  When returning a single result, call `asSingle()`:
     *
     * <code>
     *   val response: Response<Email> = client.emails.fetch(emailId)
     *   val email: Email = response.asSingle()
     * </code>
     *
     * Note:
     *   Calling `asSingle()` on a response containing multiple results
     *   will return the first in the list
     */
    fun asSingle(): T?
}
