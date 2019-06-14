#!/usr/bin/env groovy



String userHome = System.properties['user.home']

def appVersion = (System.getenv('BEAPI_BUILD_VERSION'))?System.getenv('BEAPI_BUILD_VERSION'):'1'
def patch = System.getenv('BUILD_NUMBER')
def version = "${appVersion}.${patch}"



Properties props2 = new Properties()
FileOutputStream out2 = new FileOutputStream("${userHome}/.jenkins/workspace/beapi-backend/gradle.properties")
FileInputStream in2 = new FileInputStream("${userHome}/.jenkins/workspace/beapi-backend/gradle.properties")

//props2.load(in2)
//props2.remove('patchVersion')
//props2.store(out2, null)

props2.load(in2)
props2.setProperty('apiFrameworkVersion', version)
Enumeration<String> enums = (Enumeration<String>) props2.propertyNames()
println("NAMES:"+enums)
while (enums.hasMoreElements()) {
	String key = enums.nextElement()
	String value = props2.getProperty(key)
	println("### PRPERTIES:"+key + " : " + value)
	props2.setProperty("${key}", value)
}
props2.store(out2, null)
out2.close()




