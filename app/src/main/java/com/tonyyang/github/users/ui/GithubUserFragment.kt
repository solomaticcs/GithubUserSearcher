package com.tonyyang.github.users.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tonyyang.github.users.R
import com.tonyyang.github.users.StringProvider
import com.tonyyang.github.users.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_github_user.*

class GithubUserFragment : Fragment() {

    private val mAdapter by lazy {
        GithubUserAdapter(activity as Context)
    }

    private val stringProvider by lazy {
        StringProvider(activity as Context)
    }

    private val userViewModel by lazy {
        UserViewModel(stringProvider)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_github_user, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_user_list.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(
                    SeparatorDecoration.Builder(context)
                            .setMargin(16F, 0F)
                            .build()
            )
        }

        userViewModel.getUsers().observe(this, Observer {
            Log.d(TAG, "Response Data: %s".format(it))
            mAdapter.updateUserList(it.items)
        })

        userViewModel.getIsUpdating().observe(this, Observer { visible ->
            (if (visible) View.VISIBLE else View.GONE).let {
                progress_background.visibility = it
                progress_circle.visibility = it
            }
        })

        userViewModel.getToastMessage().observe(this, Observer { message ->
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        })

        et_keyword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                userViewModel.searchUsers(et_keyword.text.toString())
            }
            false
        }
    }

    companion object {
        fun newInstance() = GithubUserFragment()
        private val TAG = GithubUserFragment::class.java.simpleName
    }
}
