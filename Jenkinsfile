def remote = [:]
remote.name = "ubuntu"
remote.host = "160.85.252.170"
remote.allowAnyHosts = true
remote.passphrase = "Mocolococo1!"
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                echo 'Working now!'
            }
        }
        stage('SSH'){
            steps{
                withCredentials([sshUserPrivateKey(credentialsId: 'jenkins', keyFileVariable: 'identity', passphraseVariable:'', usernameVariable: 'ubuntu')]){
                remote.user = ubuntu
                remote.identityFile = identity
                }
            }
        }
        stage('Remote SSH') {
            steps{
                sshCommand remote: remote, command: "source Documents/FuegoGroup/cps/CPS-SORTER/venv/bin/activate\ncps_sorter run-model-eval -i Documents/FuegoGroup/cps/test_scenarios/Master-Thesis-CPS-SORTER/RQ1/Full\\ Road/BeamNG/BeamNG_RF_1_5/beamng_risk_1.5"
                sshCommand remote: remote, command: "ls"
                // sshCommand remote: remote, command: "source Documents/FuegoGroup/cps/CPS-SORTER/venv/bin/activate"
                // sshCommand remote: remote, command: "cps_sorter run-model-eval -i Documents/FuegoGroup/cps/test_scenarios/Master-Thesis-CPS-SORTER/RQ1/Full Road/BeamNG/BeamNG_RF_1_5/beamng_risk_1.5"
            }
        }
    }
}