package com.satch.service

import com.satch.domain.Message
import com.satch.domain.User
import grails.transaction.Transactional

@Transactional
class MessageService {

    def springSecurityService

    List<Message> getUserMessages(User user) {
        return Message.executeQuery(
                "from Message where recipient = :user or sender = :user order by date desc",
                ['user': user])

    }

    def getCurrentUserUnreadMessageCount() {
        def user = springSecurityService.getCurrentUser()
        return Message.executeQuery(
                "select count(*) from Message where recipient = :user and wasRead=false",
                ['user': user]).first()
    }
}
