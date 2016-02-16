package com.satch.domain

class Message {
    String text
    User sender
    User recipient
    boolean wasRead
    Date date

    static constraints = {
        text maxSize: 1024 * 16
        sender nullable: false
        recipient nullable: false
    }
}
