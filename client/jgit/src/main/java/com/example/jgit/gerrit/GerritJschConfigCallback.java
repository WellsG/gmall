package com.example.jgit.gerrit;

import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;

/**
 * Receives a callback allowing type-specific configuration to be set on the Transport instance after it's been created.
 * 
 * For gerrit , it needs to replace the SshSessionFactorys with GerritJschConfigSessionFactory on any SSHTransport used (eg to
 * control available SSH identities), they can set the TransportConfigCallback on the JGit API command - once the transport has
 * been created by the command, the callback will be invoked and passed the transport instance, which the client can then
 * inspect and configure as necessary.
 * 
 */
public class GerritJschConfigCallback implements TransportConfigCallback {

    @Override
    public void configure(Transport transport) {
        if (transport instanceof SshTransport) {
            SshTransport sshTransport = (SshTransport) transport;
            sshTransport.setSshSessionFactory(new GerritJschConfigSessionFactory());
        }
    }

}
