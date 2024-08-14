pipeline {
    agent { label 'ubuntu20' }

    stages {
        stage('Delete workspace before build starts') {
            steps {
                echo 'Deleting workspace'
                deleteDir()
            }
        }
        stage('Checkout') {
            steps {
                git branch: 'master',
                url: 'https://github.com/shimozukuri/pastebin'

                sh 'pwd'
                sh 'ls -la'
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
        stage('Test') {
            steps {
                sh 'docker build -t pastebin/jenkins-images:0.1 .'
            }
        }
    }
}