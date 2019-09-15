package com.tonyyang.github.users.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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
        initAdapter()
        initSwipeToRefresh()
        initSearch()
    }

    private fun initAdapter() {
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

        mUserViewModel.userPagedList.observe(this, Observer {
            mGithubUserAdapter.submitList(it)
        })

        mUserViewModel.networkState.observe(this, Observer {
            mGithubUserAdapter.setNetworkState(it)
        })
    }

    private fun initSwipeToRefresh() {
        mUserViewModel.refreshState.observe(this, Observer {
            swipe_refresh.isRefreshing = it == NetworkState.LOADING
            if (it != NetworkState.LOADING) {
                showEmptyView(if (mUserViewModel.listIsEmpty()) View.VISIBLE else View.GONE)
            }
        })
        swipe_refresh.setOnRefreshListener {
            mUserViewModel.refresh()
        }
    }

    private fun initSearch() {
        et_keyword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                updatedGithubUsersFromInput()
            }
            false
        }
    }

    private fun updatedGithubUsersFromInput() {
        et_keyword.text.trim().toString().let {
            if (it.isNotEmpty()) {
                if (mUserViewModel.showGithubUser(it)) {
                    rv_user_list.scrollToPosition(0)
                    mGithubUserAdapter.submitList(null)
                }
            }
        }
    }

    private fun showEmptyView(visibility: Int) {
        empty_view.visibility = visibility
    }

    companion object {
        fun newInstance() = GithubUserFragment()
    }
}
