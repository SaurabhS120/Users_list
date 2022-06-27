package com.example.users.users_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.user.users_domain.entities.UsersEntity
import com.example.users.databinding.UserListItemBinding

class UsersListAdapter : ListAdapter<UsersEntity, UsersListAdapter.EntityViewHolder>(callback) {
    inner class EntityViewHolder(val binding : UserListItemBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val binding = UserListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EntityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        val text:String = getItem(position).let { 
            "${it.firstName} ${it.maidenName} ${it.lastName}"
        }
        holder.binding.fullNameTv.setText(text)
    }
    companion object{
        val callback = object :DiffUtil.ItemCallback<UsersEntity>(){
            override fun areItemsTheSame(oldItem: UsersEntity, newItem: UsersEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UsersEntity, newItem: UsersEntity): Boolean {
                return oldItem.id.equals(newItem.id)
            }

        }
    }
}