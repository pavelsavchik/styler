package com.satch.domain

class Catalog {

    String catalogId
    String description

    static belongsTo = [store : Store]
    static hasMany = [attributeValues: AttributeValue, products: Product]

    static constraints = {
        catalogId blank: false, unique: 'store'
        description blank: false
    }
}
