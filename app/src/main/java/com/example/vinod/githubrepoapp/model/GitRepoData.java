package com.example.vinod.githubrepoapp.model;

import com.google.gson.annotations.SerializedName;

public class GitRepoData {

    @SerializedName("GitRepoDatum")
    private GitRepoDatum mGitRepoDatum;

    public GitRepoDatum getGitRepoDatum() {
        return mGitRepoDatum;
    }

    public void setGitRepoDatum(GitRepoDatum gitRepoDatum) {
        mGitRepoDatum = gitRepoDatum;
    }
}
