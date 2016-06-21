node {
    checkout scm

    echo env.BRANCH_NAME

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