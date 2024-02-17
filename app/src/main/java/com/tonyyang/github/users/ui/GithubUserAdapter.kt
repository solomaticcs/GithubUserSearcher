package com.tonyyang.github.users.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tonyyang.github.users.databinding.UserListItemBinding
import com.tonyyang.github.users.model.User


class GithubUserAdapter(
    private val context: Context
) : PagedListAdapter<User, GithubUserAdapter.UserViewHolder>(UserDiffUtils()) {

    class UserViewHolder(private val binding: UserListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            Glide.with(itemView.context).load(user.avatarUrl).into(binding.civUserAvatar)
            binding.tvUserLoginId.text = user.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(UserListItemBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class UserDiffUtils : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}
