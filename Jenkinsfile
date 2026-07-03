pipeline {
    agent any

    tools {
        maven '3.9.16'
    }

    stages {
        stage('Unit Tests - JUnit & JaCoCo') {
            steps {
                sh 'mvn clean package -DskipTests=true'
                archive 'target/*.jar'
            }
        }
    }
}
