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
class ErrorFunctionalSpec extends Specification {

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
    @Shared String guestdata = "{'username': 'errtest','password':'testamundo','email':'err@guesttest.com'}"
    @Shared String guestlogin = 'errtest'
    @Shared String guestpassword = 'testamundo'

    void "login and get token"(){
        setup:"logging in"
            this.testDomain = Holders.grailsApplication.config.environments.test.grails.serverURL
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
        String controller = 'person'
        String action = 'create'

        def info
        def proc = ["curl", "-H", "Content-Type: application/json", "-H", "Authorization: Bearer ${this.token}", "--request", "${METHOD}", "-d", "${this.guestdata}", "${this.testDomain}/${this.appVersion}/${controller}/${action}"].execute()
        proc.waitFor()
        def outputStream = new StringBuffer()
        def error = new StringWriter()
        proc.waitForProcessOutput(outputStream, error)
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
        String controller = 'personRole'
        String action = 'create'
        LinkedHashMap mapData = [:]
        String data = "{'personId': '${this.guestId}','roleId':'1'}"

        // test for existing guest user based on mockdata
        String className = "net.nosegrind.apiframework.Person"
        Class Person = grailsApplication.getDomainClass(className).clazz

        def gperson = Person.get(this.guestId)
        if(gperson){
            METHOD = "GET"
            action = 'show'
            data = "{'id':'${gperson.id}'}"
        }

        def info
        def proc = ["curl", "-H", "Content-Type: application/json", "-H", "Authorization: Bearer ${this.token}", "--request", "${METHOD}", "-d", "${data}", "${this.testDomain}/${this.appVersion}/${controller}/${action}"].execute()
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

        String url = "curl -H 'Content-Type: application/json' -X POST -d '{\"username\":\"${this.guestlogin}\",\"password\":\"${this.guestpassword}\"}' ${this.testDomain}${loginUri}"
        def proc = ['bash','-c',url].execute()
        proc.waitFor()
        def info = new JsonSlurper().parseText(proc.text)

        when:"set token"
        this.guestToken = info.access_token
        then:"has bearer token"
        assert info.token_type == 'Bearer'
    }

    // create using mockdata
    void "CREATE api call"() {
        setup:"api is called"
            String METHOD = 'POST'
            String controller = 'person'
            String action = 'create'
            LinkedHashMap mapData = [:]
            String data = "{"


            ApiCacheService apiCacheService = applicationContext.getBean("apiCacheService")
            LinkedHashMap cache = apiCacheService.getApiCache(controller)
            Integer version = cache['cacheversion']

            cache?."${version}"?."${action}".receives.each(){ k,v ->
                v.each(){
                    mapData[it.name] = it.mockData
                    data += "'"+it.name+"': '"+it.mockData+"',"
                }
            }
            data += "}"

            // test for existing guest user based on mockdata
            String className = "net.nosegrind.apiframework.Person"
            Class Person = grailsApplication.getDomainClass(className).clazz
            def gperson = Person.findByUsername(mapData.username)
            if(gperson){
                METHOD = "GET"
                action = 'show'
                data = "{'id':'${gperson.id}'}"
            }


            def info
            def proc = ["curl", "-H", "Content-Type: application/json", "-H", "Authorization: Bearer ${this.token}", "--request", "${METHOD}", "-d", "${data}", "${this.testDomain}/${this.appVersion}/${controller}/${action}"].execute();

            proc.waitFor()
            def outputStream = new StringBuffer()
            def error = new StringWriter()
            proc.waitForProcessOutput(outputStream, error)
        
            String output = outputStream.toString()
            info = new JsonSlurper().parseText(output)

        when:"info is not null"
            assert info!=null
        then:"created user"
            cache?."${version}"?."${action}".returns.each(){ k,v ->
                assert this.authorities.contains(k)
                v.each(){
                    if(it.keyType=='PRIMARY'){
                        this.currentId = info."${it.name}"
                    }
                    assert info."${it.name}" != null
                }
            }
    }

    /**
     * Bad Calls
     */
    void "Testing checkRequestMethod with Bad Variables"() {
        setup:"api is called"
            String METHOD = "POST"
            String controller = 'person'
            String action = 'show'

            LinkedHashMap info = [:]

            ApiCacheService apiCacheService = applicationContext.getBean("apiCacheService")
            LinkedHashMap cache = apiCacheService.getApiCache(controller)
            Integer version = cache['cacheversion']

            //String pkey = cache?."${version}"?."${action}".pkey[0]

            def proc = ["curl","-v","-H","Content-Type: application/json","-H","Authorization: Bearer ${this.token}","--request","${METHOD}","${this.testDomain}/${this.appVersion}/${controller}/${action}?id=${this.currentId}"].execute();
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

        when:"all values returned"
            //Expected request method for endpoint does not match sent method
            assert outputStream.toString() =~ /Expected request method for endpoint does not match sent method/
        then:"bad method sent"
            assert method == '400'
    }

    void "Testing checkURIDefinitions with Bad Variables"() {
        setup:"api is called"
            String METHOD = "GET"
            String controller = 'person'
            String action = 'show'

            LinkedHashMap info = [:]
            ApiCacheService apiCacheService = applicationContext.getBean("apiCacheService")
            LinkedHashMap cache = apiCacheService.getApiCache(controller)

            Integer version = cache['cacheversion']

            String data = "{'fred': 'flintstone'}"

            def proc = ["curl","-v","-H","Content-Type: application/json","-H","Authorization: Bearer ${this.token}","--request","${METHOD}", "-d", "${data}","${this.testDomain}/${this.appVersion}/${controller}/${action}?id=${this.currentId}"].execute();
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
        when:"all values returned"
            assert outputStream.toString() =~ /Expected request variables for endpoint/
        then:"bad method sent"
            assert method == '400'
    }

    void "Testing Bad Token with Bad Variables"() {
        setup:"api is called"
            String METHOD = "GET"
            String controller = 'person'
            String action = 'show'

            LinkedHashMap info = [:]
            ApiCacheService apiCacheService = applicationContext.getBean("apiCacheService")
            LinkedHashMap cache = apiCacheService.getApiCache(controller)

            Integer version = cache['cacheversion']


            def proc = ["curl","-v","-H","Content-Type: application/json","-H","Authorization: Bearer 1234567890","--request","${METHOD}","${this.testDomain}/${this.appVersion}/${controller}/${action}?id=${this.currentId}"].execute();
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
        when:"all values returned"
            //println("#### Testing Bad Token with Bad Variables:"+response2)
            assert outputStream.toString()==""
        then:"bad method sent"
            assert method == '401'
    }

    // test checkAuth
    // create using mockdata
    void "Testing CheckAuth on Unauthorized Endpoint"() {
        setup:"api is called"
            String METHOD = "POST"
            String controller = 'personRole'
            String action = 'create'

            ApiCacheService apiCacheService = applicationContext.getBean("apiCacheService")
            LinkedHashMap cache = apiCacheService.getApiCache(controller)
            Integer version = cache['cacheversion']


            String data = "{'personId':${this.guestId},'roleId':1}"

            def proc = ["curl","-v", "-H", "Content-Type: application/json", "-H", "Authorization: Bearer ${this.guestToken}", "--request", "${METHOD}", "-d", "${data}", "${this.testDomain}/${this.appVersion}/${controller}/${action}"].execute()

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
            //println("#### Testing CheckAuth on Unauthorized Endpoint:"+response2)
            assert method!=null
        then:"created user"
            assert method != 200


    }



    // create using mockdata
    void "DELETE api call: [map]"() {
        setup:"api is called"

            String METHOD = "DELETE"
            String controller = 'person'
            String action = 'delete'

            LinkedHashMap info = [:]
            ApiCacheService apiCacheService = applicationContext.getBean("apiCacheService")
            LinkedHashMap cache = apiCacheService.getApiCache(controller)
            Integer version = cache['cacheversion']

            def proc = ["curl","-H","Content-Type: application/json","-H","Authorization: Bearer ${this.token}","--request","${METHOD}","${this.testDomain}/${this.appVersion}/${controller}/${action}?id=${this.currentId}"].execute();
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

