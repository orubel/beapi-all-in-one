package grails.api.framework

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import org.apache.catalina.connector.Connector;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.security.SecurityFilterAutoConfiguration
import org.springframework.boot.autoconfigure.transaction.jta.*
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean


//import org.grails.io.support.Resource
import org.springframework.context.EnvironmentAware
import org.springframework.core.env.Environment
import org.springframework.core.env.MapPropertySource

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.security.SecurityFilterAutoConfiguration
import org.springframework.boot.autoconfigure.transaction.jta.*

import org.grails.config.yaml.YamlPropertySourceLoader
import org.springframework.core.io.ResourceLoader
import org.springframework.core.io.Resource
import org.grails.config.NavigableMapPropertySource
import org.springframework.core.io.DefaultResourceLoader
import groovy.util.ConfigSlurper

import org.apache.coyote.http2.Http2Protocol
import org.apache.coyote.http11.Http11Nio2Protocol

@EnableAutoConfiguration(exclude = [SecurityFilterAutoConfiguration,JtaAutoConfiguration])
class Application extends GrailsAutoConfiguration implements EnvironmentAware,ExternalConfig {

    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }

    // Add secondary connector for port 8080
    //@Bean
    //public EmbeddedServletContainerFactory servletContainer() {
    //    TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory()
    //    tomcat.addAdditionalTomcatConnectors(createConnector())
    //    return tomcat
    //}


    private Connector createConnector() {
        try {
            Connector connector = new Connector();
            connector.setPort(8080);
            connector.setRedirectPort(8443)
            connector.setSecure(false);
            connector.setScheme("http");
            return connector;
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
                        NavigableMapPropertySource yamlConfig = yamlPropertySourceLoader.load(resource.filename, resource, null) as NavigableMapPropertySource
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
