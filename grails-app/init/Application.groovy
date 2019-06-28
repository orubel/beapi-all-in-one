package grails.api.framework

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import org.apache.catalina.connector.Connector;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration
import org.springframework.boot.autoconfigure.transaction.jta.*
//import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
//import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory
import org.springframework.context.annotation.Bean
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory

//import org.grails.io.support.Resource
import org.springframework.context.EnvironmentAware
import org.springframework.core.env.Environment
import org.springframework.core.env.MapPropertySource

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
//import org.springframework.boot.autoconfigure.security.SecurityFilterAutoConfiguration
import org.springframework.boot.autoconfigure.transaction.jta.*

import org.grails.config.yaml.YamlPropertySourceLoader
import org.springframework.core.io.ResourceLoader
import org.springframework.core.io.Resource
import org.grails.config.NavigableMapPropertySource
import org.springframework.core.io.DefaultResourceLoader
import groovy.util.ConfigSlurper

import org.apache.coyote.http2.Http2Protocol

@EnableAutoConfiguration(exclude = [SecurityFilterAutoConfiguration,JtaAutoConfiguration])
class Application extends GrailsAutoConfiguration implements EnvironmentAware,ExternalConfig {

    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }

    // Add secondary connector for port 8080
    @Bean
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory()
        tomcat.addAdditionalTomcatConnectors(createConnector())
        //tomcat.addContextValves(headerEncodingValve)
        return tomcat
    }

    // Add port 8080 and redirect to 8443
    private Connector createConnector() {
        try {
            Connector connector = new Connector()
            connector.setScheme("http")
            connector.setPort(8080)
            connector.setSecure(false)
            connector.setRedirectPort(8443)
            connector.addUpgradeProtocol(new Http2Protocol())
            return connector
        } catch (Throwable ex) {
            throw new IllegalStateException("Failed setting up Connector", ex)
        }
    }

}

trait ExternalConfig implements EnvironmentAware {

    private ResourceLoader defaultResourceLoader = new DefaultResourceLoader()
    private YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader()

    @Override
    void setEnvironment(Environment environment) {
        List locations = environment.getProperty('grails.config.locations', ArrayList, [])
        String encoding = environment.getProperty('grails.config.encoding', String, 'UTF-8')

        if (locations) {

            locations.reverse().each { location ->
                String finalLocation = location.toString()
                // Replace ~ with value from system property 'user.home' if set
                String userHome = System.properties.getProperty('user.home')
                if (userHome && finalLocation.startsWith('~/')) {
                    finalLocation = "file:${userHome}${finalLocation[1..-1]}"
                }
                finalLocation = environment.resolvePlaceholders(finalLocation)

                Resource resource = defaultResourceLoader.getResource(finalLocation) as Resource

                if(resource.exists()) {
                    if (finalLocation.endsWith('.groovy')) {
                        String configText = resource.inputStream.getText(encoding)
                        Map properties = configText ? new ConfigSlurper(grails.util.Environment.current.name).parse(configText)?.flatten() : [:]
                        MapPropertySource groovyConfig = new MapPropertySource(resource.filename, properties)
                        environment.propertySources.addFirst(groovyConfig)
                    } else if (finalLocation.endsWith('.yml')) {
                        def yamlConfig = yamlPropertySourceLoader.load(resource.filename, resource, null)
                        environment.propertySources.addFirst(yamlConfig)
                    } else {
                        // properties
                    }
                }else{
                    println("${finalLocation} does not exist")
                }

            }
        }

    }

}
