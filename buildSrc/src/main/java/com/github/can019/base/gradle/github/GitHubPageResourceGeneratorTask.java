package com.github.can019.base.gradle.github;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class GitHubPageResourceGeneratorTask extends DefaultTask {

    public GitHubPageResourceGeneratorTask() {
        setDescription("Generates all resources for GitHub Pages");
        setGroup("GitHub Pages");

        // 의존성 설정
        dependsOn("registerGitHubMetadata");
    }

    @TaskAction
    public void generateResources() {
        getLogger().lifecycle("Generating all resources for GitHub Pages...");
    }
}