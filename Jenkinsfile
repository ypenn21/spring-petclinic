#!/usr/bin/groovy
pipeline {

   agent { label 'master' }

   environment {APP_NAME = "java-pet-docker"}

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
				            sh "The environment is ${environment}"
				            InitializeEnv(environment)
				            sh "The environment is ${environment}"
				            sh "The variable is ${SERVER}"
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