package com.example.users.users_list

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.user.users_domain.entities.UsersEntity
import com.example.users.R
import com.example.users.databinding.UserListItemBinding

class UsersListAdapter(val selctedUserVar: MutableLiveData<Int>, val theme: Resources.Theme) :
    ListAdapter<UsersEntity, UsersListAdapter.EntityViewHolder>(callback) {
    inner class EntityViewHolder(val binding: UserListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val binding =
            UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EntityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        holder.binding.enyity = getItem(position)
        holder.binding.circularCard.setCardBackgroundColor(
            ResourcesCompat.getColor(
                holder.binding.root.resources,
                when (position % 5) {
                    0 -> R.color.card_red
                    1 -> R.color.card_orange
                    2 -> R.color.card_purple
                    3 -> R.color.card_green
                    4 -> R.color.card_blue
                    else -> R.color.card_red
                }, theme
            )
        )
        holder.binding.root.setOnClickListener {
            selctedUserVar.postValue(position)
        }


    }

    companion object {
        val callback = object : DiffUtil.ItemCallback<UsersEntity>() {
            override fun areItemsTheSame(oldItem: UsersEntity, newItem: UsersEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UsersEntity, newItem: UsersEntity): Boolean {
                return oldItem.id.equals(newItem.id)
            }

        }
    }
}