package com.satch.domain

class SalesOrderItem {
    SalesOrder salesOrder
    Product product

    static constraints = {
        salesOrder nullable: false
        product nullable: false
    }
}
