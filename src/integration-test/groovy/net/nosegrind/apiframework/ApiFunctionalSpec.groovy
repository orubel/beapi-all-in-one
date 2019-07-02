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
    @Shared String guestdata = "{'username': 'apitest','password':'testamundo','email':'api@guesttest.com',firstName:'api',lastName:'test'}"
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
            String url = "curl -v  -H 'Content-Type: application/json' -H 'Origin: http://localhost' -H 'Access-Control-Request-Headers: Origin,X-Requested-With' -d '{\"username\":\"${login}\",\"password\":\"${password}\"}' ${this.testDomain}${loginUri}"
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

    // create using mockdata
    void "CREATE user"() {
        setup:"api is called"

            String METHOD = "POST"
            String controller = 'person'
            String action = 'create'
            ApiCacheService apiCacheService = applicationContext.getBean("apiCacheService")
            LinkedHashMap cache = apiCacheService.getApiCache(controller)
            Integer version = cache['cacheversion']

            def info

            def proc = ["curl", "-H", "Content-Type: application/json", "-H", "Authorization: Bearer ${this.token}", "--request", "${METHOD}", "-d", "${this.guestdata}", "${this.testDomain}/${this.appVersion}/${controller}/${action}"].execute();

            proc.waitFor()
            def outputStream = new StringBuffer()
            def error = new StringWriter()
            proc.waitForProcessOutput(outputStream, error)
            String output = outputStream.toString()
            if(output) {
                info = new JsonSlurper().parseText(output)
            }else{
                println("CREATE user (ERR):"+error)
            }
        when:"info is not null"
            assert info!=null
        then:"created user"
            cache?."${version}"?."${action}".returns.each(){ k,v ->
                assert this.authorities.contains(k)
                v.each(){
                    if(it.keyType=='PRIMARY'){
                        this.guestId = info."${it.name}"
                    }
                    assert info."${it.name}" != null
                }
            }
    }

    // create using mockdata
    void "CREATE user with role"() {
        setup:"api is called"
        String METHOD = "POST"
        String controller = 'personRole'
        String action = 'create'
        String data = "{'personId': '${this.guestId}','roleId':'1'}"
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
        String METHOD = "POST"

        setup:"logging in"
        String loginUri = Holders.grailsApplication.config.grails.plugin.springsecurity.rest.login.endpointUrl

        String url = "curl -H 'Content-Type: application/json' -X ${METHOD} -d '{\"username\":\"${this.guestlogin}\",\"password\":\"${this.guestpassword}\"}' ${this.testDomain}${loginUri}"
        def proc = ['bash','-c',url].execute()
        proc.waitFor()
        def info = new JsonSlurper().parseText(proc.text)

        when:"set token"
        this.guestToken = info.access_token
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

            def proc = ["curl","-H","Content-Type: application/json","-H","Authorization: Bearer ${this.token}","--request","${METHOD}","${this.testDomain}/${this.appVersion}/${controller}/${action}?id=${this.guestId}"].execute();
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

    /**
     * TODO: output errors for this
     */
    void "GET api call with version: [domain object]"() {
        setup:"api is called"
            String METHOD = "GET"
            String controller = 'person'
            String action = 'show'
            LinkedHashMap info = [:]
            ApiCacheService apiCacheService = applicationContext.getBean("apiCacheService")

            LinkedHashMap cache = apiCacheService.getApiCache(controller)
            Integer version = cache['cacheversion']
            //String pkey = cache?."${version}"?."${action}".pkey[0]

            def proc = ["curl","-H","Content-Type: application/json","-H","Authorization: Bearer ${this.token}","--request","${METHOD}","${this.testDomain}/${this.appVersion}-1/${controller}/${action}?id=${this.guestId}"].execute();
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

    // test list of domain objects
    void "GET list api call: [list of domain objects]"() {
        setup:"api is called"
            String METHOD = "GET"
            String controller = 'person'
            String action = 'list'
            LinkedHashMap info = [:]
            ApiCacheService apiCacheService = applicationContext.getBean("apiCacheService")

            //LinkedHashMap cache = apiCacheService.getApiCache(controller)
            //Integer version = cache['cacheversion']
            //String pkey = cache?."${version}"?."${action}".pkey[0]

            def proc = ["curl","-H","Content-Type: application/json","-H","Authorization: Bearer ${this.token}","--request","${METHOD}","${this.testDomain}/${this.appVersion}/${controller}/${action}"].execute();
            proc.waitFor()
            def outputStream = new StringBuffer()
            def error = new StringWriter()
            proc.waitForProcessOutput(outputStream, error)
            String output = outputStream.toString()

            //ArrayList stdErr = error.toString().split( '> \n' )
            //ArrayList response1 = stdErr[0].split("> ")
            //ArrayList response2 = stdErr[1].split("< ")
        
            def slurper = new JsonSlurper()
            slurper.parseText(output).each(){ k,v ->
                info[k] = v
            }
        when:"info is not null"
            assert info!=[:]
        then:"get user"
            assert info.size()==Person.list().size()
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

            def proc = ["curl","-H","Content-Type: application/json","-H","Authorization: Bearer ${this.token}","--request","${METHOD}","${this.testDomain}/${this.appVersion}/${controller}/${action}?id=${this.guestId}"].execute();
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
            assert this.guestId == id
    }



}

