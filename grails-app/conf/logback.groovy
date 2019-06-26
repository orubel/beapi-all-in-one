import grails.util.BuildSettings
import grails.util.Environment


// See http://logback.qos.ch/manual/groovy.html for details on configuration

appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%level %logger - %msg%n"
    }
}

root(ERROR, ['STDOUT'])


def targetDir = System.getProperty('catalina.base')?:System.getProperty('user.dir')
if(targetDir) {
    appender("FULL_STACKTRACE", FileAppender) {
        file = "${targetDir}/logs/orp.log"
        append = true
        encoder(PatternLayoutEncoder) {
            pattern = "%level %logger - %msg%n"
        }
    }
    logger("StackTrace", ERROR, ['STDOUT','FULL_STACKTRACE'], false )
    logger 'main.scripts.net.nosegrind.apiframework', ERROR, ['STDOUT','FULL_STACKTRACE']
    logger 'grails.plugin.springsecurity.rest', ERROR, ['STDOUT','FULL_STACKTRACE']
    logger 'grails.plugins.quartz', ERROR, ['STDOUT','FULL_STACKTRACE']

    // Spring Security Rest Testing/Logging
    logger("org.springframework.security", ERROR, ['STDOUT','FULL_STACKTRACE'])
    logger("grails.plugin.springsecurity", ERROR, ['STDOUT','FULL_STACKTRACE'])
    logger("org.pac4j", ERROR, ['STDOUT'], false)
}
