package com.project.aadn_uas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private val students = ArrayList<Student>()
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewHome)
        progressBar = view.findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = StudentAdapter(students)
        recyclerView.adapter = adapter

        updateList()
    }

    fun updateList() {
        val url = "http://10.0.2.2/nmp/project/get_all_student.php"

        val q = Volley.newRequestQueue(activity)
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                try {
                    progressBar.visibility = View.GONE
                    Log.d("HomeFragment", "Response dari PHP: $response")

                    val sType = object : TypeToken<ArrayList<Student>>() {}.type
                    val result = Gson().fromJson<ArrayList<Student>>(response, sType)

                    adapter.updateData(result)
                    Log.d("HomeFragment", "Sukses load: ${result.size} data")

                } catch (e: Exception) {
                    progressBar.visibility = View.GONE
                    Log.e("HomeFragment", "Error Parsing JSON: ${e.message}")
                    Toast.makeText(activity, "Error Format Data: ${e.message}", Toast.LENGTH_LONG).show()
                }
            },
            { error ->
                progressBar.visibility = View.GONE
                Log.e("HomeFragment", "Volley Error: ${error.message}")
                Toast.makeText(activity, "Gagal koneksi server", Toast.LENGTH_SHORT).show()
            }
        )

        q.add(stringRequest)
    }
}