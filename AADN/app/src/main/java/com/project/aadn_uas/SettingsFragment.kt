package com.project.aadn_uas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.switchmaterial.SwitchMaterial
import org.json.JSONObject

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnReset = view.findViewById<Button>(R.id.btnReset)
        val switchNight = view.findViewById<SwitchMaterial>(R.id.switchNightMode)

        val currentMode = AppCompatDelegate.getDefaultNightMode()
        if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            switchNight.isChecked = true
        } else {
            switchNight.isChecked = false
        }

        switchNight.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        btnReset.setOnClickListener {
            resetFriends()
        }
    }

    private fun resetFriends() {
        val url = "http://10.0.2.2/nmp/project/reset_friends.php"

        val q = Volley.newRequestQueue(activity)
        val stringRequest = StringRequest(Request.Method.POST, url,
            { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val result = jsonObject.getString("result")

                    if (result == "OK") {
                        Toast.makeText(activity, "Berhasil menghapus semua teman", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, "Gagal reset", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(activity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(activity, "Gagal koneksi", Toast.LENGTH_SHORT).show()
            }
        )
        q.add(stringRequest)
    }
}