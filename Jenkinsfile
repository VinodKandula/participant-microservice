node {
    checkout scm

    sh 'echo ${GIT_COMMIT}'
    sh 'echo ${env.GIT_COMMIT}'
    echo '${GIT_COMMIT}'
    echo '${env.GIT_COMMIT}'
    echo '{{env.BRANCH_NAME}}'

    stage 'Compile'
    sh './gradlew assemble'

    stage 'Unit test'
    sh './gradlew check'

    stage 'Merge'
    build 'Participant-microservice-merge'
    
    stage 'Integration test'
    sh './gradlew integrationTest'

    stage 'Functional test'
    sh './gradlew functionalTest'
}