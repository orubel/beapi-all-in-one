package net.nosegrind.apiframework

class RoleController{

	HashMap list() {
		def result = Role.list()
		return [role:result]
	}


	LinkedHashMap create(){
		try{
			Role role = new Role(usernameauthority:"${params.authority}")

			if(role){
				if(!role.save(flush:true,failOnError:true)){
					role.errors.allErrors.each { println(it) }
				}
				return [role:role]
			}else{
				render(status: 500,text:"Id does not match record in database.")
			}
		}catch(Exception e){
			throw new Exception("[RoleController : create] : Exception - full stack trace follows:",e)
		}
	}

	LinkedHashMap delete() {
		Role role
		try {
			role = Role.findByAuthority(params.authority)
			if(role){
				role.delete(flush: true, failOnError: true)
				return [role: params.authority]
			}else{
				render(status: 500,text:"Role '" + params.authority + "' does not match record in database.")
			}
		}catch(Exception e){
			throw new Exception("[RoleController : delete] : Exception - full stack trace follows:",e)
		}
	}
}
