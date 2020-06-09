package net.nosegrind.apiframework

class HookRole {
	static belongsTo = [Hook, Role]

	Hook hook
	Role role
	Date dateCreated
	Date lastModified = new Date()

	
	static constraints = {
		hook(nullable:false)
		role(nullable:false)
	}

	static mapping = {
		cache true
	}
}