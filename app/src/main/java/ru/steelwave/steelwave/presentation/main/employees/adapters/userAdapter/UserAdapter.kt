package ru.steelwave.steelwave.presentation.main.employees.adapters.userAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.steelwave.steelwave.databinding.ItemEmployeeBinding
import ru.steelwave.steelwave.domain.entity.user.UserModel
import ru.steelwave.steelwave.utils.formatPrice
import ru.steelwave.steelwave.utils.formatName

class UserAdapter: ListAdapter<UserModel, UserViewHolder>(UserDiffCallback()) {

    var onEmployeesClickListener: ((View, UserModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEmployeeBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            with(item){
                tvNameEmployee.text = formatName(item)
                tvProfEmployee.text = this.position
                if(avatar != null) {
                    ivProfile.setImageBitmap(avatar)
                } else {
                    ivProfile.visibility = View.INVISIBLE
                    ivDefaultAvatar.visibility = View.VISIBLE
                }
                tvIncome.text = formatPrice(salary) + "$"
                tvPercent.text = item.percentSalaryProject.toString()+ "%"
                btnMore.btnMore.setOnClickListener {
                    onEmployeesClickListener?.invoke(it, item)
                }
            }
        }
    }
}