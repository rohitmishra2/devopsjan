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


        stage('Deploy the project using k8s') {
            steps {
                echo "Running Java Application in k8s"
                bat '''
                   set MINIKUBE_EXE="C:\\Program Files\\Kubernetes\\Minikube\\minikube.exe"

                   %MINIKUBE_EXE% delete
                   %MINIKUBE_EXE% start
                   %MINIKUBE_EXE% status
                   
                   %MINIKUBE_EXE% image load rohit58677/mymvnproj:latest
                   kubectl apply -f deployment.yaml
                   sleep 20
                   kubectl get pods
                   kubectl apply -f services.yaml
                   sleep 10
                   kubectl get services
                   %MINIKUBE_EXE% image ls
	               
	            '''
            }
        }
        stage('Parrallel Loading of services and Dashboard'){
			parallel{
				stage('Run minikube dashboard'){
                    steps{
                        echo "Running minikube dashboard"
                        bat '''
                           set MINIKUBE_EXE="C:\\Program Files\\Kubernetes\\Minikube\\minikube.exe"
                           %MINIKUBE_EXE% addons enable metrics-server
                           %MINIKUBE_EXE% dashboard
                           echo "Dashboard is running"
                        '''
                    }
					
				}
				stage('Run minikube services'){
                    steps{
                        echo "Running minikube services"
                        bat '''
                           set MINIKUBE_EXE="C:\\Program Files\\Kubernetes\\Minikube\\minikube.exe"
                           %MINIKUBE_EXE% service --all
                           echo "All services are running"
                        '''				
				}
			}
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
