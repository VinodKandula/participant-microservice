node {
    checkout scm

    stage 'Merge'
    sh './gradlew clean'
    sh 'git checkout master'
    sh 'git merge origin/b1'

    stage 'Compile'
    sh './gradlew assemble'

    stage 'Unit test'
    sh './gradlew check'

    stage 'Integration test'
    sh './gradlew integrationTest'

    stage 'Functional test'
    sh './gradlew functionalTest'
}