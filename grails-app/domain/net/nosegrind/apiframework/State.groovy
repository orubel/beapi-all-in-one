package net.nosegrind.apiframework

class State {

	static belongsTo = [Person]

	String name
	String abbrev
	String code

	static constraints = {
		name nullable:false
		abbrev nullable:false
		code nullable:false, maxSize:2, minSize:2
	}

	static mapping = {
		master index: 'state_code_idx'
		cache: true
	}
}
