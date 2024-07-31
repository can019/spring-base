package com.github.can019.test.util.report;

public enum ReportPath {
    ROOT_PATH("build/test-reports");

    private String path;

    ReportPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
