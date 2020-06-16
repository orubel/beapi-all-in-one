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
import grails.converters.JSON



@Integration
@Rollback
class BatchFunctionalSpec extends Specification {

    @Autowired
    ApplicationContext applicationContext

    @Shared String token
    @Shared List authorities = ['permitAll']
    @Shared String controller = 'test'
    @Shared String testDomain
    @Shared List currentId = []
    @Shared String appVersion = "b${Metadata.current.getProperty(Metadata.APPLICATION_VERSION, String.class)}"
    @Shared String cert = '/home/owen/.ssh/'

    void "login and get token"(){
        setup:"logging in"
            this.testDomain = Holders.grailsApplication.config.environments.test.grails.serverURL
        
            String login = Holders.grailsApplication.config.root.login
            String password = Holders.grailsApplication.config.root.password
            String loginUri = Holders.grailsApplication.config.grails.plugin.springsecurity.rest.login.endpointUrl

            String url = "curl -H 'Content-Type: application/json' -X POST -d '{\"username\":\"${login}\",\"password\":\"${password}\"}' --cacert '${cert}' ${this.testDomain}${loginUri}"
            println(url)

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

    void "CREATE api call - Concatenate"() {
        setup:"api is called"
            String METHOD = "POST"

            ApiCacheService apiCacheService = applicationContext.getBean("apiCacheService")
            LinkedHashMap cache = apiCacheService.getApiCache(this.controller)
            Integer version = cache['cacheversion']


            String action = 'create'
            String data = "{'combine':true,'batch': [{'name': 'test1'},{'name': 'test2'},{'name': 'test3'},{'name': 'test4'},{'name': 'test5'},{'name': 'test6'}]}"
            def info
            def proc = ["curl", "-H", "Content-Type: application/json", "-H", "Authorization: Bearer ${this.token}", "--request", "${METHOD}", "-d", "${data}","--cacert","${cert}", "${this.testDomain}/${this.appVersion}/${this.controller}/${action}"].execute();
            proc.waitFor()
            def outputStream = new StringBuffer()
            proc.waitForProcessOutput(outputStream, System.err)
            String output = outputStream.toString()
            info = new JsonSlurper().parseText(output)

        when:"info is not null"
            assert info!=null
        then:"created user"
            info.each { it ->
                def out = new JsonSlurper().parseText(it)
                this.currentId.add(out.id)
            }
            assert this.currentId.size()==6
    }


    void "CREATE api call - No Concatenation"() {
        setup:"api is called"
            String METHOD = "POST"

            ApiCacheService apiCacheService = applicationContext.getBean("apiCacheService")
            LinkedHashMap cache = apiCacheService.getApiCache(this.controller)
            Integer version = cache['cacheversion']

            String action = 'create'
            String data = "{'batch': [{'name': 'test1'},{'name': 'test2'},{'name': 'test3'},{'name': 'test4'},{'name': 'test5'},{'name': 'test6'}]}"
            def info
            def proc = ["curl", "-H", "Content-Type: application/json", "-H", "Authorization: Bearer ${this.token}", "--request", "${METHOD}", "-d", "${data}","--cacert","${cert}", "${this.testDomain}/${this.appVersion}/${this.controller}/${action}"].execute();
            proc.waitFor()
            def outputStream = new StringBuffer()
            proc.waitForProcessOutput(outputStream, System.err)
            String output = outputStream.toString()
            info = new JsonSlurper().parseText(output)

        when:"info is not null"
            assert info!=null
        then:"created user"
            def out = info as LinkedHashMap
            this.currentId.add(out.id)
            assert this.currentId.size()==7
    }

    // create using mockdata
    void "GET api call"() {
        List localId = []
        setup:"api is called"
            String METHOD = "GET"

            ApiCacheService apiCacheService = applicationContext.getBean("apiCacheService")
            LinkedHashMap cache = apiCacheService.getApiCache(this.controller)
            Integer version = cache['cacheversion']
            String action = 'show'

            String data = "{\"combine\":true,\"batch\":["
            int inc = 1
            int size = this.currentId.size()
            this.currentId.each(){
                data += "{\"id\":${it}}"
                inc++
                if(inc<=size){
                    data += ","
                }
            }
            data += "]}"

            def info
            def proc = ["curl", "-H", "Content-Type: application/json", "-H", "Authorization: Bearer ${this.token}", "--request", "${METHOD}", "-d", "${data}","--cacert","${cert}", "${this.testDomain}/${this.appVersion}/${this.controller}/show"].execute();
            proc.waitFor()
            def outputStream = new StringBuffer()
            proc.waitForProcessOutput(outputStream, System.err)
            String output = outputStream.toString()

            info = new JsonSlurper().parseText(output)

        when:"info is not null"
            assert info!=null
        then:"get created user"
            info.each { it ->
                def out = new JsonSlurper().parseText(it)
                localId.add(out.id)
            }
            assert localId.size()==this.currentId.size()
    }

    // create using mockdata
    void "DELETE api call"() {
        setup:"api is called"
            String METHOD = "DELETE"

            ApiCacheService apiCacheService = applicationContext.getBean("apiCacheService")
            LinkedHashMap cache = apiCacheService.getApiCache(this.controller)
            Integer version = cache['cacheversion']
            String action = 'delete'

            String data = "{\"combine\":true,\"batch\":["
            int inc = 1
            int size = this.currentId.size()
            this.currentId.each(){
                data += "{\"id\":${it}}"
                inc++
                if(inc<=size){
                    data += ","
                }
            }
            data += "]}"

            def info
            def proc = ["curl", "-H", "Content-Type: application/json", "-H", "Authorization: Bearer ${this.token}", "--request", "${METHOD}", "-d", "${data}","--cacert","${cert}", "${this.testDomain}/${this.appVersion}/${this.controller}/delete"].execute();
            proc.waitFor()
            def outputStream = new StringBuffer()
            proc.waitForProcessOutput(outputStream, System.err)
            String output = outputStream.toString()

            info = new JsonSlurper().parseText(output)

        when:"info is not null"
            assert info!=null
        then:"delete created user"
            info.each { it ->
                def out = new JsonSlurper().parseText(it)
                this.currentId.add(out.id)
            }
            assert this.currentId.size()==14
    }




}

