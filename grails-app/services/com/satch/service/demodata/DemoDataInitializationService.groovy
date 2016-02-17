package com.satch.demodata

import com.satch.AttributeType
import com.satch.domain.Attribute
import com.satch.domain.AttributeValue
import com.satch.domain.Catalog
import com.satch.domain.Classification
import com.satch.domain.ClassificationGroup
import com.satch.domain.Price
import com.satch.domain.Product
import com.satch.domain.Role
import com.satch.domain.Store
import com.satch.domain.Supplier
import com.satch.domain.User
import com.satch.domain.UserRole
import grails.transaction.Transactional

@Transactional
class DemoDataInitializationService {

    def dataGenerationService
    def fileManagerService

    List<Supplier> suppliers = []
    List<Attribute> attributes = []
    List<Classification> classifications = []
    List<ClassificationGroup> classificationGroups = []
    List<Store> stores = []
    List<Catalog> catalogs = []

    Classification basicClassification
    ClassificationGroup basicClassificationGroup
    List<Attribute> basicAttributes = []

    Classification systemClassification

    def random = new Random()

    def initDemoData(int productCount, int catalogCount){
        initSystemData()
        initBasicData(catalogCount)
        initProducts(productCount)
        initUserData()
    }

    def initUserData(){
        def role1 = new Role("ROLE_ADMIN").save()
        def role2 = new Role("ROLE_USER").save()
        def role3 = new Role("ROLE_SELLER").save()
        def role4 = new Role("ROLE_MANAGER").save()
        def user1 = new User("admin", "admin").save()
        def user2 = new User("buyer", "buyer").save()
        def user3 = new User("seller", "seller").save()
        def user4 = new User("manager", "manager").save()

        user3.supplier = Supplier.findBySupplierId("nike")
        user3.save()
        user4.supplier = Supplier.findBySupplierId("puma")
        user4.save()

        new UserRole(user1, role1).save()
        new UserRole(user2, role2).save()
        new UserRole(user3, role3).save()
        new UserRole(user4, role4).save()
    }

    def initSystemData(){
        systemClassification = new Classification(classificationId: 'system', description: 'Do not remove!').save(failOnError: true)
        def supplierClassificationGroup = new ClassificationGroup(classificationGroupId: 'supplier', classification: systemClassification, description: 'Do not remove!').save(failOnError: true)

        def attrLogo = new Attribute(attributeId: 'logo', description: 'Логотип', type: AttributeType.STRING).save(failOnError: true)
        def attrDescription = new Attribute(attributeId: 'description', description: 'Описание', type: AttributeType.STRING).save(failOnError: true)

        systemClassification.addToAttributes(attrLogo).save(failOnError: true)
        systemClassification.addToAttributes(attrDescription).save(failOnError: true)

        supplierClassificationGroup.addToAttributes(attrLogo).save(failOnError: true)
        supplierClassificationGroup.addToAttributes(attrDescription).save(failOnError: true)
    }

    def initBasicData(int catalogCount){
        initSuppliers()
        initClassifications()
        initStores()
        initCatalogs(catalogCount)
    }

    def initSuppliers(){
        def supplierClassificationGroup = ClassificationGroup.findByClassificationGroupId('supplier')

        def sup1 = new Supplier(supplierId: 'nike', supplierName: 'Nike', classification: systemClassification, classificationGroup: supplierClassificationGroup).save(failOnError: true)
        def sup2 = new Supplier(supplierId: 'puma', supplierName: 'Puma', classification: systemClassification, classificationGroup: supplierClassificationGroup).save(failOnError: true)
        def sup3 = new Supplier(supplierId: 'megatop', supplierName: 'Мегатоп', classification: systemClassification, classificationGroup: supplierClassificationGroup).save(failOnError: true)

//        TODO:Implement LOGO assigning
//        String path = Thread.currentThread().getContextClassLoader().getResource("assets").getPath()
//        def logo = {String filePath, Supplier sup -> fileManagerService.getFilePath(fileManagerService.copyFileToSupplierDir(new File(filePath), sup, "logo"))}
//        sup1.addToAttributeValues(new AttributeValue(attribute: Attribute.findByAttributeId('logo'), value: logo(path + '/suppliers/nike-logo.jpg', sup1)).save(failOnError: true))
//        sup2.addToAttributeValues(new AttributeValue(attribute: Attribute.findByAttributeId('logo'), value: logo(path + '/suppliers/puma-logo.png', sup2)).save(failOnError: true))
//        sup3.addToAttributeValues(new AttributeValue(attribute: Attribute.findByAttributeId('logo'), value: logo(path + '/suppliers/megatop_logo.jpg', sup3)).save(failOnError: true))
//
//        sup1.addToAttributeValues(new AttributeValue(attribute: Attribute.findByAttributeId('description'), value: 'Просто сделай это.').save(failOnError: true))
//        sup2.addToAttributeValues(new AttributeValue(attribute: Attribute.findByAttributeId('description'), value: 'Ощути лёгкость вместе с нами.').save(failOnError: true))
//        sup3.addToAttributeValues(new AttributeValue(attribute: Attribute.findByAttributeId('description'), value: 'Обувь большого города.').save(failOnError: true))

        suppliers.addAll([sup1, sup2, sup3])
    }

    def initProducts(int productCount){

        productCount.times {
            def classificationGroup = classificationGroups.get(random.nextInt(classificationGroups.size()))
            def catalog = catalogs.get(random.nextInt(catalogs.size()))

            def product = new Product(
                    productId: dataGenerationService.generateProductId(),
                    shortDesc: "lalala",
                    longDesc: dataGenerationService.generateProductLongDesc(classificationGroup),
                    price: dataGenerationService.generateProductPrice(),
                    catalog: catalog,
                    classification: classificationGroup.classification,
                    classificationGroup: classificationGroup,
                    isSearchable: true,
                    quantity: random.nextInt(1000),
                    manufacturer: dataGenerationService.generateProductManufacturer(),
            ).save(failOnError: true)

//            TODO: Implement
//            product.addToAttributeValues(
//                    new AttributeValue(attribute: basicAttributes[0], value: dataGenerationService.generateProductImagePath(classificationGroup, catalog.store.supplier)).
//                            save(failOnError: true)
//            ).save(failOnError: true)
//
//            4.times{ i ->
//                if(random.nextBoolean())
//                    product.addToAttributeValues(
//                        new AttributeValue(attribute: basicAttributes[i + 1], value: dataGenerationService.generateProductImagePath(classificationGroup, catalog.store.supplier)
//                        ).save(failOnError: true)
//                    ).save(failOnError: true)
//            }

            dataGenerationService.generateAttributeValues(product)

            product.shortDesc = dataGenerationService.generateProductShortDesc(product)
            product.save(failOnError: true)
        }
    }

    def initClassifications(){
        initBasicClassification()

        classifications << new Classification(classificationId: 'shoes', description: 'Обувь').save(failOnError: true)
        classifications << new Classification(classificationId: 'clothing', description: 'Одежда').save(failOnError: true)
        classifications << new Classification(classificationId: 'accessories', description: 'Аксессуары').save(failOnError: true)

        classificationGroups << new ClassificationGroup(classification: classifications[0], classificationGroupId: 'sneakers', description: 'Кроссовки').save(failOnError: true)
        classificationGroups << new ClassificationGroup(classification: classifications[0], classificationGroupId: 'gymshoes', description: 'Кеды').save(failOnError: true)

        classificationGroups << new ClassificationGroup(classification: classifications[1], classificationGroupId: 'shirts', description: 'Рубашки').save(failOnError: true)
        classificationGroups << new ClassificationGroup(classification: classifications[1], classificationGroupId: 'jeans', description: 'Джинсы').save(failOnError: true)

        classificationGroups << new ClassificationGroup(classification: classifications[2], classificationGroupId: 'glasses', description: 'Очки').save(failOnError: true)

        classificationGroups << new ClassificationGroup(classification: classifications[1], classificationGroupId: 'skinny-jeans', description: 'Узкие джинсы', parentClassificationGroup: classificationGroups[3]).save(failOnError: true)
        classificationGroups << new ClassificationGroup(classification: classifications[1], classificationGroupId: 'normal-jeans', description: 'Обычные джинсы', parentClassificationGroup: classificationGroups[3]).save(failOnError: true)

        def attributeSize = new Attribute(attributeId: 'size', description: 'Размер', type: AttributeType.STRING, isMultivalue: true).save(failOnError: true)
        def attributeColor = new Attribute(attributeId: 'color', description: 'Цвет', type: AttributeType.STRING, isMultivalue: true).save(failOnError: true)
        def attributeMaterial = new Attribute(attributeId: 'material', description: 'Материал', type: AttributeType.STRING, isMultivalue: true).save(failOnError: true)

        classifications[0].addToAttributes(attributeSize).addToAttributes(attributeColor).addToAttributes(attributeMaterial).save(failOnError: true)
        classifications[1].addToAttributes(attributeSize).addToAttributes(attributeColor).addToAttributes(attributeMaterial).save(failOnError: true)
        classifications[2].addToAttributes(attributeSize).addToAttributes(attributeColor).addToAttributes(attributeMaterial).save(failOnError: true)

        classificationGroups.each {it.addToAttributes(attributeSize).addToAttributes(attributeColor).addToAttributes(attributeMaterial).save(failOnError: true)}
    }

    def initBasicClassification(){
        def basicClassification = new Classification(classificationId: 'basic', description: 'basic').save(failOnError: true)
        def basicClassificationGroup = new ClassificationGroup(classificationGroupId: 'basic', classification: basicClassification, description: 'basic').save(failOnError: true)

        def attrImage1 = new Attribute(attributeId: 'image1', description: 'Изображение 1', type: AttributeType.STRING).save(failOnError: true)
        def attrImage2 = new Attribute(attributeId: 'image2', description: 'Изображение 2', type: AttributeType.STRING).save(failOnError: true)
        def attrImage3 = new Attribute(attributeId: 'image3', description: 'Изображение 3', type: AttributeType.STRING).save(failOnError: true)
        def attrImage4 = new Attribute(attributeId: 'image4', description: 'Изображение 4', type: AttributeType.STRING).save(failOnError: true)
        def attrImage5 = new Attribute(attributeId: 'image5', description: 'Изображение 5', type: AttributeType.STRING).save(failOnError: true)

        basicClassification
                .addToAttributes(attrImage1)
                .addToAttributes(attrImage2)
                .addToAttributes(attrImage3)
                .addToAttributes(attrImage4)
                .addToAttributes(attrImage5)
                .save(failOnError: true)

        basicClassificationGroup
                .addToAttributes(attrImage1)
                .addToAttributes(attrImage2)
                .addToAttributes(attrImage3)
                .addToAttributes(attrImage4)
                .addToAttributes(attrImage5)
                .save(failOnError: true)

        basicAttributes << attrImage1 << attrImage2 << attrImage3 << attrImage4 << attrImage5
    }

    def initStores(){
//        def catalogsCount = catalogs.size()
//        def generateCatalogsList = {
//            def catalogList = []
//            (catalogsCount/5).times {
//                def catIndex = new Random().nextInt(catalogs.size())
//                catalogList << catalogs[catIndex]
//                catalogs.remove(catIndex)
//            }
//            return catalogList
//        }

        stores << new Store(storeId: 'nike1', name: 'Nike', description: 'Just do it.', address: dataGenerationService.generateAddress(), catalogs: [], supplier: suppliers[0]).save(failOnError: true)
        stores <<  new Store(storeId: 'nike2', name: 'Nike', description: 'Do it just?', address: dataGenerationService.generateAddress(), catalogs: [], supplier: suppliers[0]).save(failOnError: true)
        stores <<  new Store(storeId: 'nike3', name: 'Nike', description: 'It just do.', address: dataGenerationService.generateAddress(), catalogs: [], supplier: suppliers[1]).save(failOnError: true)

        stores <<  new Store(storeId: 'nike4', name: 'Nike', description: 'Just do it.(store desc)', address: dataGenerationService.generateAddress(), catalogs: [], supplier: suppliers[2]).save(failOnError: true)
        stores <<  new Store(storeId: 'nike5', name: 'Nike', description: 'Just do it.(store desc)', address: dataGenerationService.generateAddress(), catalogs: [], supplier: suppliers[2]).save(failOnError: true)
    }

    def initCatalogs(int catalogCount){
        catalogCount.times {
            def store = stores.get(random.nextInt(stores.size()))
            def catalog =  new Catalog(
                catalogId: dataGenerationService.generateCatalogId(),
                description: dataGenerationService.generateCatalogDescription(),
                store: store
            ).save(failOnError: true)
            catalogs << catalog
            store.addToCatalogs(catalog).save(failOnError: true)
        }
    }
}
