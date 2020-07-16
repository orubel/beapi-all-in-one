package net.nosegrind.apiframework

import org.springframework.http.HttpMethod


class RequestMap{


    String configAttribute
    HttpMethod httpMethod
    String url


    static constraints = {
        configAttribute blank: false
        httpMethod nullable: true
        url blank: false, unique: 'httpMethod'
    }

    static mapping = {
        //datasource 'user'
        //cache true
    }
}
