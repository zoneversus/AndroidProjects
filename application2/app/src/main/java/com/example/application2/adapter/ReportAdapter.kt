package com.example.application2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.application2.R
import com.example.application2.entity.ReportEntity

class ReportAdapter(
    private val tasks: List<ReportEntity>,
    private val checkTask: (ReportEntity) -> Unit,
    private val deleteTask: (ReportEntity) -> Unit) : RecyclerView.Adapter<ReportAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_notes, parent, false))
    }
    override fun getItemCount(): Int {
        return tasks.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tasks[position]
        holder.bind(item, checkTask, deleteTask)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvReport = view.findViewById<TextView>(R.id.tvReport)
        private val tvReportDate=view.findViewById<TextView>(R.id.tvReportDate)
        private val cbIsDone = view.findViewById<CheckBox>(R.id.cbIsDone)
        private val iVDelete = view.findViewById<ImageView>(R.id.iVDelete)

        fun bind(
            task: ReportEntity,
            checkTask: (ReportEntity) -> Unit,
            deleteTask: (ReportEntity) -> Unit
        ) {
            tvReport.text = task.name
            tvReportDate.text=task.date
            cbIsDone.isChecked = task.isDone
            cbIsDone.setOnClickListener { checkTask(task) }
            iVDelete.setOnClickListener {
                deleteTask(task)
            }

        }
    }
}