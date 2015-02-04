package com.example.jgit.gerrit;

public final class GerritConstants {

    /**
     * The default ssh port for the gerrit server.
     */
    public static final int DEFAULT_GERRIT_SSH_PORT = 29418;

    public static final String PROPERTY_USERNAME = "user.name";
    public static final String PROPERTY_USER_HOME = "user.home";
    /**
     * The default dir to store key-file
     */
    public static final String DEFAULT_PRIVATE_KEY_DIR = ".ssh";

    /**
     * The default key-file to use when authentication to the gerrit server
     */
    public static final String DEFAULT_PRIVATE_KEY = "id_rsa";

    public static final String PROTOCOL_GIT_SSH = "git+ssh";
    public static String GERRIT_HOST = "";
    public static String GERRIT_SERVER = "";
    public static String GERRIT_USER = "";
    public static String GERRIT_USERMAIL = "";
    /**
     * The group that will be the default owner of the newly gerrit project , after initial commit done , should be removed from
     * the owner groups
     */
    public static String GERRIT_GROUP = "";
    /**
     * The path of the private key file that will be used to connect with gerrit and git server
     */
    public static String PRIVATE_KEY_PATH = "";
    /**
     * The timeout settings for exec Gerrit commands
     */
    public static int GERRIT_CMD_BLOCK_TIMEOUT = 0; 

}
