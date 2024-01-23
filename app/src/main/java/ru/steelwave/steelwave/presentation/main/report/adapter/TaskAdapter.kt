package ru.steelwave.steelwave.presentation.main.report.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.ItemTaskBinding
import ru.steelwave.steelwave.domain.entity.user.TaskModel

class TaskAdapter(private val context: Context): ListAdapter<TaskModel, TaskViewHolder>(TaskDiffCallback()) {

    private val rotateAnimation by lazy { AnimationUtils.loadAnimation(context, R.anim.rotate_animation) }
    private val rotateReverseAnimation by lazy { AnimationUtils.loadAnimation(context, R.anim.rotate_animation_reverse) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            tvTextTask.text = item.descr
            tvTextTask.maxLines = MIN_LINES
            if (!item.state){
                ivStateTask.setImageResource(R.drawable.icon_cross)
                cvTask.outlineSpotShadowColor = ContextCompat.getColor(context, R.color.sw_red)
            } else {
                ivStateTask.setImageResource(R.drawable.icon_check_mark)
                cvTask.outlineSpotShadowColor = ContextCompat.getColor(context, R.color.sw_green)
            }
            holder.itemView.setOnClickListener {
                val lines = tvTextTask.maxLines
                if (lines == MIN_LINES){
                    tvTextTask.maxLines = MAX_LINES
                    ivShowHideText.startAnimation(rotateAnimation)
                } else {
                    tvTextTask.maxLines = MIN_LINES
                    ivShowHideText.startAnimation(rotateReverseAnimation)
                }
            }
        }
    }

    companion object{
        private const val MIN_LINES = 2
        private const val MAX_LINES = 10000
        const val MAX_POOL_SIZE = 8
    }
}