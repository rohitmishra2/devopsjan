
pipeline {
    agent any
     tools{
         jdk 'Java17'
         maven 'Maven'
    }
    stages {
        stage('Checkout Code') {
            steps {
               echo "Pulling from GITHUB repository"
               git branch: 'main', credentialsId: 'mygithubcred', url: 'https://github.com/chntraining/devopsjan.git'
            }
        }
         stage('Test the Project') {
            steps {
               echo "Test my JAVA project"
               bat 'mvn clean test' 
            }
              post {
                  always {
                         junit '**/target/surefire-reports/*.xml'
                         echo 'Test Run succeeded!'          
					}
				}
		}
        stage('Build Project') {
            steps {
               echo "Building my JAVA project"
               bat 'mvn clean package -DskipTests' 
            }
        }
        stage(' Build the Docker Image') {
            steps {
               echo "Build the Docker Image for mvn project"
               bat 'docker build -t mvnproj:1.0 .'
            }
        }
         stage('Push Docker Image to DockerHub') {
            steps {
               echo "Push Docker Image to DockerHub for mvn project"
                 withCredentials([string(credentialsId: 'dockerhubpwd', variable: 'DOCKER_PASS')]) {
                         bat '''
   	        echo %DOCKER_PASS% | docker login -u deepikkaa20 --password-stdin
                         docker tag mvnproj:1.0 deepikkaa20/mymvnproj:latest
                         docker push deepikkaa20/mymvnproj:latest
                         '''
                  }
            }
        }
       
        stage('Deploy the project using Container') {
            steps {
                echo "Running Java Application"
            }
        }
    }

    post {
        success {
            echo 'I succeeded!'
           
        }
        failure {
            echo 'Failed........'
        }
    }
}