package com.example.vinod.githubrepoapp.view

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager

import com.example.vinod.githubrepoapp.R
import com.example.vinod.githubrepoapp.adapter.RepositoryListAdapter
import com.example.vinod.githubrepoapp.databinding.ActivityRecyclerViewListBinding
import com.example.vinod.githubrepoapp.model.GitRepoDatum
import com.example.vinod.githubrepoapp.model.openpullrequests.OpenPullRequest

class RecyclerViewListActivity : AppCompatActivity(), RepositoryListAdapter.IActivityCommunicator {

  private lateinit var mBinder: ActivityRecyclerViewListBinding
  private var mAdapter: RepositoryListAdapter? = null
  private var mGitReposList = ArrayList<OpenPullRequest>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mBinder = DataBindingUtil.setContentView(this, R.layout.activity_recycler_view_list)
    mGitReposList = intent.getSerializableExtra("GIT_REPO_LIST") as ArrayList<OpenPullRequest>
    mBinder.tbToolbar.setNavigationOnClickListener {
      finish()
    }
    mBinder.tbToolbar.title = getString(R.string.txt_git_hub_repo_list)
    initAdapter()
  }

  private fun initAdapter() {
    mBinder.rvRepoList.layoutManager =
      LinearLayoutManager(mBinder.root.context, LinearLayoutManager.VERTICAL, false)
    mAdapter = RepositoryListAdapter(mGitReposList, this)
    mBinder.rvRepoList.adapter = mAdapter
  }

  override fun onItemSelected(mGitRepoDatum: OpenPullRequest, position: Int) {

  }

}
