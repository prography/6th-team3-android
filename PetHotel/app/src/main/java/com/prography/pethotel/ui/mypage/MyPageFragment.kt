package com.prography.pethotel.ui.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.prography.pethotel.R
import kotlinx.android.synthetic.main.fragment_my_page.*
import kotlinx.android.synthetic.main.fragment_my_page.view.*
import kotlinx.android.synthetic.main.login_register_fragment.*
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
            R.drawable.ic_baseline_rate_review_black_24
        )
        view.settings.img_list.setImageResource(
            R.drawable.ic_settings_black_24dp
        )
        view.my_pick.tv_list.text = "내가 찜한 곳"
        view.my_review.tv_list.text = "내가 쓴 리뷰"
        view.settings.tv_list.text = "설정"

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        img_btn_user_edit.setOnClickListener {
            //내 정보 수정 페이지로 이동한다.
            findNavController().navigate(R.id.action_myPageFragment_to_userInfoEditFragment)
        }

        my_pick.setOnClickListener {
            //내가 찜한 곳으로 이동한다.
            findNavController().navigate(R.id.action_myPageFragment_to_myPickFragment)
        }

        my_review.setOnClickListener {
            //나의 리뷰로 이동한다.
            findNavController().navigate(R.id.action_myPageFragment_to_myReviewFragment)
        }

        settings.setOnClickListener {
            //환경 설정으로 이동한다.
            findNavController().navigate(R.id.action_myPageFragment_to_settingsFragment)
        }

    }

}
