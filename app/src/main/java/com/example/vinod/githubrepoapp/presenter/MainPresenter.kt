package com.example.vinod.githubrepoapp.presenter

import com.example.vinod.githubrepoapp.network.NetworkClient
import com.example.vinod.githubrepoapp.network.NetworkInterface
import com.example.vinod.githubrepoapp.view.MainViewInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(private val mMainViewInterface: MainViewInterface) {

  private val mCompositeDisposable = CompositeDisposable()

  fun fetchGitRepositoryList(inputGitUserName: String, inputGitRepoName: String, state: String) {
    NetworkClient.getRetrofit()?.create(NetworkInterface::class.java)
      ?.getOpenPullRequestsForParticularUser(inputGitUserName, inputGitRepoName, state)
      ?.subscribeOn(
        Schedulers.io()
      )?.observeOn(AndroidSchedulers.mainThread())?.subscribe({
        mMainViewInterface.getRepoDataList(it)
      }, {
        mMainViewInterface.displayError(it.localizedMessage)
      })?.let {
        mCompositeDisposable.add(it)
      }
  }

  fun clearCompositeDisposable() {
    mCompositeDisposable.clear()
  }
}