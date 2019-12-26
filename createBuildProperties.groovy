#!/usr/bin/env groovy

//String userHome = System.properties['user.home']

Properties props = new Properties()
def propsFile = new File("/var/lib/jenkins/workspace/api-framework/gradle.properties")
props.load(propsFile.newDataInputStream())
def buildVersion = props.getProperty('buildVersion')
def patchVersion = props.getProperty('patchVersion')
def apiFrameworkVersion = "${buildVersion}.${patchVersion}"

Properties props2 = new Properties()
def propsFile2 = new File("/var/lib/jenkins/workspace/beapi-all-in-one/gradle.properties")
props2.load(propsFile2.newDataInputStream())

//def patch = (System.getenv('BUILD_NUMBER'))?System.getenv('BUILD_NUMBER'):props2.getProperty('patchVersion')

props2.setProperty('apiFrameworkVersion', apiFrameworkVersion)
props2.setProperty('buildVersion', buildVersion)
props2.setProperty('patchVersion', patchVersion)
props2.store(propsFile2.newWriter(), null)


