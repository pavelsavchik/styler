package com.satch.domain

class AttributeValue {

    Attribute attribute
    String value

    static constraints = {
        attribute nullable: false
        value blank: false
    }
}
