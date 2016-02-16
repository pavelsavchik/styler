package com.satch.domain

class ProductCount {
    Product product
    //string with "{<attributeId>:<value>;...;<attributeId>:<value>}"
    String props
    Integer count

    static hasMany = [children: ProductCount]
    static belongsTo = [parent: ProductCount]

    static constraints = {
        count min: 0
        product nullable: false
    }

    static mapping = {
        children cascade: 'all-delete-orphan'
    }
}
