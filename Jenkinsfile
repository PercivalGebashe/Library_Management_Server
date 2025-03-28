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

        stage('Cache Maven Dependencies') {
            steps {
                cache(path: '$HOME/.m2', key: 'maven-cache') {
                    bat 'mvn dependency:go-offline --batch-mode'
                }
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test --batch-mode'  // Runs Rest Assured tests separately
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

                    bat "docker build -t ${imageTag} ."
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
                    bat "docker push ${imageTag}"
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
