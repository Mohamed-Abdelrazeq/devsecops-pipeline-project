pipeline {
    agent any

    tools {
        maven '3.9.16'
    }

    stages { 
        stage('Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
            
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Code Coverage (JaCoCo)') {
            steps {
                sh 'mvn jacoco:report'
            }

            post {
                always {
                    archiveArtifacts artifacts: 'target/site/jacoco/**', fingerprint: true
                }
            }
        }

        stage('Mutation Testing (PIT)') {
            steps {
                sh 'mvn org.pitest:pitest-maven:mutationCoverage'
            }

            post {
                always {
                      publishHTML([
                        allowMissing: true,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'target/pit-reports',
                        reportFiles: 'index.html',
                        reportName: 'PIT Mutation Report'
                    ])
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }

            post {
                success {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully.'
        }

        failure {
            echo 'Pipeline failed.'
        }
    }
}
