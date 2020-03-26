

//import grails.plugins.GrailsPluginManager
//import grails.plugins.GrailsPlugin
import net.nosegrind.apiframework.Arch
import net.nosegrind.apiframework.Person
import net.nosegrind.apiframework.Role
import net.nosegrind.apiframework.PersonRole
import grails.util.Environment
import grails.util.Holders
import grails.gorm.transactions.Transactional

//import org.h2.tools.Server

@Transactional('auth')
class BootStrap {

    final String[] args = ["-tcpPort", "8092", "-tcpAllowOthers"]
    //Server server
    def passwordEncoder
    def grailsApplication
    def springSecurityService

    def init = { servletContext ->
        // Throttle
        // only instantiate if this server is 'master'; check config value
        //server = Server.createTcpServer(args).start()

        String url = Holders.config.grails.serverURL
        Integer cores = grailsApplication.config.apitoolkit.procCores
        Boolean master = (grailsApplication.config.apitoolkit.serverType)? true : null
        Arch architecture = Arch.findByUrl(url)
        if(!architecture?.id) {
            architecture = new Arch(url:url, master:master)
            if (!architecture.save(flush: true,failOnError:true)) {
                println('#### WARNING ####:  Bad URL for Instance *OR* Master may already exist; Check your beapi_api.yml file for this instance.')
                // System.exit(0)
            }
        }

        
        def networkRoles = grailsApplication.config.apitoolkit.networkRoles
        networkRoles.each(){ k, v ->
            v.each{ it2 ->
                Role role = Role.findByAuthority(it2)
                if(!role){
                    role = new Role(authority:it2)
                    role.save(flush:true,failOnError:true)
                }
            }
        }

        // bootstrap admin
        Person user = Person.findByUsername("${grailsApplication.config.root.login}")
        PersonRole.withTransaction { status ->
            Role adminRole = Role.findByAuthority("ROLE_ADMIN")

            if (!user?.id) {
                user = new Person(username: "${grailsApplication.config.root.login}", password: "${grailsApplication.config.root.password}", email: "${grailsApplication.config.root.email}")

                if (!user.save(flush: true)) {
                    user.errors.allErrors.each { println it }
                }
            } else {
                // user exists
                if (!passwordEncoder.isPasswordValid(user.password, grailsApplication.config.root.password, null)) {
                    log.error "Error: Bootstrapped Root Password was changed in config. Please update"
                }
            }

            if (!user?.authorities?.contains(adminRole)) {
                PersonRole pRole = new PersonRole(user, adminRole)
                pRole.save(flush: true, failOnError: true)
            } else {
                // role exists
            }

            status.isCompleted()
        }

        // bootstrap test user
        Person user2 = Person.findByUsername("${grailsApplication.config.test.login}")
        PersonRole.withTransaction { status ->
            Role userRole = Role.findByAuthority("ROLE_USER")

            if (!user2?.id) {
                user2 = new Person(username: "${grailsApplication.config.test.login}", password: "${grailsApplication.config.test.password}", email: "${grailsApplication.config.test.email}")

                if (!user2.save(flush: true)) {
                    user2.errors.allErrors.each { println it }
                }
            } else {
                // user exists
                if (!passwordEncoder.isPasswordValid(user2.password, grailsApplication.config.test.password, null)) {
                    log.error "Error: Bootstrapped Root Password was changed in config. Please update"
                }
            }

            if (!user2?.authorities?.contains(userRole)) {
                PersonRole pRole = new PersonRole(user2, userRole)
                pRole.save(flush: true, failOnError: true)
            } else {
                //println("role exists")
            }
            
            status.isCompleted()
        }
        
    }

    def destroy = {
	    //server.stop()
    }
}
