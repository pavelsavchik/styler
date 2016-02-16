package com.satch.domain

class Supplier {

    String supplierId
    String supplierName
    Classification classification
    ClassificationGroup classificationGroup

    static hasMany = [stores: Store, attributeValues: AttributeValue, stocks: ProductCount, users: User]

    static constraints = {
        supplierId blank: false, unique: true
        supplierName nullable: true, blank: false
        users nullable: true
    }

    @Override
    String toString() {
        "$supplierId"
    }
}
