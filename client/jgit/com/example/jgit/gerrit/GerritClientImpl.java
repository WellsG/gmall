package com.example.jgit.gerrit;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.example.ssh.SshConfig;
import com.example.ssh.SshConnection;
import com.example.ssh.SshConnectionFactory;
import com.example.ssh.SshException;

public class GerritClientImpl implements GerritClient {

    private final String hostName;
    private final int port;
    private final SshConfig sshConfig;

    /**
     * gerrit create-project
     * 
     * Creates a new bare Git repository under gerrit.basePath, using the project name supplied. The newly created repository is
     * empty (has no commits), but is registered in the Gerrit database so that the initial commit may be uploaded for review,
     * or initial content can be pushed directly into a branch.
     */
    private static String CREATE_PROJECT_CMD = "gerrit create-project -n %s.git -o %s -o %s -d '%s'";

    /**
     * gerrit ls-groups
     * 
     * Displays the list of group names, one per line, that are visible to the account of the calling user.
     */
    private static String LS_GROUPS_CMD = "gerrit ls-groups";

    public GerritClientImpl(String userName, String gerritHostName, String privateKeyFilePath) {
        this(userName, gerritHostName, GerritConstants.DEFAULT_GERRIT_SSH_PORT, privateKeyFilePath);
    }

    public GerritClientImpl(String userName, String gerritHostName, int gerritPort, String privateKeyFilePath) {
        this(userName, gerritHostName, gerritPort, new File(privateKeyFilePath));
    }

    public GerritClientImpl(String userName, String gerritHostName, int gerritPort, File privateKeyFile) {
        hostName = gerritHostName;
        port = gerritPort;
        sshConfig = new SshConfig();
        sshConfig.setUsername(userName);
        sshConfig.setPrivateKeyFile(privateKeyFile);
    }

    public void createProject(String projectName, String ownerGroup, String projectDesc) throws SshException {
        SshConnection client = getSshConnection();
        try {
            client.executeCommand(String.format(CREATE_PROJECT_CMD, projectName, GerritConstants.GERRIT_GROUP, ownerGroup,
                    projectDesc), GerritConstants.GERRIT_CMD_BLOCK_TIMEOUT);
        } catch (SshException e) {
            throw e;
        } finally {
            client.disconnect();
        }
    }

    public List<String> listGroups() throws SshException {
        SshConnection client = getSshConnection();
        String result = null;
        try {
            result = client.executeCommand(LS_GROUPS_CMD, GerritConstants.GERRIT_CMD_BLOCK_TIMEOUT);
            List<String> groups = Arrays.asList(result.split(System.lineSeparator()));
            return groups;
        } catch (SshException e) {
            throw e;
        } finally {
            client.disconnect();
        }
    }

    private SshConnection getSshConnection() throws SshException {
        SshConnection client = SshConnectionFactory.getConnection(hostName, port, sshConfig);
        client.connect();
        return client;
    }

}
