package com.tonyyang.github.users.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tonyyang.github.users.R
import com.tonyyang.github.users.api.GithubService
import com.tonyyang.github.users.viewmodel.UserViewModel
import com.tonyyang.github.users.repository.NetworkState
import com.tonyyang.github.users.repository.UserPagedListRepository
import kotlinx.android.synthetic.main.fragment_github_user.*

class GithubUserFragment : Fragment() {

    private val mGithubUserAdapter by lazy {
        GithubUserAdapter(activity as Context)
    }

    private val mUserPagedListRepository by lazy {
        UserPagedListRepository(GithubService.getService())
    }

    private val mUserViewModel by lazy {
        UserViewModel(mUserPagedListRepository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_github_user, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_user_list.apply {
            setHasFixedSize(true)
            adapter = mGithubUserAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(
                    SeparatorDecoration.Builder(context)
                            .setMargin(16F, 0F)
                            .build()
            )
        }

        et_keyword.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchUserByKeyword(v.text.toString())
            }
            false
        }
    }

    private fun searchUserByKeyword(query: String) {
        mUserViewModel.replaceSubscription(this, query)
        startUserObserver()
    }

    private fun startUserObserver() {
        mUserViewModel.userPagedList?.observe(this, Observer {
            mGithubUserAdapter.submitList(it)
        })
        mUserViewModel.networkState?.observe(this, Observer {
            Toast.makeText(activity, it.message.msg, Toast.LENGTH_SHORT).show()
            if (it == NetworkState.LOADING) {
                showProgress(View.VISIBLE)
            } else {
                showProgress(View.GONE)
                showEmptyView(if (mUserViewModel.listIsEmpty()) View.VISIBLE else View.GONE)
            }
        })
    }

    private fun showProgress(visibility: Int) {
        progress_background.visibility = visibility
        progress_circle.visibility = visibility
    }

    private fun showEmptyView(visibility: Int) {
        empty_view.visibility = visibility
    }

    companion object {
        fun newInstance() = GithubUserFragment()
    }
}
