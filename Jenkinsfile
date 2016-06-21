node {
    checkout scm

    sh 'git rev-parse HEAD > commit'
    def commit = readFile('commit').trim()
    echo commit

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