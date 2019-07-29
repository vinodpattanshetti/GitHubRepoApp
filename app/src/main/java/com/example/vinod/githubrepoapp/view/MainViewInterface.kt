package com.example.vinod.githubrepoapp.view

import com.example.vinod.githubrepoapp.model.GitRepoData
import com.example.vinod.githubrepoapp.model.GitRepoDatum
import com.example.vinod.githubrepoapp.model.openpullrequests.OpenPullRequest

interface MainViewInterface {

  fun showToast(s: String)
  fun getRepoDataList(mOpenPullRequest: ArrayList<OpenPullRequest>?)
  fun displayError(s: String)
}