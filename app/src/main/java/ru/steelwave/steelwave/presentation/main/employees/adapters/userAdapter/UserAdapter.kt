package ru.steelwave.steelwave.presentation.main.employees.adapters.userAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.steelwave.steelwave.databinding.ItemEmployeeBinding
import ru.steelwave.steelwave.domain.entity.user.UserModel
import ru.steelwave.steelwave.utils.formatPrice

class UserAdapter: ListAdapter<UserModel, UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEmployeeBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            with(item){
                tvNameEmployee.text = lastName + " ${firstName[0]}.${middleName[0]}."
                tvProfEmployee.text = this.position
                ivProfile.setImageBitmap(avatar)
                tvIncome.text = formatPrice(salary) + "$"
            }
        }
    }
}