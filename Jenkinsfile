pipeline {
    agent any
    stages {
        stage('Checkout SCM') {
            steps {
                git credentialsId: 'cc068929-2d84-4f0b-aefd-8bbe5d0f5a94', url: 'https://github.com/tdakowicz/jenkins-sample-app.git'
            }
        }
        stage('Build') {
            steps {
                sh './gradlew clean check --parallel --configure-on-demand --no-daemon'
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