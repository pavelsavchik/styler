package com.satch.controllers

import com.satch.domain.Product
import com.satch.domain.Store
import grails.converters.JSON
import grails.transaction.Transactional

class StoreController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: "GET", list: "GET"]

    def list() {
        def stores = Store.list(params)

        response.reset()
        response.setStatus(HttpURLConnection.HTTP_PARTIAL)
        def offset = params.offset && params.offset.isLong() ? params.long('offset') : 0
        def rangeStart = offset.toString()
        def rangeEnd = (offset + stores.size() - 1).toString()
        def count = Store.count().toString()
        response.setHeader("Content-range", "stores $rangeStart-$rangeEnd/$count")
        return render(stores as JSON)
    }

    def show() {
        if (!params.id || !params.id.isLong()) {
            return render(status: HttpURLConnection.HTTP_BAD_REQUEST, text: '')
        }

        def store = Store.get(params.id)
        if (!store) {
            return render(status:  HttpURLConnection.HTTP_NOT_FOUND, text: '')
        }
        return render(store as JSON)
    }

    @Transactional
    def save() {
        Store store = new Store(params)

        store.validate()

        if (!store.hasErrors()) {
            try {
                store.save(flush: true, failOnError: true)
            } catch (e) {
                response.status = 500
                return render(store.errors.allErrors)
            }

            response.status = 200
            return render(store as JSON)
        } else {
            def errors = store.errors.allErrors.collect() { error ->
                [field: error.field, message: message(error: error)]
            }
            response.status = 400
            return render([errors: errors] as JSON)
        }
    }

    @Transactional
    def update() {
        if (!params.id || !params.id.isLong()) {
            return render(status: HttpURLConnection.HTTP_BAD_REQUEST, text: '')
        }
        def store = Store.get(params.id)
        if (!store) {
            return render(status: HttpURLConnection.HTTP_NOT_FOUND, text: '')
        }

        bindData(store, params)
        store.validate()

        if (!store.hasErrors()) {
            try {
                store.save(flush: true, failOnError: true)
            } catch (e) {
                response.status = 500
                return render(store.errors.allErrors)
            }

            response.status = 200
            return render(store as JSON)
        } else {
            def errors = store.errors.allErrors.collect() { error ->
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
        def store = Store.get(params.id)
        if (!store) {
            return render(status: HttpURLConnection.HTTP_NOT_FOUND, text: '')
        }
        try {
            store.delete(flush: true)
        } catch (e) {
            return render(status: HttpURLConnection.HTTP_INTERNAL_ERROR, text: e.message)
        }
        return render(status: HttpURLConnection.HTTP_OK, text: '')
    }

}
