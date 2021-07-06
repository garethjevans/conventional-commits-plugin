package io.jenkins.plugins.conventionalcommits.utils;

import com.github.zafarkhaja.semver.Version;
import io.jenkins.plugins.conventionalcommits.process.DefaultProcessHelper;
import io.jenkins.plugins.conventionalcommits.process.ProcessHelper;

import java.io.File;
import java.io.IOException;

public class CurrentVersion {

    private ProcessHelper processHelper;

    private Version getCurrentVersionTag(String latestTag){
        return Version.valueOf(latestTag.isEmpty() ? "0.0.0" : latestTag);
    }

    public Version getCurrentVersion(File directory, String latestTag) throws IOException, InterruptedException {

        Version currentVersion;
        ProjectType projectType = ProjectTypeFactory.getProjectType(directory);

        if (projectType != null) {
            if (processHelper == null) {
                processHelper = new DefaultProcessHelper();
            }
            currentVersion = projectType.getCurrentVersion(directory, processHelper);
        } else {
            currentVersion = getCurrentVersionTag(latestTag);
        }

        return  currentVersion;
    }

    public void setProcessHelper(ProcessHelper processHelper) {
        this.processHelper = processHelper;
    }

}
