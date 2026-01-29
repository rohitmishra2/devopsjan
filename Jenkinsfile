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

               git branch: 'main', credentialsId: 'rohitmishra2', url: 'https://github.com/rohitmishra2/devopsjan.git'

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

        withCredentials([usernamePassword(credentialsId: 'dockerhubpass',

                                          usernameVariable: 'DOCKER_USER',

                                          passwordVariable: 'DOCKER_PASS')]) {

            bat '''

            docker logout

            echo %DOCKER_PASS%| docker login -u %DOCKER_USER% --password-stdin

            docker tag mvnproj:1.0 %DOCKER_USER%/myapp:latest

            docker push %DOCKER_USER%/myapp:latest

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
