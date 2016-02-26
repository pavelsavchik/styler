package com.satch.controllers

import grails.converters.JSON
import com.satch.domain.Product
import grails.converters.JSON
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

class ProductController {
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: "GET", list: "GET"]

    def productSearchService

    def list() {
        def products = productSearchService.searchProducts(params)

        response.reset()
        response.setStatus(HttpURLConnection.HTTP_PARTIAL)
        def offset = params.offset && params.offset.isLong() ? params.long('offset') : 0
        def rangeStart = offset.toString()
        def rangeEnd = (offset + products.size() - 1).toString()
        def count = Product.count().toString()
        response.setHeader("Content-range", "products $rangeStart-$rangeEnd/$count")
        render products as JSON
    }

    def show() {
        if (!params.id || !params.id.isLong()) {
            return render(status: HttpURLConnection.HTTP_BAD_REQUEST, text: '')
        }

        def product = Product.get(params.id)
        if (!product) {
            return render(status: HttpURLConnection.HTTP_NOT_FOUND, text: '')
        }
        return render(product as JSON)
    }

    @Transactional
    def save() {
        Product product = new Product(params)

        product.validate()

        if (!product.hasErrors()) {
            try {
                product.save(flush: true, failOnError: true)
            } catch (e) {
                response.status = HttpURLConnection.HTTP_INTERNAL_ERROR
                return render(product.errors.allErrors)
            }

            response.status = HttpURLConnection.HTTP_OK
            return render(product as JSON)
        } else {
            def errors = product.errors.allErrors.collect() { error ->
                [field: error.field, message: message(error: error)]
            }
            response.status = HttpURLConnection.HTTP_BAD_REQUEST
            return render([errors: errors] as JSON)
        }
    }

    @Transactional
    def update() {
        if (!params.id || !params.id.isLong()) {
            return render(status: HttpURLConnection.HTTP_BAD_REQUEST, text: '')
        }
        def product = Product.get(params.id)
        if (!product) {
            return render(status: HttpURLConnection.HTTP_NOT_FOUND, text: '')
        }

        bindData(product, params)
        product.validate()

        if (!product.hasErrors()) {
            try {
                product.save(flush: true, failOnError: true)
            } catch (e) {
                response.status = HttpURLConnection.HTTP_INTERNAL_ERROR
                return render(product.errors.allErrors)
            }

            response.status = HttpURLConnection.HTTP_OK
            return render(product as JSON)
        } else {
            def errors = product.errors.allErrors.collect() { error ->
                [field: error.field, message: message(error: error)]
            }
            response.status = 400
            return render([errors: errors] as JSON)
        }
    }

    def delete() {
        if (!params.id || !params.id.isLong()) {
            return render(status: HttpURLConnection.HTTP_BAD_REQUEST, text: '')
        }
        def product = Product.get(params.id)
        if (!product) {
            return render(status: HttpURLConnection.HTTP_NOT_FOUND, text: '')
        }
        try {
            product.delete(flush: true)
        } catch (e) {
            return render(status: HttpURLConnection.HTTP_INTERNAL_ERROR, text: 'Delete failed.')
        }
        return render(status: HttpURLConnection.HTTP_OK, text: '')
    }

}