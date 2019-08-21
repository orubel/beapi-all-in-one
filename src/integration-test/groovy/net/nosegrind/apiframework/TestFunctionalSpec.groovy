package net.nosegrind.apiframework

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.*
import spock.lang.*
import geb.spock.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import grails.util.Metadata
import groovy.json.JsonSlurper
import grails.util.Holders
import net.nosegrind.apiframework.ApiCacheService

/**
 * Testing Template for Bootstrapping. Do NOT bootstrap Person Class as that is handled by TestService
 */
@Integration
@Rollback
class TestFunctionalSpec extends Specification {

    @Autowired
    ApplicationContext applicationContext
    def testService

    
    void "AUTOMATION TEST LOOP"() {
        setup:"get data"
            this.testService = applicationContext.getBean("testService")
            this.testService.initTest()
    }


    void cleanup() {
        this.testService.cleanupTest()
    }
}

