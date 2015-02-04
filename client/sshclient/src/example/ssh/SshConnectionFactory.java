package com.example.ssh;

public class SshConnectionFactory {

    private SshConnectionFactory() {
        throw new UnsupportedOperationException("Cannot initialize util classes.");
    }

    public static SshConnection getConnection(String host, int port, SshConfig sshConfig) {
        SshConnection connection = new SshConnectionImpl(host, port, sshConfig);
        return connection;
    }
}
