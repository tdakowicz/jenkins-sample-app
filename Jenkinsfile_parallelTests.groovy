pipeline {
	agent any
	stages {
//		stage('Checkout SCM') {
//			steps {
//				git 'https://github.com/tdakowicz/jenkins-sample-app'
//			}
//		}
		stage('Stash source code') {
			steps {
				stash includes: '**', name: 'sourceCode'
			}
		}
		stage('Build in parallel') {
			parallel {
				stage('Build #1') {
					agent {
						label 'master'
					}
					steps {
						unstash 'sourceCode'
						sh './gradlew clean check --parallel --configure-on-demand --no-daemon'
					}
				}
				stage('Build #2') {
					agent {
						label 'slave'
					}
					steps {
						unstash 'sourceCode'
						sh './gradlew clean check --parallel --configure-on-demand --no-daemon'
					}
				}
			}
		}
		stage('Tests result') {
			steps {
				step([$class: 'JUnitResultArchiver', allowEmptyResults: true, testResults: '*/build/test-results/test/TEST-*.xml'])
			}
		}
		stage('Code coverage') {
			steps {
				jacoco exclusionPattern: '**/generated-sources/**', execPattern: '**/build/jacoco/test.exec'
			}
		}
	}
}