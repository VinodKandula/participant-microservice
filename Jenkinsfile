node {
    checkout scm

    echo "${env.GIT_COMMIT}"
    echo "${env.GIT_BRANCH}"
    
    stage 'Compile'
    sh './gradlew assemble'

    stage 'Unit test'
    sh './gradlew check'

    stage 'Integration test'
    sh './gradlew integrationTest'

    stage 'Functional test'
    sh './gradlew functionalTest'

    stage 'Merge'
    build 'Participant-microservice-merge'
}