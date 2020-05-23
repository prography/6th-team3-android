package com.prography.pethotel.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prography.pethotel.R
import kotlinx.android.synthetic.main.fragment_my_page.*
import kotlinx.android.synthetic.main.my_page_list_layout.view.*
import kotlinx.android.synthetic.main.my_page_list_layout.view.img_list

class MyPageActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_my_page)

        my_pick.img_list.setImageResource(
           R.drawable.ic_favorite_black_24dp
        )
        my_review.img_list.setImageResource(
            R.drawable.ic_edit_black_24dp
        )
        notification_setting.img_list.setImageResource(
            R.drawable.ic_notifications_black_24dp
        )
        settings.img_list.setImageResource(
            R.drawable.ic_settings_black_24dp
        )
        my_pick.tv_list.text = "내가 찜한 곳"
        my_review.tv_list.text = "내가 쓴 리뷰"
        notification_setting.tv_list.text="알림 설정"
        settings.tv_list.text = "설정"

    }
}