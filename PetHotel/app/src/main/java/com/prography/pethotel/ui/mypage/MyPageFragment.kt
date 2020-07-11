package com.prography.pethotel.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.prography.pethotel.R
import com.prography.pethotel.ui.authentication.LoginRegisterActivity
import com.prography.pethotel.ui.authentication.kakao.KakaoLoginActivity
import com.prography.pethotel.utils.LoginStateViewModel
import com.prography.pethotel.utils.LoginStateViewModelFactory
import com.prography.pethotel.utils.TokenState
import kotlinx.android.synthetic.main.new_my_page_logged_in_layout.*
import kotlinx.android.synthetic.main.new_my_page_logged_in_layout.view.*
import kotlinx.android.synthetic.main.new_my_page_not_logged_in_layout.*
import kotlinx.android.synthetic.main.new_my_page_not_logged_in_layout.view.*


private const val TAG = "MyPageFragment"
class MyPageFragment : Fragment() {

    private var myPageView : View? = null
    private val loginStateViewModel: LoginStateViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if(loginStateViewModel.isTokenValid(requireActivity())){
            myPageView = inflater.inflate(R.layout.new_my_page_logged_in_layout, container, false)
        }else{
            myPageView = inflater.inflate(R.layout.new_my_page_not_logged_in_layout, container, false)
        }
        return myPageView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginStateViewModel.userTokenState.observe(viewLifecycleOwner, Observer {
            when(it){
                TokenState.STORED -> myPageView?.let { v -> setLoggedInView(v) }
                TokenState.REMOVED -> {
                    val intent = Intent(requireActivity(), LoginRegisterActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //TODO 예약 확인 navigation 만들기
        //TODO 로그아웃 로직 작성하기

    }

//    private fun setLoggedOutView(view : View){
//        view.btn_login_mypage.setOnClickListener {
//            //TODO 마이페이지에서 로그인으로 넘어가기
//        }
//        view.btn_register_mypage.setOnClickListener {
//            //TODO 마이페이지에서 회원가입으로 넘어가기
//        }
//        view.btn_kakao_login_mypage.setOnClickListener {
//            val intent = Intent(requireActivity(), KakaoLoginActivity::class.java)
//            startActivity(intent)
//        }
//    }

    private fun setLoggedInView(view : View){
        view.mypage_edit_user_pet_info.setOnClickListener {
            //내 정보 수정 페이지로 이동한다.
//            findNavController().navigate(R.id.action_myPageFragment_to_userInfoEditFragment)
        }

        view.mypage_my_favorites.setOnClickListener {
            //내가 찜한 곳으로 이동한다.
            findNavController().navigate(R.id.action_myPageFragment_to_myPickFragment)
        }

        view.mypage_myreviews.setOnClickListener {
            //나의 리뷰로 이동한다.
            findNavController().navigate(R.id.action_myPageFragment_to_myReviewFragment)
        }

        view.mypage_other_settings.setOnClickListener {
            //환경 설정으로 이동한다.
            findNavController().navigate(R.id.action_myPageFragment_to_settingsFragment)
        }
        view.btn_mypage_logout.setOnClickListener {
            loginStateViewModel.removeUserToken(requireActivity())

            Log.d(TAG, "setLoggedInView: 로그아웃 클릭")
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            MyPageFragment()
    }
}
