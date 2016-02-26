import com.satch.domain.Product
import grails.converters.JSON

class BootStrap {

    def grailsApplication

    def demoDataInitializationService

    def init = { servletContext ->
        //TODO:Find better place for marshaller registration
        JSON.registerObjectMarshaller(Product) { Product product ->
            if (product) {
                return [
                        id                 : product.id,
                        productId          : product.productId,
                        catalog            : product.catalog ? [id: product.catalog.id] : null,
                        classification     : product.classification ? [id: product.classification.id] : null,
                        classificationGroup: product.classificationGroup ? [id: product.classificationGroup.id] : null,
                        isSearchable       : product.isSearchable,
                        price              : product.price ? [value: product.price.value] : null,
                        manufacturer       : product.manufacturer,
                        longDesc           : product.longDesc,
                        shortDesc          : product.shortDesc,
                        attributeValues    : product.attributeValues?.collect { attributeValue ->
                            [
                                    attribute: [
                                            attributeId: attributeValue.attribute.attributeId,
                                            description: attributeValue.attribute.description
                                    ],
                                    value    : attributeValue.value
                            ]
                        }
                ]
            }
        }

        //create workarea directory if not exists
        File catalinaBase = new File(System.getProperty("catalina.base")).getAbsoluteFile()
        File workareaDir = new File(catalinaBase, "webapps/workarea")
        if (!workareaDir.exists()) {
            workareaDir.mkdir()
        }

        if (grailsApplication.config.com.satch.populatedb != 'update') {
            demoDataInitializationService.initDemoData(50, 5)
        }
    }

    def destroy = {
    }

}
