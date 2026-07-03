pipeline {
    agent any

    tools {
        maven '3.9.16'
    }

    stages {
        stage('Build Artifact - Maven') {
            steps {
                sh 'mvn clean package -DskipTests=true'
                archive 'target/*.jar'
            }
        }
    }
}
