package com.project.aadn_uas

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {

    var descAbout = ""
    var descCourse = ""
    var descExperience = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val spinner = findViewById<Spinner>(R.id.spinnerDetail)
        val options = arrayOf("About Me", "My Courses", "My Experiences")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val txtContent = findViewById<TextView>(R.id.txtInfoContent)
                when (position) {
                    0 -> txtContent.text = descAbout
                    1 -> txtContent.text = descCourse
                    2 -> txtContent.text = descExperience
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        val nrp = intent.getStringExtra("student_nrp")
        if (nrp != null) {
            loadStudentDetail(nrp)
        }

        val btnAdd = findViewById<Button>(R.id.btnAddFriend)

        btnAdd.setOnClickListener {
            val nama = findViewById<TextView>(R.id.txtDetailNama).text.toString()
            val currentNRP = findViewById<TextView>(R.id.txtDetailNRP).text.toString()
            addFriend(currentNRP, nama)
        }
    }

    private fun loadStudentDetail(nrp: String) {
        val progressBar = findViewById<ProgressBar>(R.id.progressBarDetail)
        progressBar.visibility = View.VISIBLE

        val url = "http://10.0.2.2/nmp/project/get_student_id.php"

        val q = Volley.newRequestQueue(this)
        val request = object : StringRequest(Method.POST, url,
            { response ->
                progressBar.visibility = View.GONE
                try {
                    val jsonObject = JSONObject(response)
                    val result = jsonObject.getString("result")

                    if (result == "OK") {
                        val data = jsonObject.getJSONObject("data")

                        findViewById<TextView>(R.id.txtDetailNama).text = data.getString("nama")
                        findViewById<TextView>(R.id.txtDetailNRP).text = data.getString("nrp")
                        findViewById<TextView>(R.id.txtDetailEmail).text = data.getString("email")

                        val photoUrl = data.getString("photo_url")
                        if (photoUrl.isNotEmpty()) {
                            Picasso.get().load(photoUrl).into(findViewById<ImageView>(R.id.imgDetailProfile))
                        }
                        val isFriend = data.getBoolean("is_friend")
                        val btnAdd = findViewById<Button>(R.id.btnAddFriend)

                        if (isFriend) {
                            btnAdd.isEnabled = false
                            btnAdd.text = "Already Added"
                        } else {
                            btnAdd.isEnabled = true
                            btnAdd.text = "Request Friend"
                        }

                        descAbout = data.getString("about")
                        descCourse = data.optString("my_course", "-")
                        descExperience = data.optString("my_experiences", "-")

                        findViewById<TextView>(R.id.txtInfoContent).text = descAbout

                        val program = data.getString("program")
                        updateRadioButton(program)

                    } else {
                        Toast.makeText(this, "Gagal: ${jsonObject.getString("message")}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("DetailActivity", "Error: ${e.message}")
                }
            },
            { error ->
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Koneksi Error", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["nrp"] = nrp
                return params
            }
        }
        q.add(request)
    }

    private fun updateRadioButton(program: String) {
        when (program) {
            "DSAI" -> findViewById<RadioButton>(R.id.rbDSAI).isChecked = true
            "NCS" -> findViewById<RadioButton>(R.id.rbNCS).isChecked = true
            "IMES" -> findViewById<RadioButton>(R.id.rbIMES).isChecked = true
            "DMT" -> findViewById<RadioButton>(R.id.rbDMT).isChecked = true
            "GD" -> findViewById<RadioButton>(R.id.rbGD).isChecked = true
        }
    }

    private fun addFriend(nrp: String, nama: String) {
        val url = "http://10.0.2.2/nmp/project/insert_friend.php"

        val q = Volley.newRequestQueue(this)
        val request = object : StringRequest(Method.POST, url,
            { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val result = jsonObject.getString("result")

                    if (result == "OK") {
                        val total = jsonObject.getInt("total")

                        showSuccessDialog(nama, total)

                        val btn = findViewById<Button>(R.id.btnAddFriend)
                        btn.isEnabled = false
                        btn.text = "Already Added"

                    } else if (result == "ALREADY_EXISTS") {
                        Toast.makeText(this, "Dia sudah ada di daftar temanmu!", Toast.LENGTH_SHORT).show()
                        findViewById<Button>(R.id.btnAddFriend).isEnabled = false
                    } else {
                        Toast.makeText(this, "Gagal: ${jsonObject.getString("message")}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("DetailActivity", "Error Add: ${e.message}")
                }
            },
            { error ->
                Toast.makeText(this, "Gagal koneksi", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["nrp"] = nrp
                return params
            }
        }
        q.add(request)
    }

    private fun showSuccessDialog(nama: String, total: Int) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("Friend Request")
        builder.setMessage("Sukses tambah $nama sebagai friend.\nFriend anda sekarang adalah $total.")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }
}