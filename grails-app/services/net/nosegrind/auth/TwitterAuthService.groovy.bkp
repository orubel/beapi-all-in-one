package net.nosegrind.auth

//import grails.converters.deep.JSON
import grails.converters.*
import main.groovy.net.nosegrind.auth.OAuthProfile
import main.groovy.net.nosegrind.auth.AuthInfo
import org.scribe.builder.api.TwitterApi
import org.scribe.model.OAuthRequest
import org.scribe.model.Token
import org.scribe.model.Verb
import org.scribe.model.Verifier
import org.scribe.oauth.OAuthService
//import Provider
class TwitterAuthService extends GrailsOAuthService {
	
	def grailsApplication

	
    //@Override
    OAuthService createOAuthService(String callbackUrl) {
        return createServiceBuilder(TwitterApi,
				grailsApplication.config.auth.twitter.key as String,
				grailsApplication.config.auth.twitter.secret as String,
				callbackUrl).build()
    }

    AuthInfo getAuthInfo(String callbackUrl) {
        OAuthService authService = createOAuthService(callbackUrl)
		Token requestToken = authService.getRequestToken()
		new AuthInfo(authUrl : authService.getAuthorizationUrl(requestToken), requestToken : requestToken, service: authService)
	}

	Token getAccessToken(OAuthService authService, Map params, Token requestToken ){
		Verifier verifier = new Verifier( params.oauth_verifier )
		authService.getAccessToken(requestToken, verifier)
	}

    OAuthProfile getProfile(OAuthService authService, Token accessToken) {
		def user = sendRequest(authService, accessToken, Verb.GET, "http://api.twitter.com/1/account/verify_credentials.json")
		new OAuthProfile(name:user.name, uid: user.id, username: user.screen_name, picture: user.profile_image_url)
	}

    private sendRequest(OAuthService authService, Token accessToken, Verb method, String url){
   		OAuthRequest request = new OAuthRequest(method, url)
   		authService.signRequest(accessToken, request)
        def response = request.send()
		JSON.use('deep')
		return JSON.parse(response.body)
	}

}