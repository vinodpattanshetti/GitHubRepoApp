package com.example.vinod.githubrepoapp.network

import com.example.vinod.githubrepoapp.model.GitRepoDatum
import com.example.vinod.githubrepoapp.model.openpullrequests.OpenPullRequest
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkInterface {

  @GET("repos/{owner}/{repo}/pulls") fun getOpenPullRequestsForParticularUser(
    @Path("owner") owner: String?, @Path("repo") repo: String?, @Query("state") state: String
  ): Observable<ArrayList<OpenPullRequest>?>

  @GET("users/{username}/repos") fun getRepo(
    @Path("username") username: String?
  ): Observable<ArrayList<GitRepoDatum>>

}