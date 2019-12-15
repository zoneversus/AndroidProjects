package com.example.application2.actividades

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application2.R
import com.example.application2.adapter.ReportAdapter
import com.example.application2.entity.ReportEntity
import kotlinx.android.synthetic.main.activity_notes.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.util.*


class Notes : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ReportAdapter
    lateinit var reports: MutableList<ReportEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        supportActionBar?.hide()
        reports = ArrayList()
        getReports()

        btnAddReport?.setOnClickListener {
            if (etReport.text.isEmpty()){
                Toast.makeText(this,"De agregar texto a la nota",Toast.LENGTH_SHORT)
            }else{
                if (etDateReport.text.equals("/  /")){
                    Toast.makeText(this,"Asegurate de seleccionar una fecha",Toast.LENGTH_SHORT)
                }else{
                    Toast.makeText(this,"Agregado",Toast.LENGTH_SHORT)
                    addReport(ReportEntity(name = etReport.text.toString(),date = etDateReport.text.toString()))
                }
            }
        }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        etDateReport?.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                etDateReport.setText("" + dayOfMonth + "/" + month + "/" + year)
            }, year, month, day)
            dpd.show()
        }

    }
    fun clearFocus(){
        etReport.setText("")
        etDateReport.setText("/  /")
    }

    fun Context.hideKeyboard() {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    fun getReports() {
        doAsync {
            reports = MusicApp.database.reportDao().getAllReportsOrderByDate()
            uiThread {
                setUpRecyclerView(reports)
            }
        }
    }

    fun addReport(report:ReportEntity){
        doAsync {
            val id = MusicApp.database.reportDao().addReport(report)
            val recoveryTask = MusicApp.database.reportDao().getReport(id)
            uiThread {
                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val strDate: Date = sdf.parse(report.date)
                var before=false
               for (i in 0 until reports.size) {
                    val dateToCompare= reports.get(i).date
                    val strDate1: Date = sdf.parse(dateToCompare)
                    if (strDate.before(strDate1)) {
                        reports.add(i,recoveryTask)
                        adapter.notifyItemInserted(i)
                        before=true
                        break
                    }
                }
                if (!before){
                    reports.add(recoveryTask)
                    adapter.notifyItemInserted(reports.size)
                }

                clearFocus()
                hideKeyboard()
            }
        }
    }

    fun updateTask(report: ReportEntity) {
        doAsync {
            report.isDone = !report.isDone
            MusicApp.database.reportDao().updateReport(report)
        }
    }

    fun deleteTask(report: ReportEntity){
        doAsync {
            val position = reports.indexOf(report)
            MusicApp.database.reportDao().deleteReport(report)
            reports.remove(report)
            uiThread {
                //                toast("delete ${tasks[position].name}")
                adapter.notifyItemRemoved(position)
            }
        }
    }

    fun setUpRecyclerView(reports: List<ReportEntity>) {
        adapter = ReportAdapter(reports, { updateTask(it) }, {deleteTask(it)})
        recyclerView = findViewById(R.id.rvTask)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }


}
