package com.tonyyang.github.users.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tonyyang.github.users.R
import com.tonyyang.github.users.data.model.User
import de.hdodenhof.circleimageview.CircleImageView


class GithubUserAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mInflater by lazy {
        LayoutInflater.from(context)
    }

    private val mUserList by lazy {
        mutableListOf<User>()
    }

    fun updateUserList(userList: List<User>) {
        mUserList.apply {
            clear()
            addAll(userList)
        }
        notifyDataSetChanged()
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: CircleImageView = itemView.findViewById(R.id.civ_user_avatar)
        val loginId: TextView = itemView.findViewById(R.id.tv_user_login_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_EMPTY) {
            return object : RecyclerView.ViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.empty_view, parent, false)) {}
        }
        return UserViewHolder(
                mInflater.inflate(
                        R.layout.item_user,
                        parent,
                        false
                )
        )
    }

    override fun getItemCount():Int {
        if (mUserList.size == 0) {
            return 1
        }
        return mUserList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserViewHolder) {
            mUserList[position].let { user ->
                Picasso.get().load(user.avatarUrl).into(holder.avatar)
                holder.loginId.text = user.login
            }
        }
    }

    override fun getItemViewType(position: Int) = if (mUserList.size == 0) VIEW_TYPE_EMPTY else VIEW_TYPE_ITEM

    companion object {
        private const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_EMPTY = 0
    }
}
