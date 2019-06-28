package net.nosegrind.security


import grails.plugin.springsecurity.userdetails.GormUserDetailsService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

import net.nosegrind.apiframework.Person;

class SpringSecuritySigninService extends GormUserDetailsService {

    void signIn(Person user) {
        def authorities = loadAuthorities(user, user.username, true)
        def userDetails = createUserDetails(user, authorities)
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities))
    }
}
