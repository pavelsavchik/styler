package satch

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: "ui", action: 'index')
        "500"(view:'/error')
        "404"(view:'/notFound')
        "/applicationInfo"(view:'/index')

        //Product REST
        "/products"(controller: 'product', action: 'save', method: 'POST')
        "/products/$catalogId/$productId"(controller: 'product', action: 'update', method: 'PUT')
        "/products/$catalogId/$productId"(controller: 'product', action: 'delete', method: 'DELETE')
        "/products/$catalogId/$productId"(controller: 'product', action: 'show', method: 'GET')
        "/products/$catalogId/$productId/$action/"(controller: 'product', method: 'GET')

        "/products/$id"(controller: 'product', action: 'show', method: 'GET')
        "/products"(controller: 'product', action: 'save', method: 'POST')
        "/products"(controller: 'product', action: 'list', method: 'GET')
        "/products/$id"(controller: 'product', action: 'update', method: 'PUT')
        "/products/$id"(controller: 'product', action: 'delete', method: 'DELETE')

        "/stores/$id"(controller: 'store', action: 'show', method: 'GET')
        "/stores"(controller: 'store', action: 'save', method: 'POST')
        "/stores"(controller: 'store', action: 'list', method: 'GET')
        "/stores/$id"(controller: 'store', action: 'update', method: 'PUT')
        "/stores/$id"(controller: 'store', action: 'delete', method: 'DELETE')

        "/messages/$id"(controller: 'message', action: 'show', method: 'GET')
        "/messages/"(controller: 'message', action: 'list', method: 'GET')
        "/messages/"(controller: 'message', action: 'save', method: 'POST')
        "/messages/$id"(controller: 'message', action: 'update', method: 'PUT')
        "/messages/$id"(controller: 'message', action: 'delete', method: 'DELETE')

//        "/messages/requestAvailability"(controller: 'message', action: 'requestAvailability', method: 'POST')
//        "/messages/count"(controller: 'message', action: 'getCurrentUserMessageCount', method: 'GET')

        //Classification REST
        "/classifications"(controller: 'classification', action: 'list', method: 'GET')
    }
}
