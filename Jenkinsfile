pipeline {
    agent { label 'ubuntu20' }

    parameters {

    }

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
        stage('Test') {
            steps {
                sh 'docker build -t pastebin/jenkins-images:0.1 .'
            }
        }
    }
}