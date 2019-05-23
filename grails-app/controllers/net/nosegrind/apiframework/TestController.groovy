package net.nosegrind.apiframework

class TestController {

    def springSecurityService

    HashMap show(){
        try{
            Test test
            test = Test.get(params?.id?.toLong())
            if(test) {
                return [test: test]
            }else{
                render(status: 500,text:"Id does not match record in database.")
            }
        }catch(Exception e){
            throw new Exception("[TestController : get] : Exception - full stack trace follows:",e)
        }
    }

    HashMap create(){
        try{
            Test test = new Test(name:"${params.name}")
            if(!test.save(flush:true,failOnError:true)){
                test.errors.allErrors.each { println it }
            }
            return [test:test]
        }catch(Exception e){
            throw new Exception("[TestController : create] : Exception - full stack trace follows:",e)
        }
    }

    HashMap delete() {
        try {
            Test test
            test = Test.get(params?.id?.toLong())
            if (test) {
                if (!test.delete(flush: true, failOnError: true)) {
                    test.errors.allErrors.each { println it }
                }
            }else{
                render(status: 500,text:"Id does not match record in database.")
            }
            return [test: [id: params.id.toLong()]]
        }catch(Exception e){
            throw new Exception("[TestController : delete] : Exception - full stack trace follows:",e)
        }
    }

    HashMap testHook() {
        return [test:params]
    }

    protected boolean isSuperuser() {
        springSecurityService.principal.authorities*.authority.any { grailsAccount.config.apitoolkit.admin.roles.contains(it) }
    }
}
