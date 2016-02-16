package com.satch.domain

import com.satch.AttributeType

class Attribute {

    String attributeId
    String description
    AttributeType type
    Boolean isMultivalue
    OptionList defaultOptionList

    static constraints = {
        attributeId blank: false
        description blank: false
        type nullable: false
        isMultivalue nullable: true
        //TODO: maybe remove
        defaultOptionList nullable: true
    }
}
