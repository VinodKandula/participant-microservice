node {
    checkout scm

    stage 'Build'
    sh './gradlew assemble'

    stage 'Unit test'
    sh './gradlew check'
}