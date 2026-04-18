package com.project.aadn_uas

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FriendsAdapter(private val friends: ArrayList<Student>)
    : RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    class FriendViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtNama = view.findViewById<TextView>(R.id.txtFriendNama)
        val txtNRP = view.findViewById<TextView>(R.id.txtFriendNRP)
        val imgFriend = view.findViewById<ImageView>(R.id.imgFriend)
        val btnEmail = view.findViewById<ImageButton>(R.id.btnEmail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friends[position]

        holder.txtNama.text = friend.nama
        holder.txtNRP.text = friend.nrp

        if (!friend.photo_url.isNullOrEmpty()) {
            Picasso.get().load(friend.photo_url).into(holder.imgFriend)
        }

        holder.btnEmail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(friend.email))
                putExtra(Intent.EXTRA_SUBJECT, "Halo ${friend.nama}")
                putExtra(Intent.EXTRA_TEXT, "Hai, apa kabar? Saya kirim email ini dari Aplikasi UAS NMP.")
            }

            try {
                holder.itemView.context.startActivity(
                    Intent.createChooser(emailIntent, "Kirim email menggunakan...")
                )
            } catch (e: Exception) {
            }
        }
    }

    override fun getItemCount(): Int {
        return friends.size
    }

    fun updateData(newFriends: List<Student>) {
        friends.clear()
        friends.addAll(newFriends)
        notifyDataSetChanged()
    }
}