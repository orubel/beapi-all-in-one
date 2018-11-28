package net.nosegrind.apiframework


import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.*
import spock.lang.*
import geb.spock.*
import grails.util.Holders
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import net.nosegrind.apiframework.ApiCacheService
import grails.util.Metadata
import org.grails.web.json.JSONObject
import groovy.json.JsonSlurperClassic

/**
 * See http://www.gebish.org/manual/current/ for more instructions
 */

@Integration
@Rollback
class BatchFunctionalSpec extends Specification {

    @Autowired
    ApplicationContext applicationContext

    @Shared String token
    @Shared List authorities = ['permitAll']
    @Shared String controller = 'test'
    @Shared String testDomain = 'http://localhost:8080'
    @Shared List currentId = []
    @Shared String appVersion = "b${Metadata.current.getProperty(Metadata.APPLICATION_VERSION, String.class)}"

    void "login and get token"(){
        setup:"logging in"
            String login = Holders.grailsApplication.config.root.login
            String password = Holders.grailsApplication.config.root.password
            String loginUri = Holders.grailsApplication.config.grails.plugin.springsecurity.rest.login.endpointUrl

            String url = "curl -H 'Content-Type: application/json' -X POST -d '{\"username\":\"${login}\",\"password\":\"${password}\"}' ${this.testDomain}${loginUri}"
            def proc = ['bash','-c',url].execute();
            proc.waitFor()
            def info = new JsonSlurper().parseText(proc.text)

        when:"set token"
            this.token = info.access_token
            info.authorities.each(){ it ->
                this.authorities.add(it)
            }
        then:"has bearer token"
            assert info.token_type == 'Bearer'
    }

    // create using mockdata
    void "CREATE api call - Concatenate"() {
        setup:"api is called"
            String METHOD = "POST"

            ApiCacheService apiCacheService = applicationContext.getBean("apiCacheService")
            LinkedHashMap cache = apiCacheService.getApiCache(this.controller)
            Integer version = cache['cacheversion']

            String action = 'create'
            String data = "{'combine':true,'batch': [{'name': 'test1'},{'name': 'test2'},{'name': 'test3'},{'name': 'test4'},{'name': 'test5'},{'name': 'test6'}]}"
            def info
            net.nosegrind.apiframework.Person.withTransaction { status ->
                def proc = ["curl", "-H", "Content-Type: application/json", "-H", "Authorization: Bearer ${this.token}", "--request", "${METHOD}", "-d", "${data}", "${this.testDomain}/${this.appVersion}/${this.controller}/${action}"].execute();

                proc.waitFor()
                def outputStream = new StringBuffer()
                proc.waitForProcessOutput(outputStream, System.err)
                String output = outputStream.toString()
                info = new JsonSlurper().parseText(output)
            }
        when:"info is not null"
            assert info!=null
        then:"created user"
            info.each { it ->
                def out = it as LinkedHashMap
                this.currentId.add(out.id)
            }
            assert this.currentId.size()==6
    }


    // create using mockdata
    /*
    void "DELETE api call: [map]"() {
        setup:"api is called"
            String METHOD = "DELETE"
            LinkedHashMap info = [:]
            ApiCacheService apiCacheService = applicationContext.getBean("apiCacheService")
            LinkedHashMap cache = apiCacheService.getApiCache(this.controller)

            Integer version = cache['cacheversion']

            String action = 'delete'
            String data = "{'batch': [{'id': 'test1'},{'id': 'test2'},{'id': 'test3'},{'id': 'test4'},{'id': 'test5'},{'id': 'test6'}]}"
            def proc = ["curl","-H","Content-Type: application/json","-H","Authorization: Bearer ${this.token}","--request","${METHOD}","${this.testDomain}/${this.appVersion}/${this.controller}/${action}"].execute();
            proc.waitFor()
            def outputStream = new StringBuffer()
            proc.waitForProcessOutput(outputStream, System.err)
            String output = outputStream.toString()
            def slurper = new JsonSlurper()
            slurper.parseText(output).each(){ k,v ->
                info[k] = v
            }
        when:"info is not null"
            assert info!=null
        then:"delete created user"
            def id
            cache?."${version}"?."${action}".returns.each(){ k,v ->
                v.each(){ it ->
                    if(it.keyType=='PRIMARY'){
                        id = info."${it.name}"
                    }

                }
            }
            assert this.currentId == id
    }
    */



}

