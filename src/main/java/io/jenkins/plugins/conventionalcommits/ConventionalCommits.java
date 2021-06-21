package io.jenkins.plugins.conventionalcommits;

import com.github.zafarkhaja.semver.Version;
import java.util.List;
import java.util.stream.Collectors;

public class ConventionalCommits {

    private List<String> filterMergeCommits(List<String> commits) {
        return commits.stream().filter(s -> !s.startsWith("Merge")).collect(Collectors.toList());
    }

    public Version nextVersion(Version in, List<String> commits) {
        List<String> filtered = filterMergeCommits(commits);
        List<String> breaking = filtered.stream().filter(s -> s.contains("!:") || breakingChangeFooter(s) ).collect(Collectors.toList());
        List<String> features = filtered.stream().filter(s -> s.startsWith("feat")).collect(Collectors.toList());

        if (!breaking.isEmpty()) {
            return in.incrementMajorVersion();
        }

        if (!features.isEmpty()) {
            return in.incrementMinorVersion();
        }

        return in.incrementPatchVersion();
    }

    private boolean breakingChangeFooter(String commit){
        int startIndex = commit.lastIndexOf("\n");

        String result = commit;

        if(startIndex!=-1 && startIndex!= commit.length()){
            result = commit.substring(startIndex+1);
        }
        return result.startsWith("BREAKING CHANGE");
    }
}
