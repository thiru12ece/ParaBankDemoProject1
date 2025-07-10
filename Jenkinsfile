pipeline {
  agent any
  tools { maven 'Maven 3' }  // Ensure Maven is configured in Global Tools
  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }
    stage('Build & Test') {
      steps {
        sh 'mvn clean test'
      }
    }
    stage('Publish Extent Report') {
      steps {
        publishHTML([
          reportDir: 'test-output',
          reportFiles: 'ExtentReport.html',
          reportName: 'Extent Report'
        ])
      }
    }
  }
}
