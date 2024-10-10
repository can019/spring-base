package com.github.can019.base.gradle.github;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.TaskAction;

public class RegisterGitHubMetadataTask extends DefaultTask{
    @TaskAction
    public void registerMetadata() {
        String commitId = (String) getProject().findProperty("commitId");
        String releaseVersion = (String) getProject().findProperty("releaseVersion");

        if (commitId == null || releaseVersion == null) {
            throw new GradleException("Commit id 또는 release version이 없습니다");
        }

        getProject().getExtensions().getExtraProperties().set("commitId", commitId);
        getProject().getExtensions().getExtraProperties().set("releaseVersion", releaseVersion);

        getLogger().lifecycle("Commit id stored: " + commitId);
        getLogger().lifecycle("Release version stored: " + releaseVersion);
    }
}
