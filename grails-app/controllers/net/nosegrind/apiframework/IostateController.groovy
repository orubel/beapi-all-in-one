package net.nosegrind.apiframework

import net.nosegrind.apiframework.ApiDescriptor
import javax.servlet.http.HttpServletResponse
import org.grails.web.json.JSONObject
import grails.converters.JSON
import grails.converters.XML
import grails.web.servlet.mvc.GrailsHttpSession

class IostateController {

	def springSecurityService
	def apiObjectService
	def apiCacheService
	def webhookService

	LinkedHashMap update() {
		if(isSuperuser()){
			String content = new String(params.iostate.getBytes(),"UTF-8")
			JSONObject json = JSON.parse(content)

			if(!apiObjectService.parseFile(json)){
				render(status:HttpServletResponse.SC_BAD_REQUEST)
				return null
			}
			
			def cache = apiCacheService.getApiCache(json.NAME)

			LinkedHashMap model = [name:cache.name,cacheversion:cache.cacheversion]

			session['cache'] = cache

			webhookService.postData('Iostate', model,'update')
			return ['iostate':model]
		}
	}


	protected boolean isSuperuser() {
		springSecurityService.principal.authorities*.authority.any {
			((List)grailsApplication.config.apitoolkit.admin.roles).contains(it)
		}
	}

	private Map parseFile(JSONObject json){
		String apiObjectName = json.NAME.toString()
		parseJson(json.NAME.toString(),json)
	}

	private Boolean parseJson(String apiName,JSONObject json){
		LinkedHashMap methods = [:]
		json.VERSION.each() { vers ->
			String defaultAction = (vers.value['DEFAULTACTION'])?vers.value.DEFAULTACTION:'index'
			List deprecated = (vers.value.DEPRECATED)?vers.value.DEPRECATED:[]
			String domainPackage = (vers.value.DOMAINPACKAGE!=null || vers.value.DOMAINPACKAGE?.size()>0)?vers.value.DOMAINPACKAGE:null

			String actionname
			vers.value.URI.each() { it ->

				def cache = apiCacheService.getApiCache(apiName.toString())
				//def cache = (temp?.get(apiName))?temp?.get(apiName):[:]

				methods['cacheversion'] = 1

				JSONObject apiVersion = json.VERSION[vers.key]

				actionname = it.key

				net.nosegrind.apiframework.ApiDescriptor apiDescriptor
				//Map apiParams

				String apiMethod = it.value.METHOD
				String apiDescription = it.value.DESCRIPTION
				List apiRoles = it.value.ROLES.DEFAULT
				List batchRoles = it.value.ROLES.BATCH
				List hookRoles = it.value.ROLES.HOOK

				String uri = it.key
				apiDescriptor = createApiDescriptor(apiName, apiMethod, apiDescription, apiRoles, batchRoles, hookRoles, uri, json.get('VALUES'), apiVersion)
				if(!methods[vers.key]){
					methods[vers.key] = [:]
				}

				if(!methods['currentStable']){
					methods['currentStable'] = [:]
					methods['currentStable']['value'] = json.CURRENTSTABLE
				}
				if(!methods[vers.key]['deprecated']){
					methods[vers.key]['deprecated'] = []
					methods[vers.key]['deprecated'] = deprecated
				}

				if(!methods[vers.key]['defaultAction']){
					methods[vers.key]['defaultAction'] = defaultAction
				}

				methods[vers.key][actionname] = apiDescriptor
			}

			if(methods){
				def cache = apiCacheService.setApiCache(apiName,methods)
				//println("apiName : ${apiName} /"+methods[vers.key][actionname]['returns'])
				cache[vers.key].each(){ key1,val1 ->

					if(!['deprecated','defaultAction'].contains(key1)){
						apiCacheService.setApiCache(apiName,key1, val1, vers.key)
					}
				}
			}

		}
	}

	private ApiDescriptor createApiDescriptor(String apiname,String apiMethod, String apiDescription, List apiRoles, List batchRoles, List hookRoles, String uri, JSONObject values, JSONObject json){
		LinkedHashMap<String,ParamsDescriptor> apiObject = [:]
		ApiParams param = new ApiParams()
		LinkedHashMap mocks = [
				"STRING":'Mock String',
				"DATE":'Mock Date',
				"LONG":999,
				"BOOLEAN":true,
				"FLOAT":0.01,
				"BIGDECIMAL":123456789,
				"EMAIL":'test@mockdata.com',
				"URL":'www.mockdata.com',
				"ARRAY":['this','is','mock','data']
		]

		List fkeys = []
		values.each{ k,v ->
			v.type = (v.references)?getKeyType(v.references, v.type):v.type
			if(v.type=='FKEY'){
				fkeys.add(k)
			}

			String references = ''
			String hasDescription = ''
			String hasMockData = mocks[v.type]?mocks[v.type]:''

			param.setParam(v.type,k)

			def configType = grailsApplication.config.apitoolkit.apiobject.type."${v.type}"

			hasDescription = (configType?.description)?configType.description:hasDescription
			hasDescription = (v?.description)?v.description:hasDescription
			if(hasDescription){ param.hasDescription(hasDescription) }

			references = (configType?.references)?configType.references:""
			references = (v?.references)?v.references:references
			if(references){ param.referencedBy(references) }

			hasMockData = (v?.mockData)?v.mockData:hasMockData
			if(hasMockData){ param.hasMockData(hasMockData) }

			// collect api vars into list to use in apiDescriptor
			apiObject[param.param.name] = param.toObject()
		}

		LinkedHashMap receives = getIOSet(json.URI[uri]?.REQUEST,apiObject)
		LinkedHashMap returns = getIOSet(json.URI[uri]?.RESPONSE,apiObject)

		ApiDescriptor service = new ApiDescriptor(
				'empty':false,
				'method':"$apiMethod",
				'fkeys':fkeys,
				'description':"$apiDescription",
				'roles':[],
				'batchRoles':[],
				'hookRoles':[],
				'doc':[:],
				'receives':receives,
				'returns':returns
		)

		service['roles'] = apiRoles
		service['batchRoles'] = (batchRoles)?batchRoles:'permitAll'
		if(hookRoles) {
			service['hookRoles'] = hookRoles
		}

		return service
	}
}
