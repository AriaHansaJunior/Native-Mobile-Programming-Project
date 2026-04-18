package com.project.aadn_uas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MyFriendsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FriendsAdapter
    private val friendsList = ArrayList<Student>()
    private lateinit var progressBar: ProgressBar
    private lateinit var txtNoFriends: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewFriends)
        progressBar = view.findViewById(R.id.progressBarFriends)
        txtNoFriends = view.findViewById(R.id.txtNoFriends)

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = FriendsAdapter(friendsList)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        loadFriends()
    }

    private fun loadFriends() {
        val url = "http://10.0.2.2/nmp/project/get_friends.php"

        val q = Volley.newRequestQueue(activity)
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                try {
                    progressBar.visibility = View.GONE
                    Log.d("MyFriendsFragment", "Response: $response")

                    val sType = object : TypeToken<ArrayList<Student>>() {}.type
                    val result = Gson().fromJson<ArrayList<Student>>(response, sType)

                    if (result.isEmpty()) {
                        txtNoFriends.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    } else {
                        txtNoFriends.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        adapter.updateData(result)
                    }

                } catch (e: Exception) {
                    progressBar.visibility = View.GONE
                    Log.e("MyFriendsFragment", "Error Parsing: ${e.message}")
                }
            },
            { error ->
                progressBar.visibility = View.GONE
                Log.e("MyFriendsFragment", "Volley Error: ${error.message}")
            }
        )
        q.add(stringRequest)
    }
}