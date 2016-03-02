package com.satch.controllers

import com.satch.domain.Message
import com.satch.domain.Store
import com.satch.domain.User
import grails.converters.JSON

class MessageController {

    static allowedMethods = [list: "GET", requestAvailability: "POST", sendMessage: "POST", getCurrentUserMessageCount: "GET", show: "GET"]

    def messageService
    def springSecurityService

    def list() {

        def messages = messageService.getUserMessages(springSecurityService.getCurrentUser(), params)

        response.reset()
        response.setStatus(HttpURLConnection.HTTP_PARTIAL)
        def offset = params.offset && params.offset.isLong() ? params.long('offset') : 0
        def rangeStart = offset.toString()
        def rangeEnd = (offset + messages.size() - 1).toString()
        def count = Store.count().toString()
        response.setHeader("Content-range", "messages $rangeStart-$rangeEnd/$count")
        return render(messages as JSON)
    }

    def getCurrentUserMessageCount() {
        render messageService.getCurrentUserUnreadMessageCount() as JSON
    }

    def requestAvailability() {
        if (!params.'product.id' || !params.'store.id') {
            return render(status: HttpURLConnection.HTTP_BAD_REQUEST, text: '')
        }

        try {
            def message = messageService.formRequestAvailabilityMessage(params.text, params.long('store.id'), params.long('product.id'))
            message.save(failOnError: true)
        } catch (e) {
            return render(status: HttpURLConnection.HTTP_INTERNAL_ERROR, text: e.message)
        }

        return render(status: HttpURLConnection.HTTP_OK, text: '')
    }

    def sendMessage() {
        if (!params.text || !params.'user.id' || !params.'user.id'.isLong()) {
            return render(status: HttpURLConnection.HTTP_BAD_REQUEST, text: '')
        }

        def recipient = User.get(params.long('user.id'))
        if (!recipient) {
            return render(status: HttpURLConnection.HTTP_NOT_FOUND, text: "Recipient (user) with id ${params.'user.id'} not found!")
        }

        try {
            new Message(
                    text: params.text,
                    sender: springSecurityService.getCurrentUser(),
                    recipient: recipient,
                    date: new Date()
            ).save()
        } catch (e) {
            return render(status: HttpURLConnection.HTTP_INTERNAL_ERROR, text: e.message)
        }

        return render(status: HttpURLConnection.HTTP_OK, text: '')

    }

    def show() {
        if (!params.id || !params.id.isLong()) {
            return render(status: HttpURLConnection.HTTP_BAD_REQUEST, text: '')
        }

        def message = Message.get(params.id)
        if (!message) {
            return render(status: HttpURLConnection.HTTP_NOT_FOUND, text: '')
        }

        return render(message as JSON)
    }


}
