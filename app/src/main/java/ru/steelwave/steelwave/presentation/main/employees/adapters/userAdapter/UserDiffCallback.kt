package ru.steelwave.steelwave.presentation.main.employees.adapters.userAdapter

import androidx.recyclerview.widget.DiffUtil
import ru.steelwave.steelwave.domain.entity.user.UserModel

class UserDiffCallback: DiffUtil.ItemCallback<UserModel>() {

    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem == newItem
    }
}