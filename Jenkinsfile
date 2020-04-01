#!/usr/bin/groovy
import org.citi.Properties
echo Properties.server()

setEnv()

pipeline {

   agent { label 'master' }

   environment {
        MY_SERVER = org.citi.Properties.server()
        MY_MAP = "test"
   }

	options {
        buildDiscarder(logRotator(numToKeepStr:'10'))
        timeout(time: 60, unit: 'MINUTES')
        ansiColor('xterm')
    }

   stages{

        stage("Build, Bake, and Deploy") {
            agent { label 'maven' }

            stages{

		        stage("Verify Environment variable "){
				    steps{
				        script {
				            sh "echo The APP_NAME is ${APP_NAME}"
				            sh "echo The TAG is ${TAG}"
				            sh "echo The YANNI is ${YANNI}"
				            def map = test()
				            def globalVar = Properties.myGlobalVar
				            sh "echo The variable MY_SERVER is ${MY_SERVER}"
				            sh "echo The variable globalVar is ${globalVar}"
				            sh "echo The variable map is ${map}"
				            sh "echo The variable Properties.server() is ${Properties.server()}"
				            sh "echo The variable Properties.myGlobalVar is ${Properties.myGlobalVar}"
				            sh "echo ${MY_MAP}"
				            sh "echo The variable is ${map}"
				       	}
				    }
			    }

                stage('Build'){
		       		steps{
		            	sh "echo testing maven build"
		            	sh "mvn clean install -DskipTests"
		            }
		        }

		        stage('Bake'){
		            steps{
		                script{
		                    sh "echo baking"
		                    sh "pwd"
		                    openshift.loglevel(5)
		                    timeout(15) { // in minutes
			                    openshift.withCluster () {
			                        def buildSelector = openshift.startBuild( "java-docker --from-dir=." )
			                        buildSelector.logs('-f')
			                    }
                            }
		                }
		            }
		        }

		    	stage("Verify Deployment"){
				    steps{
				        script {
				        	timeout(2) { // in minutes
					        	verifyDeployment("pet-clinc", "yanni-test")
		                    }
				       	}
				    }
				}

            }
        }
    }
}
