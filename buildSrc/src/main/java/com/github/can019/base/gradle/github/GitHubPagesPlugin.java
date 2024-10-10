package com.github.can019.base.gradle.github;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class GitHubPagesPlugin implements Plugin<Project>{
    @Override
    public void apply(Project target) {
        target.getTasks().register("generateGitHubPageResources", GitHubPageResourceGeneratorTask.class);
        target.getTasks().register("registerGitHubMetadata", RegisterGitHubMetadataTask.class);
    }
}
