package com.satch.domain

class Address {

    String city
    String street
    String home

    static hasMany = [phones: Phone]

    static constraints = {
        city blank: false
        street blank: false
        home blank: false
    }

    @Override
    String toString() {
        "$city, $street, $home"
    }
}
