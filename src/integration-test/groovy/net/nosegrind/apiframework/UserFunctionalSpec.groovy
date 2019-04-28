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


/**
 * See http://www.gebish.org/manual/current/ for more instructions
 */

@Integration
@Rollback
class UserFunctionalSpec extends Specification {

    @Autowired
    ApplicationContext applicationContext

    @Shared String token
    @Shared String guestToken
    @Shared List authorities = ['permitAll']
    @Shared List guestAuthorities = ['permitAll']
    @Shared String controller = 'person'
    @Shared String testDomain = 'http://localhost:8080'
    @Shared String currentId
    @Shared String guestId
    @Shared String appVersion = "v${Metadata.current.getProperty(Metadata.APPLICATION_VERSION, String.class)}"

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

    void "CREATE guest id call"() {
        setup:"api is called"
            String METHOD = "POST"
            String action = 'create'
            String data = "{'username': 'guesttest','password':'testamundo','email':'guest@guesttest.com'}"
            def info
            def proc = ["curl", "-H", "Content-Type: application/json", "-H", "Authorization: Bearer ${this.token}", "--request", "POST", "-d", "${data}", "${this.testDomain}/${this.appVersion}/person/create"].execute()
            proc.waitFor()
            def outputStream = new StringBuffer()
            proc.waitForProcessOutput(outputStream, System.err)
            String output = outputStream.toString()
            info = new JsonSlurper().parseText(output)
            when:"info is not null"
            this.guestId = info['id']
            assert info!=null
        then:"created user"
            assert info['id'] != null

    }

    // create using mockdata
    void "CREATE guest role call"() {
        setup:"api is called"
            String METHOD = "POST"
            String action = 'create'
            String data = "{'personId': '${this.guestId}','roleId':'1'}"
            def info
            def proc = ["curl", "-H", "Content-Type: application/json", "-H", "Authorization: Bearer ${this.token}", "--request", "POST", "-d", "${data}", "${this.testDomain}/${this.appVersion}/personRole/create"].execute()
            proc.waitFor()
            def outputStream = new StringBuffer()
            proc.waitForProcessOutput(outputStream, System.err)
            String output = outputStream.toString()
            info = new JsonSlurper().parseText(output)
        when:"info is not null"
            assert info!=null
        then:"created user"
            assert info['roleId'] != null

    }


    void "GUEST login and get token"(){
        setup:"logging in"
        String loginUri = Holders.grailsApplication.config.grails.plugin.springsecurity.rest.login.endpointUrl

        String url = "curl -H 'Content-Type: application/json' -X POST -d '{\"username\":\"guesttest\",\"password\":\"testamundo\"}' ${this.testDomain}${loginUri}"
        def proc = ['bash','-c',url].execute();
        proc.waitFor()
        def info = new JsonSlurper().parseText(proc.text)

        when:"set token"
        this.guestToken = info.access_token
        info.authorities.each(){ it ->
            this.guestAuthorities.add(it)
        }
        then:"has bearer token"
        assert info.token_type == 'Bearer'
        assert !guestAuthorities.contains('ROLE_ADMIN')
    }

    void "Disable User"() {
        setup:"api is called"
        String METHOD = "GET"
        LinkedHashMap info = [:]
        ApiCacheService apiCacheService = applicationContext.getBean("apiCacheService")
        LinkedHashMap cache = apiCacheService.getApiCache(this.controller)

        Integer version = cache['cacheversion']
        String action = 'disable'
        //String pkey = cache?."${version}"?."${action}".pkey[0]

        def proc = ["curl","-H","Content-Type: application/json","-H","Authorization: Bearer ${this.guestToken}","--request","${METHOD}","${this.testDomain}/${this.appVersion}/person/disable"].execute()
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
            v.each(){ it ->
                assert info."${it.name}" != null
            }
        }
    }

    void "Get User (BAD CALL)"() {
        setup:"api is called"
            String METHOD = "GET"
            LinkedHashMap info = [:]
            ApiCacheService apiCacheService = applicationContext.getBean("apiCacheService")
            LinkedHashMap cache = apiCacheService.getApiCache(this.controller)

            Integer version = cache['cacheversion']
            String action = 'show'
            //String pkey = cache?."${version}"?."${action}".pkey[0]

            def proc = ["curl","-v","-H","Content-Type: application/json","-H","Authorization: Bearer ${this.guestToken}","--request","${METHOD}","${this.testDomain}/${this.appVersion}/${this.controller}/show?id=${this.guestId}"].execute();
            proc.waitFor()
            def outputStream = new StringBuffer()
            def error = new StringWriter()
            proc.waitForProcessOutput(outputStream, error)
            ArrayList stdErr = error.toString().split( '> \n' )
            ArrayList response1 = stdErr[0].split("> ")
            ArrayList response2 = stdErr[1].split("< ")

            String method
            response2.each(){
                def temp = it.split(' ')
                switch(temp[0]){
                    case 'HTTP/1.1':
                        method = temp[1]
                        break
                }
            }
        when:"info is not null"
            assert method!=null
        then:"get user"
            assert method != 200
    }



    void "DELETE guest:"() {
        setup:"api is called"
            String METHOD = "DELETE"
            LinkedHashMap info = [:]
            def proc = ["curl","-H","Content-Type: application/json","-H","Authorization: Bearer ${this.token}","--request","${METHOD}","${this.testDomain}/${this.appVersion}/person/delete?id=${this.guestId}"].execute()
            proc.waitFor()
            def outputStream = new StringBuffer()
            proc.waitForProcessOutput(outputStream, System.err)
            String output = outputStream.toString()

            info = new JsonSlurper().parseText(output)
        when:"info is not null"
            assert info!=null
        then:"delete created user"
            assert this.guestId == info.id
    }

}

