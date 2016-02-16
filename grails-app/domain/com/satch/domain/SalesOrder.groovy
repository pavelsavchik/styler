package com.satch.domain

class SalesOrder {
    String salesOrderId

    static hasMany = [salesOrderItems: SalesOrderItem]

    static constraints = {
        salesOrderId blank: false
    }
}
