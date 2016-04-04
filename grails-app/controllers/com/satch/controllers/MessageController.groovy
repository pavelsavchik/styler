package com.satch.controllers

import com.satch.MessageStatus
import com.satch.domain.Message
import com.satch.domain.Store
import com.satch.domain.User
import grails.converters.JSON
import grails.transaction.Transactional
import grails.web.servlet.mvc.GrailsParameterMap

class MessageController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: "GET", list: "GET"]

    def messageService
    def springSecurityService

    def list() {
        params.'user' = springSecurityService.getCurrentUser()

        def messages = messageService.search(params)

        response.reset()
        response.setStatus(HttpURLConnection.HTTP_PARTIAL)
        def offset = params.offset && params.offset.isLong() ? params.long('offset') : 0
        def rangeStart = offset.toString()
        def rangeEnd = (offset + messages.size() - 1).toString()
        def count = Message.count().toString()
        response.setHeader("Content-range", "messages $rangeStart-$rangeEnd/$count")
        return render(messages as JSON)
    }

    @Transactional
    def save() {
        Message newMessage = new Message(request.JSON)
//        message.sender = User.get(1)
        newMessage.sender = springSecurityService.getCurrentUser()

        newMessage.date = new Date()
        newMessage.status = 100

        newMessage.validate()

        if (!newMessage.hasErrors()) {
            try {
                newMessage.save(flush: true, failOnError: true)
            } catch (e) {
                response.status = 500
                return render(newMessage.errors.allErrors)
            }

            response.status = 200
            return render(newMessage as JSON)
        } else {
            def errors = newMessage.errors.allErrors.collect() { error ->
                [field: error.field, message: message(error: error)]
            }
            response.status = 400
            return render([errors: errors] as JSON)
        }
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

    @Transactional
    def update() {
        if (!params.id || !params.id.isLong()) {
            return render(status: HttpURLConnection.HTTP_BAD_REQUEST, text: '')
        }
        def updatedMessage = Message.get(params.id)
        if (!updatedMessage) {
            return render(status: HttpURLConnection.HTTP_NOT_FOUND, text: '')
        }

        bindData(updatedMessage, params)
        updatedMessage.validate()

        if (!updatedMessage.hasErrors()) {
            try {
                updatedMessage.save(flush: true, failOnError: true)
            } catch (e) {
                response.status = 500
                return render(updatedMessage.errors.allErrors)
            }

            response.status = 200
            return render(updatedMessage as JSON)
        } else {
            def errors = updatedMessage.errors.allErrors.collect() { error ->
                [field: error.field, message: message(error: error)]
            }
            response.status = 400
            return render([errors: errors] as JSON)
        }
    }

    @Transactional
    def delete() {
        if (!params.id || !params.id.isLong()) {
            return render(status: HttpURLConnection.HTTP_BAD_REQUEST, text: '')
        }
        def deletedMessage = Message.get(params.id)
        if (!deletedMessage) {
            return render(status: HttpURLConnection.HTTP_NOT_FOUND, text: '')
        }
        try {
            deletedMessage.status = 600
            deletedMessage.save(failOnError: true)
//            deletedMessage.delete(flush: true)
        } catch (e) {
            return render(status: HttpURLConnection.HTTP_INTERNAL_ERROR, text: e.message)
        }
        return render(status: HttpURLConnection.HTTP_OK, text: '')
    }

}
