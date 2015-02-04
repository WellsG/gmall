package com.example.jgit.gerrit;

import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.util.FS;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.example.ssh.BlindHostKeyRepository;

/**
 * The base session factory for gerrit that set BlindHostKeyRepository and private keys from $HOME/.ssh.
 * 
 */
public class GerritJschConfigSessionFactory extends JschConfigSessionFactory {

    @Override
    protected void configure(Host hc, Session session) {

    }

    @Override
    protected JSch getJSch(Host hc, FS fs) throws JSchException {
        JSch jsch = super.getJSch(hc, fs);
        System.setProperty(GerritConstants.PROPERTY_USERNAME, GerritConstants.GERRIT_USER);
        jsch.addIdentity(GerritConstants.PRIVATE_KEY_PATH);
        jsch.setHostKeyRepository(new BlindHostKeyRepository());
        return jsch;
    }
}
