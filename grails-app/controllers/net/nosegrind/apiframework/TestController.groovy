package net.nosegrind.apiframework

class TestController {

    def springSecurityService

    LinkedHashMap show(){
        println("test/show called")
        println(params)
        f = new File('myfile.txt')
        f.append('hello again!\n')
        /*
        try{
            Test test
            test = Test.get(params?.id?.toLong())
            return [test: test]
        }catch(Exception e){
            throw new Exception("[TestController : get] : Exception - full stack trace follows:",e)
        }
        */
        return [test:params]
    }

    LinkedHashMap create(){
        try{
            Test test = new Test(name:"${params.name}")
            if(!test.save(flush:true,failOnError:true)){
                test.errors.allErrors.each { log.error it }
            }
            return [test:test]
        }catch(Exception e){
            throw new Exception("[TestController : create] : Exception - full stack trace follows:",e)
        }
    }

    LinkedHashMap delete() {
        try {
            Test test
            test = Test.get(params?.id?.toLong())
            test.delete(flush: true, failOnError: true)
            return [test: [id: params.id.toLong()]]
        }catch(Exception e){
            throw new Exception("#[TestController : delete] : Exception - full stack trace follows:",e)
        }
    }

    LinkedHashMap testHook() {
        return [test:params]
    }

    protected boolean isSuperuser() {
        springSecurityService.principal.authorities*.authority.any { grailsApplication.config.apitoolkit.admin.roles.contains(it) }
    }
}
