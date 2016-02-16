package com.satch.domain

class Customer {

    String customerId

    static constraints = {
        customerId blank: false
    }
}
