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


    def list(){
        def products = productSearchService.searchProducts(params)

        response.reset()
        response.setStatus(206)
        response.setHeader("Content-range", "products " + params.offset ? params.offset.toString() : "0" + "-" + products.size().toString() + "/" + Long.toString(Product.count(params)));
        render products as JSON
    }

    def show(){
        if(!params.productId){
            return render(status: BAD_REQUEST)
        }

        def product = Product.findByProductId(params.productId)
        if(!product){
            return render(status: NOT_FOUND)
        }
        render product as JSON
    }

    @Transactional
    def save(){
        Product product = new Product(params)

        product.validate()

        if(!product.hasErrors()){
            try {
                product.save(flush: true, failOnError: true)
            } catch (e) {
                response.status = 500
                return render(product.errors.allErrors)
            }

            response.status = 200
            return render(product as JSON)
        }
        else {
            def errors = product.errors.allErrors.collect() { error ->
                [field: error.field, message: message(error: error)]
            }
            response.status = 400
            return render ([errors: errors] as JSON)
        }
    }

    def update(){
        if(!params.id){
            render status: BAD_REQUEST
        }
        def product = Product.get(params.id)
        if(!product){
            return render(status: NOT_FOUND)
        }

        bindData(product, params)
        product.validate()

        if(!product.hasErrors()){
            try {
                product.save(flush: true, failOnError: true)
            } catch (e) {
                response.status = 500
                return render(product.errors.allErrors)
            }

            response.status = 200
            return render(product as JSON)
        }
        else {
            def errors = product.errors.allErrors.collect() { error ->
                [field: error.field, message: message(error: error)]
            }
            response.status = 400
            return render ([errors: errors] as JSON)
        }
    }

    def delete(){
        if(!params.id){
            return render(status: BAD_REQUEST)
        }
        def product = Product.get(params.id)
        if(!product){
            return render (status: NOT_FOUND)
        }
        try {
            product.delete(flush: true)
        } catch (e) {
            return render (status: INTERNAL_SERVER_ERROR)
        }
        return render (status: OK)
    }

}