package com.satch.service

import com.satch.domain.Product
import com.satch.domain.Store
import grails.transaction.Transactional

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
}
