package net.nosegrind.apiframework


import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.plugin.springsecurity.SpringSecurityService
import grails.compiler.GrailsCompileStatic
import groovy.transform.CompileDynamic
import org.springframework.beans.factory.annotation.Autowired
import grails.util.Holders
import org.springframework.context.ApplicationContext

class Person{

	static transactional = true
	static transients = ['hasBeforeInsert','hasBeforeValidate','hasBeforeUpdate','springSecurityService']

	transient hasBeforeInsert = false
	transient hasBeforeValidate = false
	transient hasBeforeUpdate = false

	SpringSecurityService springSecurityService

	String username
	String password
	String uid
	String firstName = 'null'
	String lastName = 'null'
	String email
	boolean emailVerified=false
	String oauthId
	String oauthProvider
	String avatarUrl
	boolean enabled=true
	boolean accountExpired=false
	boolean accountLocked=false
	boolean passwordExpired=false

/*
	Person(String username, String password) {
		this()
		this.username = username
		this.password = password
	}
*/


	@Override
	int hashCode() {
		username?.hashCode() ?: 0
	}

	@Override
	boolean equals(other) {
		is(other) || (other instanceof Person && other.username == username)
	}

	@Override
	String toString() {
		username
	}

	/*
	Set<Role> getAuthorities() {
		PersonRole.findAllByPerson(this)*.role
	}
*/

	Set<Role> getAuthorities() {
		(PersonRole.findAllByPerson(this) as List<PersonRole>)*.role as Set<Role>
	}

	def beforeInsert() {
		if (!hasBeforeInsert) {
			hasBeforeInsert = true
			encPassword()
		}
	}

	def afterInsert() {
		hasBeforeInsert = false
	}

	def beforeUpdate() {
		if (!hasBeforeUpdate) {
			if (isDirty('password')) {
				hasBeforeUpdate = true
				encPassword()
			}
		}
	}

	def afterUpdate() {
		hasBeforeUpdate = false
	}

	protected void encPassword() {
		ApplicationContext ctx = Holders.grailsApplication.mainContext
		def springSecurityService = ctx.getBean("springSecurityService")
		if (springSecurityService == null){
			println "springSecurityService is null"
		}
		password = springSecurityService.encodePassword(password)
	}


/*
	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}
*/

	static constraints = {
		username blank: false, unique: true
		password blank: false
		uid nullable: true
		firstName(nullable:false)
		lastName(nullable:false)
		email(nullable:false,email:true, unique: true,maxSize:100)
		oauthId(nullable: true)
		oauthProvider(nullable: true)
		avatarUrl(nullable: true)
	}

	static mapping = {
		password column: '`password`'
		cache true
	}
}
