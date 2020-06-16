package net.nosegrind.auth

//import grails.converters.deep.XML
import grails.converters.*
import main.groovy.net.nosegrind.auth.OAuthProfile
import main.groovy.net.nosegrind.auth.AuthInfo
import org.scribe.builder.api.LinkedInApi
import org.scribe.model.OAuthRequest
import org.scribe.model.Token
import org.scribe.model.Verb
import org.scribe.model.Verifier
import org.scribe.oauth.OAuthService
//import Provider

class LinkedinAuthService extends GrailsOAuthService {

	def grailsApplication

	
    //@Override
    OAuthService createOAuthService(String callbackUrl) {
        return createServiceBuilder(LinkedInApi.class,
				grailsApplication.config.auth.linkedin.key as String,
				grailsApplication.config.auth.linkedin.secret as String,
				callbackUrl).build()
    }

    AuthInfo getAuthInfo(String callbackUrl) {
        OAuthService authService = createOAuthService(callbackUrl)
		Token requestToken = authService.getRequestToken();
		new AuthInfo(authUrl: authService.getAuthorizationUrl(requestToken), requestToken: requestToken, service: authService)
	}

    Token getAccessToken(OAuthService authService, Map params, Token requestToken ){
		Verifier verifier = new Verifier(params.oauth_verifier)
		authService.getAccessToken(requestToken, verifier)
	}

	OAuthProfile getProfile(OAuthService authService, Token accessToken) {
		def profile = sendRequest(authService, accessToken, Verb.GET, "http://api.linkedin.com/v1/people/~")
        def m = profile.'site-standard-profile-request'.url =~ /.*id=(\d*)&.*/
        def uid = m[0][1]
		def name = "${profile.'first-name' as String} ${profile.'last-name' as String}"
        def login = "${profile.'first-name' as String}.${profile.'last-name' as String}".toLowerCase()
        new OAuthProfile(name:name,username: login, uid: uid)
	}

	private sendRequest(OAuthService authService, Token accessToken, Verb method, String url){
		OAuthRequest request = new OAuthRequest(method, url)
		authService.signRequest(accessToken, request)
		def response = request.send()
		XML.use('deep')
		return XML.parse(response.body)
	}

}