package com.example.aadn_uas // Pastikan package ini sesuai punya mu

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.aadn_uas.HomeFragment
import com.project.aadn_uas.MyFriendsFragment
import com.project.aadn_uas.SettingsFragment

class MyPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> MyFriendsFragment()
            2 -> SettingsFragment()
            else -> HomeFragment()
        }
    }
}