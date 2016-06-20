node {
    checkout scm

    stage 'Merge1'
    sh 'git checkout origin/master'
    sh 'git merge origin/branch-pass'

    stage 'Compile'
    sh './gradlew assemble'

    stage 'Unit test'
    sh './gradlew check'

    stage 'Integration test'
    sh './gradlew integrationTest'

    stage 'Functional test'
    sh './gradlew functionalTest'
}