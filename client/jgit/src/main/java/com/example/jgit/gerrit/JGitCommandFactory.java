package com.example.jgit.gerrit;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.TransportCommand;
import org.eclipse.jgit.lib.PersonIdent;

public class JGitCommandFactory {

    public CloneCommand getCloneCommand() {
        CloneCommand cloneCommand = Git.cloneRepository();
        setTransportConfigCallback(cloneCommand);
        return cloneCommand;
    }

    public PushCommand getPushCommand(Git git) {
        PushCommand pushCommand = git.push();
        setTransportConfigCallback(pushCommand);
        return pushCommand;
    }

    public FetchCommand getFetchCommand(Git git) {
        FetchCommand fetchCommand = git.fetch();
        setTransportConfigCallback(fetchCommand);
        return fetchCommand;
    }

    public CommitCommand getCommitCommand(Git git){
        CommitCommand commitCommand = git.commit();
        PersonIdent gerrit = new PersonIdent(GerritConstants.GERRIT_USER, 
                GerritConstants.GERRIT_USERMAIL);
        commitCommand.setAuthor(gerrit);
        commitCommand.setCommitter(gerrit);
        return commitCommand;
    }

    public void setTransportConfigCallback(TransportCommand command) {
        command.setTransportConfigCallback(new GerritJschConfigCallback());
    }

}
