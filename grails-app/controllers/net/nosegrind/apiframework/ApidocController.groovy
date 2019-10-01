package net.nosegrind.apiframework

import org.grails.core.DefaultGrailsControllerClass
//import main.scripts.net.nosegrind.apiframework.Method
import grails.util.Metadata

class ApidocController {

	def apiCacheService
	def springSecurityService
	
	def index(){
		redirect(action:'show')
	}

	LinkedHashMap show(){
		HashMap docs = [:]
		List cacheKeys = apiCacheService.getCacheKeys()

		String authority = springSecurityService.principal.authorities*.authority[0]
		cacheKeys.each(){ it ->
			def cache = apiCacheService.getApiCache(it)
			if(cache){
				String version = cache['currentStable']['value']
				cache[version].each() { k, v ->

					if (!['deprecated', 'defaultAction','currentStable','testOrder', 'testUser'].contains(k)) {

						if(checkAuth(cache[version][k]['roles']) || cache[version][k]['roles'].contains('permitAll')) {
							if (!docs["${it}"]){ // avoid duplicates
								docs["${it}"] = [:]
							}

							docs["${it}"]["${k}"] = v['doc']
						}
					}

				}
			}
		}

		return ['apidoc':docs]
	}

	private boolean checkAuth(LinkedHashSet auths) {
		if(springSecurityService.principal.authorities*.authority.any { auths.contains(it) }){
			return true
		}else{
			return false
		}
	}
}

