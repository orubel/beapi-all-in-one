package net.nosegrind.apiframework

//@ToString(includeNames = true, includeFields = true)
class Arch{

	static transactional = true

	String url
	Boolean master
	Boolean hasCert = false
	Date certExpiration

	static constraints = {
		url blank: false, unique: true
		master unique: true, nullable:true
		hasCert blank: false
		certExpiration nullable: true
	}

	static mapping = {
		master index: 'arch_master_idx'
		cache true
	}

}
