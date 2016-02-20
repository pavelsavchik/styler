package com.satch.demodata

import com.satch.domain.Address
import com.satch.domain.Attribute
import com.satch.domain.AttributeValue
import com.satch.domain.Catalog
import com.satch.domain.ClassificationGroup
import com.satch.domain.Phone
import com.satch.domain.Price
import com.satch.domain.Product
import com.satch.domain.Supplier
import com.satch.service.file.FileManagerService
import grails.transaction.Transactional
import groovy.io.FileType

@Transactional
class DataGenerationService {

    FileManagerService fileManagerService

    def CITIES = ['Минск', 'Бобруйск']
    def STREETS = ['Фабрициуса', 'Московская', 'Ленина', 'Октябрьская', 'Зыбицкая', 'Красная']

    def catalogDescWords = ['amazing', 'shoes', 'clothes', '&', 'winter', 'summer', 'autumn', 'spring', 'style', '.']

    def productShortDescWords = ['Air', 'Top', 'Low', 'Mid', 'Easy', 'High', 'Light', 'Rap', 'Street']

    def colors = ['красный', 'белый', 'синий', 'зеленый', 'желтый', 'черный', 'розовый', 'оранжевый']

    def manufacturers = ['Nike', 'Puma', 'Reebok', 'Adidas', 'Umbro', 'Свитанак', 'Vans']

    def materials = ['кожа', 'кожзам', 'хлопок', 'резина', 'полиэстэр']

    def sizes = ['42', '43', '44', '45', '41', '40', '39', '38']

    def applicationContext

    def random = new Random()

    def generateAddress(){
        new Address(
                city: CITIES.get(random.nextInt(CITIES.size())),
                street: STREETS.get(random.nextInt(STREETS.size())),
                home: random.nextInt(100),
                phones: generatePhonesList(random.nextInt(3) + 1)
        ).save(failOnError: true)
    }

    def generatePhonesList(int count){
        def generateMainPart = {
            def numberStr = ""
            7.times {
                numberStr = numberStr + random.nextInt(10).toString()
            }
            return numberStr
        }

        def phonesList = []
        count.times {
            phonesList << new Phone(
                    number: '+375' + ['29', '44', '33'].get(random.nextInt(3)) + generateMainPart()
            ).save(failOnError: true)
        }

        return phonesList
    }

    def generateCatalogId(){
        def CATALOG_ID_LENGTH = 10

        def generator = { String alphabet, int n ->
            random.with {
                (1..n).collect { alphabet[ nextInt( alphabet.length() ) ] }.join()
            }
        }

        def catalogId = ""
        while(!catalogId || Catalog.findByCatalogId(catalogId))
            catalogId = generator((('a'..'z')+('0'..'9')).join(), CATALOG_ID_LENGTH)

        return catalogId
    }

    def generateCatalogDescription(){
        def desc = ""
        (random.nextInt(5) + 1).times {
            desc += catalogDescWords.get(random.nextInt(catalogDescWords.size())) + ' '
        }
        return desc
    }

    def generateProductId(){
        def PRODUCT_ID_LENGTH = 10

        def generator = { String alphabet, int n ->
            random.with {
                (1..n).collect { alphabet[ nextInt( alphabet.length() ) ] }.join()
            }
        }

        def productId = ""
        while(!productId || Product.findByProductId(productId))
            productId = generator((('a'..'z')+('0'..'9')).join(), PRODUCT_ID_LENGTH)

        return productId
    }

    def generateProductShortDesc(Product product){
        def desc = ""
        def manufacturer = product.manufacturer
        def color = product.attributeValues.find {it.attribute.attributeId == 'color'}?.value
        def size = product.attributeValues.find {it.attribute.attributeId == 'size'}?.value
        def material = product.attributeValues.find {it.attribute.attributeId == 'material'}?.value

        def generator = { String alphabet, int n ->
            random.with {
                (1..n).collect { alphabet[ nextInt( alphabet.length() ) ] }.join()
            }
        }

        desc += manufacturer.substring(0,3) + ' '
        desc += color.substring(0,3) + ' '
        desc += material.substring(0,3) + ' '
        desc += size + ' '
//        desc += generator((('a'..'z')+('0'..'9')).join(), 3) + ' '
        desc += product.classificationGroup.classificationGroupId.substring(0,3)

        return desc
    }

    def generateProductLongDesc(ClassificationGroup classificationGroup){
        return 'long desc'
    }

    def generateProductManufacturer(){
        return manufacturers.get(random.nextInt(manufacturers.size()))
    }

    def generateProductPrice(){
        new Price(value: random.nextInt(1000).toBigDecimal() + 10, percent: random.nextInt(99), amount: null).save(failOnError: true)
    }

    def generateAttributeValues(Product product){
//        product.classificationGroup.attributes.each{
//            product.addToAttributeValues(new AttributeValue(attribute: it, value: 'abcdef').save(failOnError: true)).save(failOnError: true)
//        }
        def color = colors.get(random.nextInt(colors.size()))
        product.addToAttributeValues(new AttributeValue(attribute: Attribute.findByAttributeId('color'), value: color).save(failOnError: true)).save(failOnError: true)

        def material = materials.get(random.nextInt(materials.size()))
        product.addToAttributeValues(new AttributeValue(attribute: Attribute.findByAttributeId('material'), value: material).save(failOnError: true)).save(failOnError: true)

        def size = sizes.get(random.nextInt(sizes.size()))
        product.addToAttributeValues(new AttributeValue(attribute: Attribute.findByAttributeId('size'), value: size).save(failOnError: true)).save(failOnError: true)
    }

    def generateProductImagePath(def classificationGroup, Supplier supplier){
        String path = Thread.currentThread().getContextClassLoader().getResource("WEB-INF/images/${classificationGroup.classificationGroupId}").path
        def imageFolder = new File(path + "/")
        def imagesList = []
        imageFolder.eachFileRecurse (FileType.FILES) { file ->
            imagesList << file
        }
        //substring for cut web-inf
        def file = imagesList.get(random.nextInt(imagesList.size()))
        def destFile = fileManagerService.copyFileToSupplierDir(file, supplier, "images/${classificationGroup.classificationGroupId}/" )
        return fileManagerService.getFilePath(destFile)
    }
}
