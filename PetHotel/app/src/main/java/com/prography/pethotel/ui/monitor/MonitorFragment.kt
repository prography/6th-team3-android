package com.prography.pethotel.ui.monitor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.prography.pethotel.R
import com.prography.pethotel.utils.DummyData
import kotlinx.android.synthetic.main.fragment_monitor.*

class MonitorFragment : Fragment() {

    lateinit var photoAdapter: PhotoAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monitor, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        val mainToolbarTitle = requireActivity().findViewById<TextView>(R.id.main_toolbar_title)
//        if(mainToolbarTitle.visibility == View.GONE){
//            mainToolbarTitle.visibility = View.VISIBLE
//        }


//        photoAdapter = PhotoAdapter(requireContext(), DummyData.mediaList)
//
//        monitor_view_pager.apply {
//            setPadding(40, 0, 40, 0)
//            clipToPadding = false
//            pageMargin = 25
//            adapter = photoAdapter
//        }
    }

}
