package com.satch.controllers

import grails.converters.JSON
import com.satch.domain.Product
import grails.converters.JSON

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
//        product.list()

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

    def save(){
        def product = new Product(params)
        try {
            product.save(flush: true, failOnError: true)
        } catch (e) {
            render(INTERNAL_SERVER_ERROR)
        }
        render status: CREATED
    }

    def update(){
        if(!params.id){
            render status: BAD_REQUEST
        }
        def product = Product.get(params.id)
        if(!product){
            render status: NOT_FOUND
        } else {
            bindData(product, params)
            try {
                product.save(flush: true, failOnError: true)
            } catch (e) {
                render(INTERNAL_SERVER_ERROR)
            }
            render status: OK
        }
    }

    def delete(){
        if(!params.id){
            render status: BAD_REQUEST
        }
        def product = Product.get(params.id)
        if(!product){
            render status: NOT_FOUND
        }
        try {
            product.delete(flush: true)
        } catch (e) {
            render status: INTERNAL_SERVER_ERROR
        }
        render status: OK
    }

}