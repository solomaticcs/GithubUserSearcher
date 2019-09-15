package com.tonyyang.github.users.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tonyyang.github.users.R
import com.tonyyang.github.users.model.User
import com.tonyyang.github.users.repository.NetworkState
import kotlinx.android.synthetic.main.user_list_item.view.*


class GithubUserAdapter(private val context: Context) :
        PagedListAdapter<User, GithubUserAdapter.UserViewHolder>(UserDiffUtils()) {

    private var networkState: NetworkState? = null

    private val mInflater by lazy {
        LayoutInflater.from(context)
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            Glide.with(itemView.context).load(user.avatarUrl).into(itemView.civ_user_avatar)
            itemView.tv_user_login_id.text = user.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            UserViewHolder(mInflater.inflate(R.layout.user_list_item, parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    class UserDiffUtils : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}
