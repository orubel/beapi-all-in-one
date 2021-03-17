package net.nosegrind.apiframework

class PersonIp{

	static belongsTo = [Person]

	Person user
	String ip
	boolean valid=true
	Date dateCreated

	static constraints = {
		user blank: false
		ip blank: false
		valid blank: false
	}

	static mapping = {
		//datasource 'user'
		cache true
	}

}

