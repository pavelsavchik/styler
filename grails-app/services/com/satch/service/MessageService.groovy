package com.satch.service

import com.satch.domain.Message
import com.satch.domain.Product
import com.satch.domain.Store
import com.satch.domain.User
import grails.transaction.Transactional

@Transactional
class MessageService {

    def springSecurityService

    List<Message> getUserMessages(User user, pagingParams) {
        return Message.executeQuery(
                "from Message where recipient = :user or sender = :user order by date desc",
                ['user': user], pagingParams)

    }

    def getCurrentUserUnreadMessageCount() {
        def user = springSecurityService.getCurrentUser()
        return Message.executeQuery(
                "select count(*) from Message where recipient = :user and wasRead=false",
                ['user': user]).first()
    }

    Message formRequestAvailabilityMessage(String text, long storeId, long productId) {
        def product = Product.get(productId)
        if (!product) {
            throw new Exception("Product with id ${productId} not found!")
        }
        def messageText = "Hello! Is product '${product.productId}.' available?"
        if (text) {
            messageText += "\nNote: " + text
        }
        //TODO: improve recipient logic
        def store = Store.get(storeId)
        if (!store) {
            throw new Exception("Store with id ${storeId} not found!")
        }

        def recipient = store?.sellers?.first()
        if (!recipient) {
            throw new Exception("Store has no sellers, so message can't be sent.")
        }

        return new Message(
                text: messageText,
                sender: springSecurityService.getCurrentUser(),
                recipient: recipient,
                date: new Date()
        )


    }
}
