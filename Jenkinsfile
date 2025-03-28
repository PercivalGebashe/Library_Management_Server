pipeline {
    agent any

    environment {
        GITHUB_REGISTRY = "ghcr.io/your-github-username"
        IMAGE_NAME = "your-repo-name"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/your-username/your-repo.git'
            }
        }

        stage('Cache Maven Dependencies') {
            steps {
                cache(path: '~/.m2', key: 'maven-cache') {
                    sh 'mvn dependency:resolve'
                }
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'  // Runs Rest Assured tests separately
            }
        }

        stage('Build JAR') {
            steps {
                sh 'mvn clean package -DskipTests'  // The JAR will be directly placed in /lib
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def version = sh(script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true).trim()
                    def imageTag = "${GITHUB_REGISTRY}/${IMAGE_NAME}:${version}"

                    sh "docker build -t ${imageTag} ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    def version = sh(script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true).trim()
                    def imageTag = "${GITHUB_REGISTRY}/${IMAGE_NAME}:${version}"

                    // Push image to GitHub Container Registry
                    sh "echo $GITHUB_TOKEN | docker login ghcr.io -u your-username --password-stdin"
                    sh "docker push ${imageTag}"
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