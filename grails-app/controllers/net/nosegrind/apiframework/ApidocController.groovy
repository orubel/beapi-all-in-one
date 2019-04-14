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

	HashMap show(){
		HashMap docs = [:]
		List cacheKeys = apiCacheService.getCacheKeys()

		String authority = springSecurityService.principal.authorities*.authority[0]
		cacheKeys.each(){
			def cache = apiCacheService.getApiCache(it)
			if(cache){
				String version = cache['currentStable']['value']
				cache[version].each() { k, v ->

					if (!['deprecated', 'defaultAction','currentStable'].contains(k)) {
						if(!docs["${it}"] && cache[version][k]['roles'].contains(authority)){
							docs["${it}"] =[:]
						}
					}
				}

			}
		}

		return ['apidoc':docs]
	}

}

