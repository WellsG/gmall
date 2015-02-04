package com.example.jgit.gerrit;

import com.example.util.PropertiesUtils;

public class GerritClientUtil {

    static final GerritClient client;
    static {
        GerritConstants.GERRIT_USER = PropertiesUtils.getValue("gerrit.User");
        GerritConstants.GERRIT_USERMAIL = PropertiesUtils.getValue("gerrit.UserMail");
        GerritConstants.GERRIT_HOST = PropertiesUtils.getValue("gerrit.Host");
        GerritConstants.GERRIT_GROUP = PropertiesUtils.getValue("gerrit.Group");
        GerritConstants.PRIVATE_KEY_PATH = PropertiesUtils.getValue("example.PrivateKeyPath");
        GerritConstants.GERRIT_SERVER = GerritConstants.PROTOCOL_GIT_SSH + "://" + GerritConstants.GERRIT_USER + "@"
                + GerritConstants.GERRIT_HOST + ":" + GerritConstants.DEFAULT_GERRIT_SSH_PORT + "/";
        GerritConstants.GERRIT_CMD_BLOCK_TIMEOUT = PropertiesUtils.getIntValue("gerrit.cmd.timeout");
        client = new GerritClientImpl(GerritConstants.GERRIT_USER, GerritConstants.GERRIT_HOST,
                GerritConstants.PRIVATE_KEY_PATH);
    }

    public static GerritClient setUp() {
        return client;
    }
}
