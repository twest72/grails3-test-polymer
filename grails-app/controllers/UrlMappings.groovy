class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/polymer"(view:"/polymer/polymer")

        "/"(view:"/index")
        "/wstest"(view:"/wstest")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
