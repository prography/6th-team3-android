package com.prography.pethotel.ui.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.prography.pethotel.R
import kotlinx.android.synthetic.main.new_my_page_logged_in_layout.*


class MyPageFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //TODO 로그인 되었으면 logged in layout, 안되어있으면 not logged in layout 띄우기
        val view = inflater.inflate(R.layout.new_my_page_logged_in_layout, container, false)

//        view.my_pick.img_list.setImageResource(
//            R.drawable.ic_favorite_black_24dp
//        )
//        view.my_review.img_list.setImageResource(
//            R.drawable.ic_baseline_rate_review_black_24
//        )
//        view.settings.img_list.setImageResource(
//            R.drawable.ic_settings_black_24dp
//        )
//        view.my_pick.tv_list.text = "내가 찜한 곳"
//        view.my_review.tv_list.text = "내가 쓴 리뷰"
//        view.settings.tv_list.text = "설정"

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mypage_edit_user_pet_info.setOnClickListener {
            //내 정보 수정 페이지로 이동한다.
            findNavController().navigate(R.id.action_myPageFragment_to_userInfoEditFragment)
        }

        mypage_my_favorites.setOnClickListener {
            //내가 찜한 곳으로 이동한다.
            findNavController().navigate(R.id.action_myPageFragment_to_myPickFragment)
        }

        mypage_myreviews.setOnClickListener {
            //나의 리뷰로 이동한다.
            findNavController().navigate(R.id.action_myPageFragment_to_myReviewFragment)
        }

        mypage_other_settings.setOnClickListener {
            //환경 설정으로 이동한다.
            findNavController().navigate(R.id.action_myPageFragment_to_settingsFragment)
        }
        //TODO 예약 확인 navigation 만들기

        //TODO 로그아웃 로직 작성하기

    }

}
