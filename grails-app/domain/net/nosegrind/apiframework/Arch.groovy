package net.nosegrind.apiframework

//@ToString(includeNames = true, includeFields = true)
class Arch{

	static transactional = true

	String url
	Boolean hasCert = false
	Date certExpiration

	static constraints = {
		url blank: false, unique: true
		hasCert blank: false
		certExpiration nullable: true
	}

	static mapping = {
		cache true
	}

}
