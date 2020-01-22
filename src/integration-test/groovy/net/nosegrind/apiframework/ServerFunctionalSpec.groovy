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
import net.nosegrind.apiframework.Person
import grails.util.Metadata


@Integration
@Rollback
class ServerFunctionalSpec extends Specification {

    @Autowired
    ApplicationContext applicationContext
    def grailsApplication
    @Shared String token

    @Shared List authorities = ['permitAll']
    @Shared String testDomain
    @Shared String appVersion = "v${Metadata.current.getProperty(Metadata.APPLICATION_VERSION, String.class)}"
    @Shared String guestdata = "{'username': 'errtest','password':'testamundo','email':'err@guesttest.com'}"
    @Shared String guestlogin = 'errtest'
    @Shared String guestpassword = 'testamundo'
    @Shared ArrayList servers = []

    void "login and get token"(){
        setup:"logging in"
        String METHOD = "POST"
        this.testDomain = Holders.grailsApplication.config.environments.test.grails.serverURL
        String login = Holders.grailsApplication.config.root.login
        String password = Holders.grailsApplication.config.root.password
        String loginUri = Holders.grailsApplication.config.grails.plugin.springsecurity.rest.login.endpointUrl
        def proc = ["curl","-H","Origin: http://localhost","-H","Access-Control-Request-Headers: Origin,X-Requested-With","-H", "Content-Type: application/json","--request","${METHOD}", "-d", "{\"username\":\"${login}\",\"password\":\"${password}\"}", "${this.testDomain}${loginUri}"].execute()
        proc.waitFor()
        def outputStream = new StringBuffer()
        def error = new StringWriter()
        proc.waitForProcessOutput(outputStream, error)
        String output = outputStream.toString()

        //ArrayList stdErr = error.toString().split( '> \n' )
        //ArrayList response1 = stdErr[0].split("> ")
        //ArrayList response2 = stdErr[1].split("< ")

        def info = new JsonSlurper().parseText(output)

        when:"set token"
        this.token = info.access_token
        info.authorities.each(){ it ->
            this.authorities.add(it)
        }
        then:"has bearer token"
        assert info.token_type == 'Bearer'
    }

    void "CREATE server list"() {
        setup:"api is called"
        String METHOD = "GET"
        String controller = 'arch'
        String action = 'getServers'

        def info
        def proc = ["curl", "-H", "Content-Type: application/json", "-H", "Authorization: Bearer ${this.token}", "--request", "${METHOD}", "${this.testDomain}/${this.appVersion}/${controller}/${action}"].execute()
        proc.waitFor()
        def outputStream = new StringBuffer()
        def error = new StringWriter()
        proc.waitForProcessOutput(outputStream, error)
        String output = outputStream.toString()
        info = new JsonSlurper().parseText(output)
        when:"info is not null"
            assert info!=null
        then:"has servers"
            assert info['servers'] != null
    }

    void "PING server list"() {
        setup:"api is called"
        String METHOD = "GET"
        String controller = 'arch'
        String action = 'pingServers'

        def info

        def proc = ["curl", "-H", "Content-Type: application/json", "-H", "Authorization: Bearer ${this.token}", "--request", "${METHOD}", "${this.testDomain}/${this.appVersion}/${controller}/${action}"].execute()
        proc.waitFor()
        def outputStream = new StringBuffer()
        def error = new StringWriter()
        proc.waitForProcessOutput(outputStream, error)
        String output = outputStream.toString()
        info = new JsonSlurper().parseText(output)
        when:"info is not null"
        assert info!=null
        then:"has servers"
        assert info['servers'] != null
    }

}

