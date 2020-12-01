def remote = [:]
remote.name = "ubuntu"
remote.host = "160.85.252.170"
remote.allowAnyHosts = true
remote.passphrase = "Mocolococo1!"
node{
        withCredentials([sshUserPrivateKey(credentialsId: 'jenkins', keyFileVariable: 'identity', passphraseVariable:'', usernameVariable: 'ubuntu')]){
            remote.user = ubuntu
            remote.identityFile = identity
            stage('Remote SSH') {
            sshCommand remote: remote, command: "ls"
            sshCommand remote: remote, command: "for i in {1..5}; do echo -n \"Loop \$i \"; date ; sleep 1; done"
        }
    }
}