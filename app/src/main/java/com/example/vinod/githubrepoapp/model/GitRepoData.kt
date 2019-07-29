package com.example.vinod.githubrepoapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GitRepoData(@SerializedName("GitRepoDatum") var gitRepoDatum: GitRepoDatum? = null) : Serializable
