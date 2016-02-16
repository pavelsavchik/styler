package com.satch

import com.satch.domain.Attribute
import com.satch.domain.AttributeValue
import com.satch.domain.Product
import grails.transaction.Transactional

@Transactional
class FilterService {
    static final FILTER_ATTRIBUTE_IDS = ['material', 'size', 'color']

    def formPriceFilter(){
        def maxPrice = Product.createCriteria().get{
            projections {
                price {
                    max "value"
                }
            }
        }
        def minPrice = Product.createCriteria().get{
            projections {
                price {
                    min "value"
                }
            }
        }
        return [maxPrice: maxPrice, minPrice: minPrice]
    }

    def formAttributeFilters(){
        Map<Attribute, List> filters = [:]
        FILTER_ATTRIBUTE_IDS.each { attributeId ->
            def values = AttributeValue.executeQuery("select distinct av.value from AttributeValue av where av.attribute.attributeId = ?", attributeId).sort()
            def filter = Attribute.findByAttributeId(attributeId)
            filters.put(filter, values)
        }
        return filters
    }

    def formManufacturerFilter(){
        def manufacturers = Product.executeQuery("select distinct pd.manufacturer from Product pd")
        [manufacturers: manufacturers]
    }

    def getFilterAttributeIds(){
        return FILTER_ATTRIBUTE_IDS
    }
}
