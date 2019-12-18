#!/usr/bin/env groovy

String userHome = System.properties['user.home']

Properties props = new Properties()
def propsFile = new File("${userHome}/.jenkins/workspace/api-framework/gradle.properties")
props.load(propsFile.newDataInputStream())
def buildVersion = props.getProperty('buildVersion')
def patchVersion = props.getProperty('patchVersion')
def apiFrameworkVersion = "${buildVersion}.${patchVersion}"



Properties props2 = new Properties()
def propsFile2 = new File("${userHome}/.jenkins/workspace/beapi-backend/gradle.properties")
props2.load(propsFile2.newDataInputStream())

props2.setProperty('apiFrameworkVersion', apiFrameworkVersion)
props2.store(propsFile2.newWriter(), null)
//props2.close()


