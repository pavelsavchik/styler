package com.satch.service

import com.satch.domain.Product
import grails.transaction.Transactional

//Map with search params should look next way
// ['productId' : ['pid1', 'pid2'], ..., attributeValues : ['color' : ['black, 'red']]]
//

@Transactional
class ProductSearchService {

    def SEARCH_FIELDS = [
            'productId',
            'catalogId',
            'supplierId',
            'storeId',
            'classificationId',
            'classificationGroupId',
            'manufacturer'
    ]
    def ATTRIBUTE_VALUES_FIELD = 'attributeValues'

    List<Product> searchProducts(Map searchParams){
        def query = "from Product pd where 1 = 1 "
        def queryParams = [:]

        SEARCH_FIELDS.each { searchField ->
            if( searchParams[searchField] ) {
                query += " and $searchField in (:${searchField}List)"
                queryParams.put("${searchField}List" as String, searchParams[searchField])
            }
        }

        if(searchParams[ATTRIBUTE_VALUES_FIELD] ){
            def attributeValuesMap = searchParams[ATTRIBUTE_VALUES_FIELD]
            attributeValuesMap.each { attributeId, attributeValueList ->
                query += " and 0 < (select count(*) from pd.attributeValues av where av.attribute.attributeId = '${attributeId}' and av.value in (:${attributeId}List))"
                queryParams.put("${attributeId}List" as String, attributeValueList)
            }
        }

        def products = Product.executeQuery(query, queryParams)

        return products
    }
}
