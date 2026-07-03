pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('sonar-token')
    }

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
                    archiveArtifacts artifacts: 'target/pit-reports/**', allowEmptyArchive: true
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

        stage('SonarQube - SAST') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh """
                    mvn sonar:sonar \
                        -Dsonar.projectKey=numeric \
                        -Dsonar.host.url=http://sonarqube:9000 \
                        -Dsonar.login=${SONAR_TOKEN}
                    """
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('SCA') {
            steps {
                sh 'mvn dependency-check:check'
            }
            post {
                always {
                dependencyCheckPublisher pattern: 'target/dependency-check-report.xml'
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
