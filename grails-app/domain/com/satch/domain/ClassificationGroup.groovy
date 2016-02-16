package com.satch.domain

class ClassificationGroup {
    String classificationGroupId
    String description

    static hasMany = [childClassificationGroups: ClassificationGroup, attributes: Attribute]
    static belongsTo = [parentClassificationGroup: ClassificationGroup, classification: Classification]

    static constraints = {
        description blank: false
        classificationGroupId blank: false, unique: 'classification'
        classification nullable: false
        parentClassificationGroup nullable: true
        childClassificationGroups nullable: true
    }
}
