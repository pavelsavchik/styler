package com.satch.service

import com.satch.domain.Classification
import com.satch.domain.Price
import com.satch.domain.Product
import com.satch.domain.Store
import grails.transaction.Transactional
import grails.web.servlet.mvc.GrailsParameterMap

@Transactional
class ProductService {
    final static BASIC_ATTRIBUTE_IDS = ["image1", "image2", "image3", "image4", "image5"]

    def retrieveProductList(){
        return Product.list()
    }

    def retrieveProductByStoreId(String id){
        def catalogs = Store.get(id).catalogs
        return Product.findAllByCatalogInList(catalogs)
    }

    def getProductAttributeValues(Product product){
        product.attributeValues.findAll {! (it.attribute.attributeId in BASIC_ATTRIBUTE_IDS)}
    }

    def getProductImageNumbers(Product product){
        def resultList = []
        (1..5).each{i ->
            if(product.attributeValues.find {it.attribute.attributeId == "image${i}"}?.value)
                resultList << i
        }
        println "resultList : $resultList"
        return resultList
    }

    def formFilterList(){
        def color
    }

    Product initProduct(GrailsParameterMap params){
        def product = new Product(params)
        //TODO: remove productId from Product domain or implement productId init logic
//        product.productId = Product.ne

        product.validate()


//        println "${product.classification.id}"

//        if(params.'classification.id'){
//            if(params.'classification.id'.isLong()){
//                def classification = Classification.get(params.long('classification.id'))
//                if(classification){
//                    product.classification = classification
//                } else {
//                    product.errors.rejectValue("classification", "Classification with id ${params.'classification.id'} not found!")
//                }
//            } else {
//                product.errors.rejectValue('classification', "Classification is incorrect.")
//            }
//        } else {
//            product.errors.rejectValue('classification', 'Classification must not be empty.')
//        }

//        if(params.price){
//            if(params.price.isNumber()){
//                product.price = new Price(value : params.float('price'))
//            } else {
//                product.errors.rejectValue('price', 'Price is incorrect.')
//            }
//        } else {
//            product.errors.rejectValue('price', 'Price must not be empty.')
//        }


        return product

    }
}
