pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = "your-docker-registry" // Replace with your Docker registry
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://your-repo-url.git'
            }
        }

        stage('Build and Deploy Messaging Service') {
            steps {
                dir('messaging-service') {
                    sh 'mvn clean install'
                    sh 'docker build -t messaging-service:latest .'
                    sh 'docker tag messaging-service:latest ${DOCKER_REGISTRY}/messaging-service:latest'
                    sh 'docker push ${DOCKER_REGISTRY}/messaging-service:latest'
                    sh 'docker stop messaging-service || true && docker rm messaging-service || true'
                    sh 'docker run -d -p 8094:8094 --name messaging-service ${DOCKER_REGISTRY}/messaging-service:latest'
                }
            }
        }

        stage('Build and Deploy Tenants Service') {
            steps {
                dir('tenants-service') {
                    sh 'mvn clean install'
                    sh 'docker build -t tenants-service:latest .'
                    sh 'docker tag tenants-service:latest ${DOCKER_REGISTRY}/tenants-service:latest'
                    sh 'docker push ${DOCKER_REGISTRY}/tenants-service:latest'
                    sh 'docker stop tenants-service || true && docker rm tenants-service || true'
                    sh 'docker run -d -p 8093:8093 --name tenants-service ${DOCKER_REGISTRY}/tenants-service:latest'
                }
            }
        }

        stage('Build and Deploy Payments Service') {
            steps {
                dir('payments-service') {
                    sh 'mvn clean install'
                    sh 'docker build -t payments-service:latest .'
                    sh 'docker tag payments-service:latest ${DOCKER_REGISTRY}/payments-service:latest'
                    sh 'docker push ${DOCKER_REGISTRY}/payments-service:latest'
                    sh 'docker stop payments-service || true && docker rm payments-service || true'
                    sh 'docker run -d -p 8095:8095 --name payments-service ${DOCKER_REGISTRY}/payments-service:latest'
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline completed.'
        }
        success {
            echo 'All services built and deployed successfully.'
        }
        failure {
            echo 'One or more services failed to build or deploy.'
        }
    }
}

