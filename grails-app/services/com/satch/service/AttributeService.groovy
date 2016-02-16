package com.satch.service

import com.satch.AttributeType
import com.satch.domain.Attribute
import com.satch.domain.AttributeValue
import grails.transaction.Transactional

@Transactional
class AttributeService {

    def getAttributeValue(AttributeValue attributeValue){
        switch(attributeValue.attribute.type){
            case AttributeType.INT:
                return attributeValue.value.toInteger()
            case AttributeType.DECIMAL:
                return attributeValue.value.toBigDecimal()
            case AttributeType.STRING:
                return attributeValue.value
//            case AttributeType.LIST_INT:
//                return Eval.me(attributeValue.value)
//            case AttributeType.LIST_DECIMAL:
//                return Eval.me(attributeValue.value)
//            case AttributeType.LIST_STRING:
//                return Eval.me(attributeValue.value)
        }
    }

    def setAttributeValue(AttributeValue attributeValue, value){
        switch(attributeValue.attribute.type){
            case AttributeType.INT:
                return attributeValue.value = value.toString()
            case AttributeType.DECIMAL:
                return attributeValue.value = value.toString()
            case AttributeType.STRING:
                return attributeValue.value = value
//            case AttributeType.LIST_INT:
//                return attributeValue.value = value.toString()
//            case AttributeType.LIST_DECIMAL:
//                return attributeValue.value = value.toString()
//            case AttributeType.LIST_STRING:
//                return attributeValue.value = value.inspect()
        }
    }


}
