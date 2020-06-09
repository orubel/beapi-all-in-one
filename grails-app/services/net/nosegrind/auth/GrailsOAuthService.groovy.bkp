package net.nosegrind.auth

import main.groovy.net.nosegrind.auth.OAuthProfile
import main.groovy.net.nosegrind.auth.AuthInfo
import org.scribe.builder.ServiceBuilder
import org.scribe.model.Token
import org.scribe.oauth.OAuthService


public abstract class GrailsOAuthService {

    static transactional = false

    def grailsApplication

    //@Override
    ServiceBuilder createServiceBuilder(Class provider, String apiKey, String secretKey, String callbackUrl) {
        def ServiceBuilder builder = new ServiceBuilder().provider(provider).apiKey(apiKey).apiSecret(secretKey).callback(callbackUrl)
        return builder
    }

    abstract AuthInfo getAuthInfo(String callbackUrl)

    abstract Token getAccessToken(OAuthService authService, Map params, Token requestToken)

    abstract OAuthProfile getProfile(OAuthService authService, Token accessToken)

}

