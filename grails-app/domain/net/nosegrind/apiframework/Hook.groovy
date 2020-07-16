package net.nosegrind.apiframework

class Hook {

	Person user
	String url
	String format = 'JSON'
	String service
	String authorization
	Long attempts = 0
	Boolean isEnabled = true
	Date dateCreated
	Date lastModified = new Date()

	static constraints = {
		user(nullable:false)
		url(nullable:false)
		format(nullable:false)
		service(nullable:false)
		attempts(nullable:false)
		authorization(nullable:true)
	}

	static mapping = {
		cache true
	}

}
