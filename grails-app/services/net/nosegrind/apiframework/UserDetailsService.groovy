package net.nosegrind.apiframework


import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.userdetails.GrailsUser
import grails.plugin.springsecurity.userdetails.GrailsUserDetailsService
//import grails.transaction.Transactional
import grails.gorm.transactions.Transactional
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.GrantedAuthority


class UserDetailsService implements GrailsUserDetailsService {

    /**
     * Some Spring Security classes (e.g. RoleHierarchyVoter) expect at least
     * one role, so we give a user with no granted roles this one which gets
     * past that restriction but doesn't grant anything.
     */
    static final List NO_ROLES = [new SimpleGrantedAuthority(SpringSecurityUtils.NO_ROLE)]

    UserDetails loadUserByUsername(String username, boolean loadRoles) throws UsernameNotFoundException {
        return loadUserByUsername(username)
    }

    @Transactional(readOnly=true, noRollbackFor=[IllegalArgumentException, UsernameNotFoundException])
    BeapiUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person user = User.findByUsername(username)
        if(!user){ throw new UsernameNotFoundException('User not found', username) }
        def authorities = user.authorities.collect {
            new SimpleGrantedAuthority(it.authority)
        }

        return new BeapiUserDetails(user.username, user.password, user.enabled, !user.accountExpired, !user.passwordExpired, !user.accountLocked, authorities ?: NO_ROLES, user.id, user.firstName + " " + user.lastName)
    }
}

class BeapiUserDetails extends GrailsUser {

    final String fullName

    BeapiUserDetails(String username, String password, boolean enabled,
                  boolean accountNonExpired, boolean credentialsNonExpired,
                  boolean accountNonLocked,
                  Collection<GrantedAuthority> authorities,
                  long id, String fullName) {
        super(username, password, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities, id)

        this.fullName = fullName
    }
}