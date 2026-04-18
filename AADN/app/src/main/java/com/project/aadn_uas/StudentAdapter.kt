package com.project.aadn_uas

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class StudentAdapter(private val students: ArrayList<Student>)
    : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtNama = view.findViewById<TextView>(R.id.txtNama)
        val txtNRP = view.findViewById<TextView>(R.id.txtNRP)
        val txtProgram = view.findViewById<TextView>(R.id.txtProgram)
        val imgStudent = view.findViewById<ImageView>(R.id.imgStudent)
        val btnDetail = view.findViewById<View>(R.id.imgStudent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]

        holder.txtNama.text = student.nama
        holder.txtNRP.text = "NRP: ${student.nrp}"
        holder.txtProgram.text = "Program: ${student.program}"

        if (!student.photo_url.isNullOrEmpty()) {
            Picasso.get()
                .load(student.photo_url)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.imgStudent)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("student_nrp", student.nrp)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return students.size
    }

    fun updateData(newStudents: List<Student>) {
        students.clear()
        students.addAll(newStudents)
        notifyDataSetChanged()
    }
}