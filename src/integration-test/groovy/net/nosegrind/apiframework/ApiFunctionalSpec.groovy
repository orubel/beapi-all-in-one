package net.nosegrind.apiframework


import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.*
import spock.lang.*
import geb.spock.*
import grails.util.Holders
import static groovyx.net.http.ContentType.*
import grails.core.GrailsApplication
import static groovyx.net.http.Method.*
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import net.nosegrind.apiframework.ApiCacheService
import grails.util.Metadata
import grails.core.GrailsApplication

/**
 * See http://www.gebish.org/manual/current/ for more instructions
 */

@Integration
@Rollback
class ApiFunctionalSpec extends Specification {

    @Autowired
    ApplicationContext applicationContext

    GrailsApplication grailsApplication

    // ROLE_USER
    @Shared String token
    @Shared String testDomain
    @Shared List authorities = ['permitAll']
    @Shared String controller = 'person'
    @Shared String currentId
    @Shared String appVersion = "v${Metadata.current.getProperty(Metadata.APPLICATION_VERSION, String.class)}"

    void "login the test user"(){
        setup:"logging in"
            this.testDomain = grailsApplication.config.grails.serverURL
            String login = grailsApplication.config.test.login
            String password = grailsApplication.config.test.password
            String loginUri = grailsApplication.config.grails.plugin.springsecurity.rest.login.endpointUrl

            String url = "curl -H 'Content-Type: application/json' -X POST -d '{\"username\":\"${login}\",\"password\":\"${password}\"}' ${this.testDomain}${loginUri}"
            def proc = ['bash','-c',url].execute();
            proc.waitFor()
            def info = new JsonSlurper().parseText(proc.text)

        when:"set token"
            this.token = info.access_token

            info.authorities.each(){ it ->
                this.authorities.add('permitAll')
            }
        then:"has bearer token??"
            assert info.token_type == 'Bearer'
    }

    void "GET api call: [domain object]"() {
        setup:"api is called"
            String METHOD = "GET"
            LinkedHashMap info = [:]
            ApiCacheService apiCacheService = applicationContext.getBean("apiCacheService")
            LinkedHashMap cache = apiCacheService.getApiCache(this.controller)

            Integer version = cache['cacheversion']

            String action = 'show'

            String pkey = cache?."${version}"?."${action}".pkey[0]

            def proc = ["curl","-H","Content-Type: application/json","-H","Authorization: Bearer ${this.token}","--request","${METHOD}","${this.testDomain}/${this.appVersion}/${this.controller}/show?id=${this.currentId}"].execute();
            proc.waitFor()
            def outputStream = new StringBuffer()
            proc.waitForProcessOutput(outputStream, System.err)
            String output = outputStream.toString()

            def slurper = new JsonSlurper()
            slurper.parseText(output).each(){ k,v ->
                info[k] = v
            }
        when:"info is not null"
            assert info!=[:]
        then:"get user"
            cache?."${version}"?."${action}".returns.each(){ k,v ->
                assert this.authorities.contains(k)
            }
    }

    // Does not have right privileges; should always fail
    void "GET list api call: [list of domain objects]"() {
        setup:"api is called"
            String METHOD = "GET"
            LinkedHashMap info = [:]
            ApiCacheService apiCacheService = applicationContext.getBean("apiCacheService")
            LinkedHashMap cache = apiCacheService.getApiCache(this.controller)

            Integer version = cache['cacheversion']

            String action = 'show'

            String pkey = cache?."${version}"?."${action}".pkey[0]

            def proc = ["curl","-H","Content-Type: application/json","-H","Authorization: Bearer ${this.token}","--request","${METHOD}","${this.testDomain}/${this.appVersion}/${this.controller}/list"].execute();
            proc.waitFor()
            def outputStream = new StringBuffer()
            proc.waitForProcessOutput(outputStream, System.err)
            String output = outputStream.toString()

            def slurper = new JsonSlurper()
        when:"info is null"
            assert output.trim()=="Unauthorized Access attempted"
        then:"end test"
            assert true
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
            def proc = ["curl","-H","Content-Type: application/json","-H","Authorization: Bearer ${this.token}","--request","${METHOD}","${this.testDomain}/${this.appVersion}/${this.controller}/delete?id=${this.currentId}"].execute();
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

