import com.satch.domain.Address
import com.satch.domain.Message
import com.satch.domain.Product
import com.satch.domain.Store
import grails.converters.JSON

class BootStrap {

    def grailsApplication

    def demoDataInitializationService

    def init = { servletContext ->
        //TODO:Find better place for marshaller registration
        registerMarshaller()

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

    def registerMarshaller = {
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
            } else {
                return null
            }
        }


        JSON.registerObjectMarshaller(Address) { Address address ->
            if (address) {
                return [
                        id    : address.id,
                        home  : address.home,
                        city  : address.city,
                        street: address.street,
                        phones: address.phones.collect { [number: it.number] }
                ]
            } else {
                return null
            }
        }

        JSON.registerObjectMarshaller(Store) { Store store ->
            if (store) {
                return [
                        id             : store.id,
                        storeId        : store.storeId,
                        name           : store.name,
                        description    : store.description,
                        address        : store.address,
                        attributeValues: store.attributeValues?.collect { attributeValue ->
                            [
                                    attribute: [
                                            attributeId: attributeValue.attribute.attributeId,
                                            description: attributeValue.attribute.description
                                    ],
                                    value    : attributeValue.value
                            ]
                        },
                        catalogs       : store.catalogs?.collect { catalog ->
                            [
                                    id         : catalog.id,
                                    description: catalog.description,
                                    catalogId  : catalog.catalogId
                            ]
                        },
                        //TODO: Implement stocks
                        stocks         : []
                ]
            } else {
                return null
            }
        }

        JSON.registerObjectMarshaller(Message) { Message message ->
            if (message) {
                def messageJSON =  [
                        id       : message.id,
                        recipient: [id: message.recipient.id],
                        sender   : [id: message.sender.id],
                        date     : message.date,
                        text     : message.text,
                        status   : message.status
                ]
                if(message.product){
                    messageJSON.put('product', [id: message.product.id])
                }
                if(message.parentMessage){
                    messageJSON.put('parentMessage', [id: message.parentMessage.id])
                }
                return messageJSON
            } else {
                return null
            }
        }


    }

}
