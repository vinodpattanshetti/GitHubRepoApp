package com.example.vinod.githubrepoapp.view

import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TextInputLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.example.vinod.githubrepoapp.R
import com.example.vinod.githubrepoapp.databinding.ActivityMainBinding
import com.example.vinod.githubrepoapp.model.openpullrequests.OpenPullRequest
import com.example.vinod.githubrepoapp.presenter.MainPresenter
import com.example.vinod.githubrepoapp.utility.hideView
import com.example.vinod.githubrepoapp.utility.orDefaultInt
import com.example.vinod.githubrepoapp.utility.showView

class GitHubInputActivity : AppCompatActivity(), MainViewInterface {
  private lateinit var mBinder: ActivityMainBinding
  private var mMainPresenter: MainPresenter? = null
  private var mHandler: Handler? = null
  private var mSearchRunnable: Runnable? = null

  private var mOpenPullRequest: ArrayList<OpenPullRequest>? = null
  private var mIsFocusNotYetTriggered = true

  private var gitHubUserName: String = ""
  private var gitHubRepositoryName: String = ""

  companion object {
    const val KEYSTROKE_TIME_DELAY: Long = 50
    const val GIT_REPO_LIST_KEY = "GIT_REPO_LIST"
    const val OPEN_KEY = "open"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mBinder = DataBindingUtil.setContentView(this, R.layout.activity_main)
    mHandler = Handler()
    mMainPresenter = MainPresenter(this@GitHubInputActivity)
    initViews()
  }

  private fun initViews() {
    mBinder.run {
      tbToolbar.setNavigationOnClickListener {
        finish()
      }
      tbToolbar.title = getString(R.string.txt_git_hub_main_activity)
      mOpenPullRequest = arrayListOf()
      etInputUserName.addTextChangedListener(object : TextWatcher {
        override fun onTextChanged(
          mCharSequence: CharSequence?, start: Int, count: Int, after: Int
        ) {
          mBinder.etInputUserName.background.mutate().setColorFilter(
            ContextCompat.getColor(this@GitHubInputActivity, R.color.color_red),
            PorterDuff.Mode.SRC_ATOP
          )
          mBinder.etInputUserName.clearTick()
        }

        override fun beforeTextChanged(
          CharSequence: CharSequence?, start: Int, count: Int, after: Int
        ) {
          //Implementation is not required
        }

        override fun afterTextChanged(mEditable: Editable?) {
          if (etInputUserName.text?.length.orDefaultInt() >= 2) {
            if (mSearchRunnable != null) {
              mHandler?.removeCallbacks(mSearchRunnable)
            }
            mSearchRunnable = Runnable {
              if (etInputUserName.text.toString().isNotEmpty()) {
                gitHubUserName = etInputUserName.text.toString()
              }
            }
            mHandler?.postDelayed(mSearchRunnable, KEYSTROKE_TIME_DELAY)
          } else if (etInputUserName.text.toString().isEmpty()) {
            Toast.makeText(
              this@GitHubInputActivity,
              getString(R.string.txt_add_git_hub_user_name),
              Toast.LENGTH_LONG
            ).show()
          }
        }
      })

      etInputRepoName.addTextChangedListener(object : TextWatcher {
        override fun onTextChanged(
          mCharSequence: CharSequence?, start: Int, count: Int, after: Int
        ) {
          mBinder.etInputRepoName.background.mutate().setColorFilter(
            ContextCompat.getColor(this@GitHubInputActivity, R.color.color_red),
            PorterDuff.Mode.SRC_ATOP
          )
          mBinder.etInputRepoName.clearTick()
        }

        override fun beforeTextChanged(
          CharSequence: CharSequence?, start: Int, count: Int, after: Int
        ) {
          //Implementation is not required
        }

        override fun afterTextChanged(mEditable: Editable?) {
          if (etInputRepoName.text?.length.orDefaultInt() >= 2) {
            if (mSearchRunnable != null) {
              mHandler?.removeCallbacks(mSearchRunnable)
            }
            mSearchRunnable = Runnable {
              if (etInputRepoName.text.toString().isNotEmpty()) {
                gitHubRepositoryName = etInputRepoName.text.toString()
              }
            }
            mHandler?.postDelayed(mSearchRunnable, KEYSTROKE_TIME_DELAY)
          } else if (etInputRepoName.text.toString().isEmpty()) {
            Toast.makeText(
              this@GitHubInputActivity,
              getString(R.string.txt_add_git_hub_repo_name),
              Toast.LENGTH_LONG
            ).show()
          }
        }
      })
    }

    mBinder.btSubmit.setOnClickListener {
      mBinder.pbProgress.showView()
      mBinder.etInputRepoName.onEditorAction(EditorInfo.IME_ACTION_DONE)
      mBinder.etInputUserName.onEditorAction(EditorInfo.IME_ACTION_DONE)
      mMainPresenter?.fetchGitRepositoryList(
        gitHubUserName, gitHubRepositoryName, OPEN_KEY
      )
    }
  }

  override fun getRepoDataList(mOpenPullRequest: ArrayList<OpenPullRequest>?) {
    this.mOpenPullRequest?.clear()
    mOpenPullRequest?.let { this.mOpenPullRequest?.addAll(it) }

    if (!mOpenPullRequest.isNullOrEmpty()) {
      mIsFocusNotYetTriggered = true
      initRecyclerViewList(mOpenPullRequest)
      setTickOnBothViews(mBinder.etInputUserName, mBinder.etInputUserName)
      setTickOnBothViews(mBinder.etInputRepoName, mBinder.etInputRepoName)
    } else {
      Toast.makeText(
        this, getString(R.string.txt_warning_msg), Toast.LENGTH_LONG
      ).show()
    }
    mBinder.pbProgress.hideView()
  }

  private fun setTickOnBothViews(etInputUserName: EditText, etRepoName: EditText) {
    etInputUserName.showTick()
    etRepoName.background.mutate()
      .setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP)
  }

  private fun initRecyclerViewList(mOpenPullRequest: ArrayList<OpenPullRequest>?) {
    val mIntent = Intent(this, RecyclerViewListActivity::class.java)
    mIntent.putExtra(GIT_REPO_LIST_KEY, mOpenPullRequest)
    startActivity(mIntent)
  }

  private fun EditText.showTick() {
    this.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.vector_green_tick_sharp_icon, 0)
  }

  private fun EditText.clearTick() {
    this.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
  }

  override fun showToast(s: String) {
    Toast.makeText(this, getString(R.string.txt_something_went_wrong), Toast.LENGTH_LONG).show()
  }

  override fun displayError(s: String) {
    Toast.makeText(this, getString(R.string.txt_something_went_wrong), Toast.LENGTH_SHORT).show()
  }

  override fun onDestroy() {
    mMainPresenter?.clearCompositeDisposable()
    super.onDestroy()
  }
}
