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
import grails.gorm.transactions.Transactional
import net.nosegrind.apiframework.Person



@Integration
@Rollback
class ApiFunctionalSpec extends Specification {

    @Autowired
    ApplicationContext applicationContext
    def grailsApplication
    @Shared String token
    @Shared String guestToken
    @Shared List authorities = ['permitAll']
    @Shared String testDomain
    @Shared String currentId
    @Shared String guestId
    @Shared String appVersion = "v${Metadata.current.getProperty(Metadata.APPLICATION_VERSION, String.class)}"
    @Shared String guestdata = "{'username': 'apitest','password':'testamundo','email':'api@guesttest.com'}"
    @Shared String guestlogin = 'apitest'
    @Shared String guestpassword = 'testamundo'



    @Transactional('auth')
    void "login and get token"(){
        setup:"logging in"

            this.testDomain = Holders.grailsApplication.config.environments.test.grails.serverURL
            String login = Holders.grailsApplication.config.root.login
            String password = Holders.grailsApplication.config.root.password
            String loginUri = Holders.grailsApplication.config.grails.plugin.springsecurity.rest.login.endpointUrl

            def info
            String url = "curl -v  -H 'Content-Type: application/json' -H 'Origin: http://localhost' -H 'Access-Control-Request-Headers: Origin,X-Requested-With' -d '{\"username\":\"${login}\",\"password\":\"${password}\"}' http://oauth.beapi.io:8080${loginUri}"
            def proc = ['bash','-c',url].execute()
            proc.waitFor()

            def outputStream = new StringBuffer()
            def error = new StringWriter()
            proc.waitForProcessOutput(outputStream, error)
            String output = outputStream.toString()
            if(output) {
                info = new JsonSlurper().parseText(output)
            }else{
                println("login and get token (ERR):"+error)
            }
        when:"set token"
            this.token = info.access_token
            info.authorities.each(){ it ->
                this.authorities.add(it)
            }
        then:"has bearer token"
            assert info.token_type == 'Bearer'
    }



    /**
     * TODO: output errors for this
     */
    void "GET api call: [domain object]"() {
        setup:"api is called"
            String METHOD = "GET"
            String controller = 'person'
            String action = 'show'
            LinkedHashMap info = [:]
            ApiCacheService apiCacheService = applicationContext.getBean("apiCacheService")

            LinkedHashMap cache = apiCacheService.getApiCache(controller)
            Integer version = cache['cacheversion']

            //String pkey = cache?."${version}"?."${action}".pkey[0]

            def proc = ["curl","-H","Content-Type: application/json","-H","Authorization: Bearer ${this.token}","--request","${METHOD}","${this.testDomain}/${this.appVersion}/${controller}/${action}?id=1"].execute();
            proc.waitFor()

            def outputStream = new StringBuffer()
            def error = new StringWriter()
            proc.waitForProcessOutput(outputStream, error)
            String output = outputStream.toString()
            if(output) {
                println("OUTPUT:"+output)
                info = new JsonSlurper().parseText(output)
                def slurper = new JsonSlurper()
                slurper.parseText(output).each(){ k,v ->
                    info[k] = v
                }
            }else{
                println("GET api call: [domain object] (ERR):"+error)
            }






        when:"info is not null"
            assert info!=[:]
        then:"get user"
            cache?."${version}"?."${action}".returns.each(){ k,v ->
                assert this.authorities.contains(k)
                v.each(){ it ->
                    assert info."${it.name}" != null
                }
            }
    }




}

