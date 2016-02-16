package com.satch.service

import com.satch.domain.Product
import com.satch.domain.ProductCount
import grails.transaction.Transactional

@Transactional
class ProductCountService {

    static final def PROPERTIES_DELIMITER = ','
    static final def PROPERTIES_EQUAL_SYMBOL = ':'

    Map parseProperties(String properties){
        if(!properties) {
            return [:]
        }
        return properties.substring(1, properties.size() - 1).split(PROPERTIES_DELIMITER).collectEntries { property ->
            def (attribute, value) = property.split(PROPERTIES_EQUAL_SYMBOL)
            if(!property){
                throw new RuntimeException("Attribute value with id $attributeValueId does not exist")
            }
            return [(attribute) : value]
        }
    }

    String formProperties(Map propertiesMap){
        def properties = ""
        propertiesMap.sort{it.key}.each {
            properties += "${it.key}" + PROPERTIES_EQUAL_SYMBOL + "${it.value}" + PROPERTIES_DELIMITER}
        return "{" + properties.substring(0, properties.size() - 1) + "}"
    }

    ProductCount increase(Product product, Map attributeValues, int countDelta) {
        def productCount = retrieve(product, attributeValues)
        if(!productCount) {
            create(product, attributeValues, countDelta)
        } else {
            productCount.count += countDelta
            productCount.save(failOnError: true)
        }
        return productCount
    }

    ProductCount increase(ProductCount productCount, int countDelta) {
        if(productCount.parent) {
            increase(productCount.parent, countDelta)
        }
        productCount.count += countDelta
        productCount.save(failOnError: true)
        return productCount
    }

    ProductCount retrieve(Product product, Map propertiesMap) {
        if(!product){
            throw new RuntimeException("Product must not be null")
        }
        def properties = formProperties(propertiesMap)
        return ProductCount.executeQuery(
                "from ProductCount where product = :product and properties = :properties",
                ['product': product, 'properties': properties]).first()
    }

    ProductCount create(Product product, Map attributeValues, Integer count = 0) {
        if(!product){
            throw new RuntimeException("Product must not be null")
        }
        def avIds = formProperties(attributeValues)
        new ProductCount(
                product: product,
                count: count,
                properties: avIds
        ).save(failOnError: true)
    }

    ProductCount createChild(ProductCount parent, Integer childQuantity = 0) {
        def child = new ProductCount(
                product: parent.product, count: childQuantity, parentProductCount: parent
        ).save(failOnError: true)
        parent.addToChildProductCount(child).save(failOnError: true)
        return child
    }

    def update(Product product, Map attributeValues, int count) {
        def productCount = retrieve(product, attributeValues)
        if(!productCount) {
            if(count < 0) {
                product.errors.reject(
                        "Product count for product $product and Attribute values set $attributeValues" +
                                "does not exist. Can't create ProductCount with count $count < 0"
                )
            } else {
                create(product, attributeValues, count)
            }
        } else {
            if(count < 0) {
                product.errors.reject(
                        "ProductCount $count < 0"
                )
            } else {
                productCount.count = count
                productCount.save(failOnError: true)
            }
        }
    }

    def delete(Product product, Map attributeValues) {
        def productCount = retrieve(product, attributeValues)
        if(!productCount) {
            product.errors.reject(
                "Product count for product $product and properties $attributeValues does not exist"
            )
        } else {
            productCount.delete(failOnError: true)
        }
    }
}
