

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
        Arch architecture = Arch.findByUrl(url)
        if(!architecture) {
            architecture = new Arch(url:url)
            architecture.save(flush:true,failOnError:true)
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

    }

    def destroy = {
	    //server.stop()
    }
}
