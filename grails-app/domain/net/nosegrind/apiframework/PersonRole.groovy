package net.nosegrind.apiframework

import grails.gorm.DetachedCriteria

class PersonRole implements Serializable {
	static belongsTo = [Person, Role]

	Person person
	Role role

	PersonRole(Person u, Role r) {
		this()
		this.person = u
		this.role = r
	}

	static PersonRole get(long personId, long roleId) {
		criteriaFor(personId, roleId).get()
	}

	static boolean exists(long personId, long roleId) {
		criteriaFor(personId, roleId).count()
	}

	private static DetachedCriteria criteriaFor(long personId, long roleId) {
		PersonRole.where {
			person == Person.load(personId) &&
					role == Role.load(roleId)
		}
	}

	static PersonRole create(Person person, Role role, boolean flush = false) {
		def instance = new PersonRole(person, role, null)
		instance.save(flush: flush, insert: true)
		instance
	}

	static boolean remove(Person u, Role r, boolean flush = false) {
		if (u == null || r == null) return false

		int rowCount = PersonRole.where { person == u && role == r }.deleteAll()

		if (flush) { PersonRole.withSession { it.flush() } }

		rowCount
	}

	static void removeAll(Person u, boolean flush = false) {
		if (u == null) return

		PersonRole.where { person == u }.deleteAll()

		if (flush) { PersonRole.withSession { it.flush() } }
	}

	static void removeAll(Role r, boolean flush = false) {
		if (r == null) return

		PersonRole.where { role == r }.deleteAll()

		if (flush) { PersonRole.withSession { it.flush() } }
	}

	static constraints = {
		role validator: { Role r, PersonRole ur ->
			if (ur.person == null || ur.person.id == null) return
			boolean existing = false
			PersonRole.withNewSession {
				existing = PersonRole.exists(ur.person.id, r.id)
			}
			if (existing) {
				return 'userRole.exists'
			}
		}
	}

	static mapping = {
		id composite: ['person', 'role']
		//cache true
	}

}
