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

    def PAGING_FIELDS = [
            'max',
            'offset'
    ]

    def SORT_FIELDS = [
            'price' : 'price.value'
    ]

    def ATTRIBUTE_VALUES_FIELD = 'attributeValues'

    List<Product> searchProducts(Map searchParams){
        def query = "from Product pd where 1 = 1 "
        def queryParams = [:]
        def pagingParams = [:]

        SEARCH_FIELDS.each { searchField ->
            if( searchParams[searchField] ) {
                query += " and $searchField in (:${searchField}List)"
                queryParams.put("${searchField}List" as String, searchParams[searchField])
            }
        }

        def minPrice = searchParams.minPrice ? new java.math.BigDecimal(searchParams.minPrice) : null
        def maxPrice = searchParams.maxPrice ? new java.math.BigDecimal(searchParams.maxPrice) : null

        if(maxPrice){
            query += " and price.value <= :maxPrice "
            queryParams.put('maxPrice', maxPrice)
        }
        if(minPrice){
            query += " and price.value >= :minPrice "
            queryParams.put('minPrice', minPrice)
        }

        if(searchParams[ATTRIBUTE_VALUES_FIELD] ){
            def attributeValuesMap = searchParams[ATTRIBUTE_VALUES_FIELD]
            attributeValuesMap.each { attributeId, attributeValueList ->
                query += " and 0 < (select count(*) from pd.attributeValues av where av.attribute.attributeId = '${attributeId}' and av.value in (:${attributeId}List))"
                queryParams.put("${attributeId}List" as String, attributeValueList)
            }
        }

        def sortField = searchParams['sort']
        if(sortField && SORT_FIELDS.containsKey(sortField)){
            query += " order by ${SORT_FIELDS.get(sortField)} "
            if(searchParams['order'] == 'desc'){
                query += ' desc '
            }
        }

        PAGING_FIELDS.each { pagingField ->
            if(searchParams[pagingField]){
                pagingParams << [(pagingField):searchParams[pagingField]]
            }
        }

        def products = Product.executeQuery(query, queryParams, pagingParams)

        return products
    }
}
