package net.nosegrind.apiframework

class Address {

	static belongsTo = [Person]

	Person user
	String address1
	String address2
	String city
	String state
	Long zip

	static constraints = {
		address1 nullable:false
		address2 nullable:true
		city nullable:false
		state nullable:false
		zip nullable:false
	}

	static mapping = {
		cache false
	}
}
