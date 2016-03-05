package com.satch.service

import com.satch.domain.Message
import com.satch.domain.Product
import com.satch.domain.Store
import com.satch.domain.User
import grails.transaction.Transactional
import grails.web.servlet.mvc.GrailsParameterMap

@Transactional
class MessageService {

    def springSecurityService

    def SEARCH_FIELDS = [
            'sender.id'       : 'Long',
            'recipient.id'    : 'Long',
            'date'            : '',
            'status'          : 'Integer',
            'product.id'      : 'Long',
            'parentMessage.id': 'Long'
    ]

    def PAGING_FIELDS = [
            'max',
            'offset'
    ]

    List<Message> search(Map searchParams) {
        def query = "from Message m where 1 = 1 "
        def queryParams = [:]
        def pagingParams = [:]

        //Custom logic for user messages restriction
        if (!searchParams.'user') {
            throw new Exception("user is wrong!")
        }
        query += " and (recipient = :user or sender = :user) "
        queryParams.put("user", searchParams.'user')

        SEARCH_FIELDS.each { searchField, fieldType ->
            if (searchParams[searchField]) {
                query += " and m.$searchField in (:${searchField.replace('.', '')}List)"
                def valueList = searchParams.list(searchField)
                if (fieldType)
                    valueList = valueList.collect { it."to$fieldType"() }
                queryParams.put("${searchField.replace('.', '')}List" as String, valueList)
            }
        }

        PAGING_FIELDS.each { pagingField ->
            if (searchParams[pagingField]) {
                pagingParams << [(pagingField): searchParams[pagingField]]
            }
        }

        def messages = Message.executeQuery(query, queryParams, pagingParams)

        return messages
    }
}
