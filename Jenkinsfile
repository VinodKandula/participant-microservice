node {
    checkout scm

    stage 'Compile'
    sh './gradlew assemble'

    stage 'Unit test'
    sh './gradlew check'

    stage 'Integration test'
    sh './gradlew integrationTest'
}