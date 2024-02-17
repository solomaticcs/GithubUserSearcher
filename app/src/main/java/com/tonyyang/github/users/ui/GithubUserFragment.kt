package com.tonyyang.github.users.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tonyyang.github.users.api.GithubService
import com.tonyyang.github.users.databinding.FragmentGithubUserBinding
import com.tonyyang.github.users.viewmodel.UserViewModel
import com.tonyyang.github.users.repository.NetworkState
import com.tonyyang.github.users.repository.UserPagedListRepository

class GithubUserFragment : Fragment() {

    private var _binding: FragmentGithubUserBinding? = null
    private val binding get() = _binding!!

    private val mGithubUserAdapter by lazy {
        GithubUserAdapter(activity as Context)
    }

    private val mUserPagedListRepository by lazy {
        UserPagedListRepository(GithubService.getService())
    }

    private val mUserViewModel by lazy {
        UserViewModel(mUserPagedListRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGithubUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
        initSwipeToRefresh()
        initSearch()
    }

    private fun initAdapter() {
        binding.rvUserList.apply {
            setHasFixedSize(true)
            adapter = mGithubUserAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(
                SeparatorDecoration.Builder(context)
                    .setMargin(16F, 0F)
                    .build()
            )
        }

        mUserViewModel.userPagedList.observe(viewLifecycleOwner) {
            mGithubUserAdapter.submitList(it)
        }
    }

    private fun initSwipeToRefresh() {
        mUserViewModel.refreshState.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = it == NetworkState.LOADING
            if (it != NetworkState.LOADING) {
                showEmptyView(if (mUserViewModel.listIsEmpty()) View.VISIBLE else View.GONE)
            }
        }
        binding.swipeRefresh.setOnRefreshListener {
            mUserViewModel.refresh()
        }
    }

    private fun initSearch() {
        binding.etKeyword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                updatedGithubUsersFromInput()
            }
            false
        }
    }

    private fun updatedGithubUsersFromInput() {
        binding.etKeyword.text.trim().toString().let {
            if (it.isNotEmpty()) {
                if (mUserViewModel.showGithubUser(it)) {
                    binding.rvUserList.scrollToPosition(0)
                    mGithubUserAdapter.submitList(null)
                }
            }
        }
    }

    private fun showEmptyView(visibility: Int) {
        binding.emptyView.root.visibility = visibility
    }

    companion object {
        fun newInstance() = GithubUserFragment()
    }
}
