package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entity.TaskEntity

class TasksAdapter(
    private val tasks: List<TaskEntity>,
    private val checkTask: (TaskEntity) -> Unit,
    private val deleteTask: (TaskEntity) -> Unit) : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_task, parent, false))
    }
    override fun getItemCount(): Int {
        return tasks.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tasks[position]
        holder.bind(item, checkTask, deleteTask)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTask = view.findViewById<TextView>(R.id.tvTask)
        private val cbIsDone = view.findViewById<CheckBox>(R.id.cbIsDone)
        private val iVDelete = view.findViewById<ImageView>(R.id.iVDelete)

        fun bind(
            task: TaskEntity,
            checkTask: (TaskEntity) -> Unit,
            deleteTask: (TaskEntity) -> Unit
        ) {
            tvTask.text = task.name
            cbIsDone.isChecked = task.isDone
            cbIsDone.setOnClickListener { checkTask(task) }
            iVDelete.setOnClickListener {
                deleteTask(task)
            }

        }
    }
}
