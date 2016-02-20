import com.satch.domain.Product
import grails.converters.JSON

class BootStrap {

    def grailsApplication

    def demoDataInitializationService

    def init = { servletContext ->
        //TODO:Find better place for marshaller registration
        JSON.registerObjectMarshaller(Product) { Product product ->
            return [
                    productId : product.productId,
                    catalog : product.catalog.catalogId,
                    classification : product.classificationId,
                    classificationGroup : product.classificationGroupId,
                    isSearchable : product.isSearchable,
                    price : product.price.value,
                    manufacturer : product.manufacturer,
                    longDesc : product.longDesc,
                    shortDesc : product.shortDesc,
                    attributeValues : product.attributeValues.collectEntries { attributeValue ->
                        [(attributeValue.attribute.attributeId) : (attributeValue.value)]
                    }
            ]
        }

        //create workarea directory if not exists
        File catalinaBase = new File( System.getProperty( "catalina.base" ) ).getAbsoluteFile()
        File workareaDir = new File( catalinaBase, "webapps/workarea" )
        if(!workareaDir.exists()){
            workareaDir.mkdir()
        }

        if(grailsApplication.config.com.satch.populatedb != 'update') {
            demoDataInitializationService.initDemoData(50, 5)
        }
    }

    def destroy = {
    }

}
