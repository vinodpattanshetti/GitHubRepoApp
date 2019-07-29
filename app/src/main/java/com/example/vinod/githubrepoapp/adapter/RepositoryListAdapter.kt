package com.example.vinod.githubrepoapp.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.vinod.githubrepoapp.R
import com.example.vinod.githubrepoapp.databinding.RepositoryListItemBinding
import com.example.vinod.githubrepoapp.model.openpullrequests.OpenPullRequest
import com.example.vinod.githubrepoapp.utility.capitalizeFirstLetterOfWords
import com.example.vinod.githubrepoapp.utility.fromHtml

class RepositoryListAdapter(
  private var mGitRepoList: ArrayList<OpenPullRequest>,
  private val mIActivityCommunicator: IActivityCommunicator
) : RecyclerView.Adapter<RepositoryListAdapter.MovieViewHolder>() {
  override fun getItemCount(): Int {
    return mGitRepoList.size
  }

  override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    holder.mBinder?.run {
      tvPullRequestState.text = String.format(
        tvPullRequestState.context.getString(R.string.txt_state),
        mGitRepoList[position].state?.capitalizeFirstLetterOfWords()
      ).fromHtml()
      tvLoginName.text = String.format(
        tvLoginName.context.getString(R.string.txt_login_name),
        mGitRepoList[position].user?.login?.capitalizeFirstLetterOfWords()
      ).fromHtml()
      tvTitle.text = String.format(
        tvTitle.context.getString(R.string.txt_commit_title),
        mGitRepoList[position].title.toString().capitalizeFirstLetterOfWords()
      ).fromHtml()
      tvBody.text = String.format(
        tvBody.context.getString(R.string.txt_body),
        mGitRepoList[position].body.toString().capitalizeFirstLetterOfWords()
      ).fromHtml()
      tvType.text = String.format(
        tvType.context.getString(R.string.txt_type),
        mGitRepoList[position].user?.type.toString().capitalizeFirstLetterOfWords()
      ).fromHtml()
      tvCreatedAndUpdatedAt.text = String.format(
        tvCreatedAndUpdatedAt.context.getString(R.string.txt_pull_request_created_and_updated_date) + mGitRepoList[position].createdAt.toString().capitalizeFirstLetterOfWords(),
        " " + mGitRepoList[position].updatedAt.toString().capitalizeFirstLetterOfWords()
      ).fromHtml()
      tvRequestedReviewers.text = String.format(
        tvRequestedReviewers.context.getString(R.string.txt_reviewers_login_and_id),
        mGitRepoList[position].requestedReviewers.toString().capitalizeFirstLetterOfWords()
      ).fromHtml()
      root.context?.let {
        Glide.with(it).load(mGitRepoList[position].user?.avatarUrl)
          .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
          .into(holder.mBinder.ivImageView.let { mImageView -> mImageView })
      }
      root.setOnClickListener {
        mIActivityCommunicator.onItemSelected(
          mGitRepoList[position], position
        )
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
    val view =
      LayoutInflater.from(parent.context).inflate(R.layout.repository_list_item, parent, false)
    return MovieViewHolder(view)
  }

  inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val mBinder: RepositoryListItemBinding? = DataBindingUtil.bind(itemView)
  }

  interface IActivityCommunicator {
    fun onItemSelected(mGitRepoDatum: OpenPullRequest, position: Int)
  }


}