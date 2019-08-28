package net.nosegrind.apiframework

class PersonController{
	
	def springSecurityService


	HashMap list() {
		if(isSuperuser()){
			def person = Person.list()
			return [person:person]
		}
	}


	LinkedHashMap show(){
		try{
			Person user = new Person()
			if(isSuperuser() && params?.id){
				user = Person.get(params.id)
				//user = Person.findWhere(id: params?.id?.toLong(), enabled: true)
			}else{
				user = Person.get(springSecurityService.principal.id)
				//user = springSecurityService.getCurrentUser()
			}
			if(user){
				return [person: user]
			}else{
				render(status: 500,text:"Id (${params.id})does not match record in database.")
			}

		}catch(Exception e){
			throw new Exception("[PersonController : get] : Exception - full stack trace follows:",e)
		}
    }

	LinkedHashMap create(){
		try{
			Person user = new Person(username:"${params.username}",password:"${params.password}",email:"${params.email}")
			if(user){
				if(!user.save(flush:true,failOnError:true)){
					user.errors.allErrors.each { println(it) }
				}
				return [person:user]
			}else{
				render(status: 500,text:"Params sent do not match requirements for database table.")
			}
		}catch(Exception e){
			throw new Exception("[PersonController : create] : Exception - full stack trace follows:",e)
		}
	}

	LinkedHashMap update(){
		println()
		try{
			Person user = new Person()
			if(isSuperuser()){
				user = Person.findWhere(id: params?.id?.toLong(), enabled: true)
			}else{
				user = Person.get(springSecurityService.principal.id)
			}
			if(user){
				user.username = params.username
				user.password = params.password
				user.email = params.email

				if(!user.save(flush:true,failOnError:true)){
					user.errors.allErrors.each { println(it) }
				}
				return [person:user]
			}else{
				render(status: 500,text:"Id does not match record in database.")
			}
		}catch(Exception e){
			throw new Exception("[PersonController : update] : Exception - full stack trace follows:",e)
		}
	}

	LinkedHashMap getByUsername(){
		try{
			Person user
			user = Person.findWhere(username: "params?.username", enabled: true)
			if(user){
				return [person: user]
			}else{
				render(status: 500,text:"Id does not match record in database.")
			}
			return [person: user]
		}catch(Exception e){
			throw new Exception("[PersonController : getByUsername] : Exception - full stack trace follows:",e)
		}
	}


	LinkedHashMap disable() {
		Person user
		try {
			if(isSuperuser()){
				user = Person.get(params?.id?.toLong())
			}else{
				user = Person.get(springSecurityService.principal.id)
			}
			if(user){
				user.enabled=false
				//user.delete(flush: true, failOnError: true)
				if(!user.save(flush:true,failOnError:true)){
					user.errors.allErrors.each { println(it) }
				}

				if(isSuperuser()){
					return [person: [id:params.id.toLong()]]
				}else{
					return [person: [id:springSecurityService.principal.id]]
				}
			}else{
				render(status: 500,text:"Id " + params.id + " does not match record in database.")
			}
		}catch(Exception e){
			throw new Exception("[PersonController : disable] : Exception - full stack trace follows:",e)
		}
	}

	LinkedHashMap enable() {
		Person user
		try {
			if(springSecurityService.principal.authorities*.contains('ROLE_ADMIN')){
				user = Person.get(params?.id?.toLong())
			}else{
				user = Person.get(springSecurityService.principal.id)
			}
			if(user){
				user.enabled=true
				//user.delete(flush: true, failOnError: true)
				if(!user.save(flush:true,failOnError:true)){
					user.errors.allErrors.each { println(it) }
				}

				if(isSuperuser()){
					return [person: [id:params.id.toLong()]]
				}else{
					return [person: [id:springSecurityService.principal.id]]
				}
			}else{
				render(status: 500,text:"Id " + params.id + " does not match record in database.")
			}
		}catch(Exception e){
			throw new Exception("[PersonController : enable] : Exception - full stack trace follows:",e)
		}
	}

	LinkedHashMap delete() {
		Person user
		List prole
		try {
			user = Person.get(params.id)
			if(user){
					prole = PersonRole.findAllByPerson(user)
					prole.each() {
						it.delete(flush: true, failOnError: true)
					}

					/**
					 * additional dependencies to be removed should be put here
					 */

					user.delete(flush: true, failOnError: true)
					return [person: [id: params.id.toLong()]]
			}else{
				render(status: 500,text:"Id " + params.id + " does not match record in database.")
			}
		}catch(Exception e){
			throw new Exception("[PersonController : delete] : Exception - full stack trace follows:",e)
		}
	}

	protected boolean isSuperuser() {
		springSecurityService.principal.authorities*.authority.any {
			((List)grailsApplication.config.apitoolkit.admin.roles).contains(it)
		}
	}
}
