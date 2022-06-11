package io.usmon.registration.presentation.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.usmon.registration.databinding.ListItemUserBinding
import io.usmon.registration.domain.model.User

// Created by Usmon Abdurakhmanv on 6/10/2022.

class UsersListAdapter(
    private val event: (Event) -> Unit
) : RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ListItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBindView(user: User) {
            binding.apply {
                fullName.text = user.fullName
                phoneNumber.text = user.phoneNumber
                user.image?.let { binding.profileImage.setImageBitmap(it) }

                root.setOnClickListener {
                    event(Event.UserClicked(user))
                }
                more.setOnClickListener {
                    event(Event.MoreClicked(user))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindView(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.phoneNumber == newItem.phoneNumber
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.fullName == newItem.fullName && oldItem.phoneNumber == newItem.phoneNumber
        }
    })

    fun submitList(list: List<User>) {
        differ.submitList(list)
    }

    sealed class Event {
        data class UserClicked(val user: User) : Event()
        data class MoreClicked(val user: User) : Event()
    }
}