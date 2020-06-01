package com.prography.pethotel.ui.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prography.pethotel.R
import kotlinx.android.synthetic.main.fragment_my_page.*
import kotlinx.android.synthetic.main.fragment_my_page.view.*
import kotlinx.android.synthetic.main.my_page_list_layout.view.*


class MyPageFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_page, container, false)

        view.my_pick.img_list.setImageResource(
            R.drawable.ic_favorite_black_24dp
        )
        view.my_review.img_list.setImageResource(
            R.drawable.ic_edit_black_24dp
        )
        view.notification_setting.img_list.setImageResource(
            R.drawable.ic_notifications_black_24dp
        )
        view.settings.img_list.setImageResource(
            R.drawable.ic_settings_black_24dp
        )
        view.my_pick.tv_list.text = "내가 찜한 곳"
        view.my_review.tv_list.text = "내가 쓴 리뷰"
        view.notification_setting.tv_list.text="알림 설정"
        view.settings.tv_list.text = "설정"

        return view
    }

}
