#!/usr/bin/groovy
pipeline {

   agent { label 'master' }

    environment {
		APP_NAME = "java-pet-docker"
		SOURCE_CONTEXT_DIR = ""
        JENKINS_TAG = "latest"
        UBER_JAR_CONTEXT_DIR = "target/"
        PROTOBIN_CONTEXT_DIR = "target/protobin/"
        UBER_JAR_FILE="target/spring-petclinic-2.2.0.BUILD-SNAPSHOT.jar"
        OCP_API_SERVER = "${OPENSHIFT_API_URL}"
        OCP_TOKEN = readFile('/var/run/secrets/kubernetes.io/serviceaccount/token').trim()
        APPLIER_SKIP_TAGS = "bitbucket-jenkins-webhook"
        APPLIER_TARGET = "app"
        ARTIFACTORY_DEV_REPO = "omnitracs-dev-images.jfrog.io"
        DEV_REPO_KEY = "dev-images"
        ARTIFACTORY_SECRET_NAME = "${CI_CD_NAMESPACE}-artifactory-access-token"
	}

	options {
        buildDiscarder(logRotator(numToKeepStr:'10'))
        timeout(time: 60, unit: 'MINUTES')
        ansiColor('xterm')
        timestamps()
    }

   stages{

        stage("Build, Bake, and Deploy") {

            agent { label 'jenkins-slave-mvn35' }

            stages{

                stage('Build'){
		       		steps{
		            	sh "echo test"
		            }
		        }

		        stage('Bake'){
		            steps{
		                script{
		                    openshift.loglevel(5)
		                    timeout(15) { // in minutes
			                    openshift.withCluster () {
			                        def buildSelector = openshift.startBuild( "${APP_NAME} --from-file=${UBER_JAR_FILE}" )
			                        buildSelector.logs('-f')
			                    }
                            }
		                }
		            }
		        }

            }
        }
    }
}
