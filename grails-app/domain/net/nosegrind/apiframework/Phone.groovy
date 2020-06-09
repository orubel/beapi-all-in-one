package net.nosegrind.apiframework

class Phone {

	static belongsTo = [Person]

	Person user
	String phone
	String phoneType = 'CELL'
	boolean isPrimary = false

	static constraints = {
		user nullable:false
		phone nullable:false
		phoneType nullable:false, inList: ['HOME','WORK','CELL']
		isPrimary nullable:false
	}

	static mapping = {
		cache false
	}
}
