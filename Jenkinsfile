pipeline {
    agent any

    environment {
        GITHUB_TOKEN = credentials('assignment-6-adv-java')  // Using Jenkins credentials
        GITHUB_USERNAME = 'PercivalGebashe'  // GitHub Username
        GITHUB_REGISTRY = "ghcr.io/PercivalGebashe"
        IMAGE_NAME = "Library_Management_Server"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', credentialsId: 'PAT', url: 'https://github.com/PercivalGebashe/Library_Management_Server.git'
            }
        }

//         stage('Cache Maven Dependencies') {
//             steps {
//                 cache(path: '$HOME/.m2', key: 'maven-cache') {
//                     bat 'mvn dependency:go-offline --batch-mode'
//                 }
//             }
//         }

        stage('Build') {
                    steps {
                        bat 'mvn clean install -DskipTests'
                    }
                }
        stage('Start Application') {
            steps {
                bat 'start /B java -jar ./lib/Assignment_5_application2-0.0.1-SNAPSHOT.jar'
            }
        }

        stage('Run Tests') {
            steps {
                sh """
until curl -s http://localhost:8082/actuator/health | grep UP; do
    sleep 2
done
"""
sh 'mvn test'
            }
        }

        stage('Build JAR') {
            steps {
                bat 'mvn clean package -DskipTests --batch-mode'  // The JAR will be directly placed in /lib
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def version = sh(script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true).trim()
                    def imageTag = "${GITHUB_REGISTRY}/${IMAGE_NAME}:${version}"

                    bat "docker build -t ${imageTag.toLowerCase()} ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    def version = sh(script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true).trim()
                    def imageTag = "${GITHUB_REGISTRY}/${IMAGE_NAME}:${version}"

                    // Push image to GitHub Container Registry
                    bat "echo $GITHUB_TOKEN | docker login ghcr.io -u $GITHUB_USERNAME --password-stdin"
                    bat "docker push ${imageTag.toLowerCase()}"
                }
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline completed successfully!'
        }
        failure {
            echo '❌ Pipeline failed!'
        }
    }
}
