package com.satch.controllers

import com.satch.service.ClassificationService
import grails.converters.JSON

class ClassificationController {
    static allowedMethods = [list: "GET"]

    ClassificationService classificationService

    def list() {
        List classifications = classificationService.classifications()
        render classifications as JSON
    }
}
