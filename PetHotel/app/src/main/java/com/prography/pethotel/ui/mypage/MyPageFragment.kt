package com.prography.pethotel.ui.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.prography.pethotel.R
import com.prography.pethotel.ui.authentication.kakao.KakaoLoginActivity
import com.prography.pethotel.utils.LoginStateViewModel
import com.prography.pethotel.utils.LoginStateViewModelFactory
import kotlinx.android.synthetic.main.new_my_page_logged_in_layout.*
import kotlinx.android.synthetic.main.new_my_page_not_logged_in_layout.*


class MyPageFragment : Fragment() {

    private var myPageView : View? = null
    lateinit var loginStateViewModel: LoginStateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        loginStateViewModel = ViewModelProvider(
            requireActivity(),
            LoginStateViewModelFactory()
        )[LoginStateViewModel::class.java]

        myPageView = if(loginStateViewModel.isTokenValid(requireActivity())){
            inflater.inflate(R.layout.new_my_page_logged_in_layout, container, false)
        }else{
            inflater.inflate(R.layout.new_my_page_not_logged_in_layout, container, false)
        }
        return myPageView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //TODO 예약 확인 navigation 만들기
        //TODO 로그아웃 로직 작성하기


        if(loginStateViewModel.isTokenValid(requireActivity())){
            setLoggedInView()
        }else{
            setLoggedOutView()
        }
    }

    private fun setLoggedOutView(){
        btn_login_mypage.setOnClickListener {
            //TODO 마이페이지에서 로그인으로 넘어가기
        }
        btn_register_mypage.setOnClickListener {
            //TODO 마이페이지에서 회원가입으로 넘어가기
        }
        btn_kakao_login_mypage.setOnClickListener {
            val intent = Intent(requireActivity(), KakaoLoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setLoggedInView(){
        mypage_edit_user_pet_info.setOnClickListener {
            //내 정보 수정 페이지로 이동한다.
//            findNavController().navigate(R.id.action_myPageFragment_to_userInfoEditFragment)
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
    }

}
