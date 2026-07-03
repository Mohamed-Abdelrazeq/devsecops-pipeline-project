pipeline {
    agent any

    tools {
        maven '3.9.16'
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}
