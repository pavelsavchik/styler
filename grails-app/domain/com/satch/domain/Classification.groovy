package com.satch.domain

class Classification {
    String classificationId
    String description
    OptionList optionList

    static hasMany = [attributes: Attribute, classificationGroups: ClassificationGroup]

    static constraints = {
        classificationId blank: false, unique: true
        description blank: false
        //TODO: maybe remove
        optionList nullable: true
    }
}
