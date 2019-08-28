package net.nosegrind.apiframework

class PersonRoleController{


	LinkedHashMap create(){
		//try{
			PersonRole prole = new PersonRole(personId:params?.personId?.toLong(),roleId:params.roleId.toLong())

			if(prole){
				if(!prole.save(flush:true)){
					println("###FAILED###")
					prole.errors.allErrors.each {
						println("### ERR:"+it)
					}
				}
				return [personrole:prole]
			}else{
				render(status: 500,text:"Bad data sent. Could not complete transaction.")
			}
		//}catch(Exception e){
		//	throw new Exception("[PersonRoleController : get] : Exception - full stack trace follows:",e)
		//}
	}

	LinkedHashMap showByPerson(){
		try{
			PersonRole role = new PersonRole()
			Person person = Person.get(params?.personId?.toLong())
			role = PersonRole.findByPerson(person)
			if(role){
				return [personrole:role]
			}else{
				render(status: 500,text:"Id does not match record in database.")
			}
		}catch(Exception e){
			throw new Exception("[PersonRoleController : get] : Exception - full stack trace follows:",e)
		}
	}

	LinkedHashMap showByRole(){
		try{
			PersonRole prole = new PersonRole()
			Role role = Role.get(params?.roleId?.toLong())
			prole = PersonRole.findByRole(role)
			if(prole){
				return [personrole:prole]
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