package net.nosegrind.apiframework

class RoleController{

	HashMap list() {
		def result = Role.list()
		return [role:result]
	}


	LinkedHashMap create(){
		try{
			Role role = new Role(authority:"${params.authority}")

			if(role){
				if(!role.save(flush:true,failOnError:true)){
					role.errors.allErrors.each { println(it) }
				}
				return [role:role]
			}else{
				render(status: 500,text:"Id does not match record in database.")
			}
		}catch(Exception e){
			throw new Exception("[RoleController : create] : Role may already exist. Please check database and try again. Exception - full stack trace follows:",e)
		}
	}

	LinkedHashMap delete() {
		Role role
		try {
			role = Role.findByAuthority(params.authority)
			if(role){
				role.delete(flush: true, failOnError: true)
				return [role: [authority:params.authority]]
			}else{
				render(status: 500,text:"Role '" + params.authority + "' does not match record in database.")
			}
		}catch(Exception e){
			throw new Exception("[RoleController : delete] : Role may no longer exist. Exception - full stack trace follows:",e)
		}
	}
}
