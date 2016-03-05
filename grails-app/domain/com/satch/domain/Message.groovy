package com.satch.domain

import com.satch.MessageStatus

class Message {
    String text
    User sender
    User recipient
    int status
    Product product
    Date date
    Message parentMessage

    static constraints = {
        text maxSize: 1024 * 16
        sender nullable: false
        recipient nullable: false
        parentMessage nullable: true
        product nullable: true
        status inList: MessageStatus.list
    }
}
