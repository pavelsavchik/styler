package com.satch.domain

class Product {

    String productId
    String shortDesc
    String longDesc
    Price price
    Catalog catalog
    Classification classification
    ClassificationGroup classificationGroup
    Boolean isSearchable = false
    String manufacturer

    static hasMany = [attributeValues: AttributeValue]

    static constraints = {
        productId blank: false, unique: 'catalog'
        shortDesc nullable: false, blank: false
        catalog nullable: false
        classification nullable: true
        classificationGroup nullable: true
        manufacturer blank: false
        price nullable: true
        isSearchable nullable: true
    }
}
