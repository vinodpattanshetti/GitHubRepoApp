package com.example.vinod.githubrepoapp.model.openpullrequests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

data class Commits(@SerializedName("href") @Expose var href: String? = null) : Serializable
