package com.prography.pethotel.ui.monitor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prography.pethotel.R
import kotlinx.android.synthetic.main.fragment_monitor.*

class MonitorFragment : Fragment() {

    lateinit var photoAdapter: PhotoAdapter
    var mediaList : ArrayList<MonitorMedia> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monitor, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        makeDummyMediaList()

        photoAdapter = PhotoAdapter(requireContext(), mediaList)

        monitor_view_pager.apply {
            setPadding(40, 0, 40, 0)
            clipToPadding = false
            pageMargin = 25
            adapter = photoAdapter
        }
    }

    private fun makeDummyMediaList(){
        if(mediaList.isNotEmpty()){
            return
        }else {
            for (x in 1..4) {
                mediaList.add(
                    MonitorMedia(
                        "https://www.newshub.co.nz/home/lifestyle/2019/11/dog-years-are-a-myth-2-year-old-dogs-already-middle-aged-scientists/_jcr_content/par/video/image.dynimg.1280.q75.jpg/v1574572358818/GETTY-labrador-puppy-1120.jpg",
                        "#산책하루${x}번 #신나요",
                        "2020-03-0${x}"
                    )
                )
            }
        }
    }

}
