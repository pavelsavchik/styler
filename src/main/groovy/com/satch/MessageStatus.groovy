package com.satch


enum MessageStatus {
    NEW(100),
    READ(400),
    DELETED(600)

    private final int value
    private static List list;

    MessageStatus(int value) { this.value = value }

    public String value() {
        return name();
    }

    public static List getList() {
        if(list != null) {
            return list
        }
        return buildList()
    }

    private static synchronized List buildList() {
        // List was not initialized...
        list = []
        values().each{
            list.add(it.value)
        }
        return list
    }
}