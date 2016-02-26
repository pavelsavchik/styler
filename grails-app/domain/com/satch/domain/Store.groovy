package com.satch.domain

class Store {
    String storeId
    String name
    String description
    Address address

    static belongsTo = [supplier : Supplier]
    static hasMany = [catalogs: Catalog, attributeValues: AttributeValue, stocks: ProductCount]

    static constraints = {
        storeId blank: false, unique: 'supplier'
        name blank: false
        description blank: false
        supplier nullable: false, blank: false
    }

    static mapping = {
        attributeValues cascade: 'all-delete-orphan'
        stocks cascade: 'all-delete-orphan'
    }
}
