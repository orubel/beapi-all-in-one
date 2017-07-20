package net.nosegrind.apiframework

class Person implements Serializable {

	String appName
	boolean enabled=true

	static constraints = {
		appName blank: false, unique: true
	}

	static mapping = {
		cache true
	}

}
