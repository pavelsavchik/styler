package com.satch.domain

class Price {
    BigDecimal value = 0
    BigDecimal percent
    BigDecimal amount

    static constraints = {
        value nullable: false
        percent nullable: true, min: new BigDecimal(0), max: new BigDecimal(99)
        amount nullable: true, validator: {val, obj -> val == null || (val >= 0 && val < obj.value)}
    }
}
