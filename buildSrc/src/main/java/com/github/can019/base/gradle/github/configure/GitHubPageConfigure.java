package com.github.can019.base.gradle.github.configure;

import lombok.Getter;

@Getter
public class GitHubPageConfigure {
    private MetaData metaData;
    private GithubPageFileTree githubPageFileTree;
}

@Getter
class MetaData {
    private String releaseVersion;
    private String commitId;
}

class GithubPageFileTree{
    private String markUpFileName;
    private String rootDirPath;

    public String getRootMarkUpFilePath() {
        return String.join("/", rootDirPath, markUpFileName);
    }
}