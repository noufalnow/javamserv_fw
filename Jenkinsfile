pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Deploy Services') {
            steps {
                script {
                    // Define services and their configurations
                    def services = [
                        [name: 'messaging-service', port: 8094],
                        [name: 'tenants-service', port: 8093],
                        [name: 'payments-service', port: 8095]
                    ]

                    // Build and deploy each service
                    for (service in services) {
                        dir(service.name) {
                            echo "Building and Deploying ${service.name}..."
                            sh """
                                mvn clean install
                                docker build -t ${service.name}:latest .
                                docker stop ${service.name} || true
                                docker rm ${service.name} || true
                                docker run -d --restart=always -p ${service.port}:${service.port} --name ${service.name} ${service.name}:latest
                            """
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline execution completed.'
        }
        success {
            echo 'All services were built and deployed successfully.'
        }
        failure {
            echo 'Pipeline failed during the build or deployment process.'
        }
    }
}

