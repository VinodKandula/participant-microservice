node {
    checkout scm

    stage 'Compile'
    sh './gradlew assemble'

    stage 'Unit test'
    sh './gradlew check'

    stage 'Merge'
    sh 'git rev-parse HEAD > commit'
    def commit_id = readFile('commit').trim()
    build job: 'Participant-microservice-merge', parameters: [[$class: 'GitParameterValue', name: 'GIT_COMMIT_ID', value: '${commit_id}']]
    
    stage 'Integration test'
    sh './gradlew integrationTest'

    stage 'Functional test'
    sh './gradlew functionalTest'
}