package com.example.ssh;

import java.io.File;

import com.redhat.engineering.gerrit.GerritConstants;

public class SshUtil {

    /**
     * Find private key file under /home/{userName}/.ssh/{privateKeyFileName}, if the parameter privateKeyFileName is not
     * specified then it will be the default one id_rsa.
     * 
     * @param userName
     * @param privateKeyFileName
     * @return
     */
    public static File getPrivateKeyFile(String userName, String privateKeyFileName) {
        System.setProperty(GerritConstants.PROPERTY_USERNAME, userName);
        File privateKeyFile = new File(new File(System.getProperty(GerritConstants.PROPERTY_USER_HOME),
                GerritConstants.DEFAULT_PRIVATE_KEY_DIR), privateKeyFileName == null ? GerritConstants.DEFAULT_PRIVATE_KEY
                : privateKeyFileName);
        return privateKeyFile;
    }
}
