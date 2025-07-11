pipeline {
    agent any

    tools {
        maven 'Maven 3'    // Must match Jenkins Maven tool name
        jdk 'JDK 17'       // Optional: if managed by Jenkins
    }

    environment {
        REPORT_DIR = 'test-output'
        REPORT_FILE = 'ExtentReport.html'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean test'
            }
        }

        stage('Publish HTML Report') {
            steps {
                publishHTML([
                    reportDir: "${REPORT_DIR}",
                    reportFiles: "${REPORT_FILE}",
                    reportName: 'Extent Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true
                ])
            }
        }

        stage('Archive Report') {
            steps {
                archiveArtifacts artifacts: "${REPORT_DIR}/${REPORT_FILE}", onlyIfSuccessful: true
            }
        }
    }

   post {
    always {
        echo 'Pipeline completed. Reports published.'
        bat "dir /s /b ${REPORT_DIR}\\ExtentReport.html"

        emailext(
            subject: "Automation Test Report - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
            body: """Hi Team,<br><br>
                    Please find the report for <b>${env.JOB_NAME}</b> build #${env.BUILD_NUMBER}.<br>
                    <a href="${env.BUILD_URL}HTML_Report/">View Report</a><br><br>
                    Regards,<br>Jenkins""",
            mimeType: 'text/html',
            to: 'thiru12ece@gmail.com',
            attachmentsPattern: 'test-output/ExtentReport.html'
        )
    }
}
}
