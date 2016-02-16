package com.satch.domain

class Phone {

    String number

    static constraints = {
        number blank: false, unique: true
    }
}
