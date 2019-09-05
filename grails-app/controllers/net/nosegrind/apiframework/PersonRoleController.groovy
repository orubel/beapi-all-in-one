package net.nosegrind.apiframework

class PersonRoleController{


	LinkedHashMap create(){
		try{
			PersonRole prole = new PersonRole(person:params?.personId?.toLong(),role:params.roleId.toLong())

			if(prole){
				if(!prole.save(flush:true)){
					prole.errors.allErrors.each {
						println("### ERR:"+it)
					}
				}
				return [personrole:prole]
			}else{
				render(status: 500,text:"Bad data sent. Could not complete transaction.")
			}
		}catch(Exception e){
			throw new Exception("[PersonRoleController : get] : Exception - full stack trace follows:",e)
		}
	}

	LinkedHashMap showByPerson(){
		try{
			PersonRole prole = new PersonRole()
			Person person = Person.get(params?.personId?.toLong())
			prole = PersonRole.findByPerson(person)
			if(prole){
				return [personrole:['roleId':prole.role.id]]
			}else{
				render(status: 500,text:"Id does not match record in database.")
			}
		}catch(Exception e){
			throw new Exception("[PersonRoleController : get] : Exception - full stack trace follows:",e)
		}
	}

	LinkedHashMap showByRole(){
		try{

			Role role = Role.get(params?.roleId?.toLong())
			PersonRole prole = PersonRole.findByRole(role)

			if(prole){
				return [personrole:['personId':prole.person.id]]
			}else{
				render(status: 500,text:"Id does not match record in database.")
			}
		}catch(Exception e){
			throw new Exception("[PersonRoleController : get] : Exception - full stack trace follows:",e)
		}
	}

	LinkedHashMap delete() {
		PersonRole role
		try {
			Person person = Person.get(params.personId?.toLong())
			if (person) {
				role = PersonRole.findByPerson(person)
				if (role) {
					role.delete(flush: true, failOnError: true)
					return [PersonRole: [personId: params.personId.toLong()]]
				} else {
					render(status: 500, text: "No roles exists for given ID of '" +  params.personId + "' in database.")
				}
			}else{
				render(status: 500, text: "Id " + params.personId + " does not match record in database.")
			}
		}catch(Exception e){
			throw new Exception("[PersonRoleController : delete] : Exception - full stack trace follows:",e)
		}
	}
}