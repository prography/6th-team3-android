package com.prography.pethotel.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.prography.pethotel.R
import com.prography.pethotel.ui.mypage.adapters.MyPickListAdapter
import com.prography.pethotel.utils.DummyData
import kotlinx.android.synthetic.main.my_page_my_pick_fragment.*

class MyPickFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_page_my_pick_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        // TODO 더미 데이터 실제 데이터로 바꾸기
        val myPickListAdapter =
            MyPickListAdapter(
                requireContext(),
                DummyData.hotelDummyList
            )
        rv_my_pick.apply {
            adapter = myPickListAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}

