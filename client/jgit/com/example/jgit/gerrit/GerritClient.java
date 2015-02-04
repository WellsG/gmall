package com.example.jgit.gerrit;

import java.util.List;

import com.example.ssh.SshException;

public interface GerritClient {

    /**
     * Create a new project and associated Git repository.
     * 
     * Creates a new bare Git repository under gerrit.basePath, using the project name supplied. The newly created repository is
     * empty (has no commits), but is registered in the Gerrit database so that the initial commit may be uploaded for review,
     * or initial content can be pushed directly into a branch.
     * 
     * @param projectName
     * @param ownerGroup
     * @param projectDesc
     * @throws SshException
     */
    public void createProject(String projectName, String ownerGroup, String projectDesc) throws SshException;

    /**
     * List groups visible to caller
     * 
     * @return groups that are visible to the account of the calling user.
     * @throws SshException
     */
    public List<String> listGroups() throws SshException;

}
