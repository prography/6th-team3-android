package com.prography.pethotel.ui.places.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class MainTabAdapter(
    fragmentManager: FragmentManager,
    behavior: Int
) : FragmentStatePagerAdapter(
    fragmentManager, behavior
) {


    var fragmentList: ArrayList<Fragment> = ArrayList()
    var fragmentTitleList: ArrayList<String> = ArrayList()


    fun addFragment(fragment: Fragment, title: String) {
        Log.d(TAG, "addFragment: ${title}")

        if(fragmentTitleList.contains(title)){
            return
        }
        fragmentList.add(fragment)
        fragmentTitleList.add(title)

        notifyDataSetChanged()
    }

    fun removeFragment(position: Int) {
        fragmentList.removeAt(position)
        fragmentTitleList.removeAt(position)

        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        Log.d(TAG, "getCount: ${fragmentList.size}")
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }

    companion object {
        private const val TAG = "MainTabAdapter"
    }
}
