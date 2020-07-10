@file:Suppress("DEPRECATION")

package com.prography.pethotel.ui.authentication.kakao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.prography.pethotel.R
import com.prography.pethotel.api.auth.response.KakaoRegistrationResponse
import com.prography.pethotel.utils.KAKAO_REG_RESPONSE_KEY

class KakaoRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kakao_register)

        /*KakaoLoginActivity 에서 받은 응답 정보를 Intent 로 RegisterActivity로 넘겨주고,
        * 해당 정보를 본 액티비티의 뷰모델에 넣는다. 이 액티비티 위에서 Navigate 하는 Fragment 들은
        * 해당 정보를 관찰한다. */
        val response
                = intent.getParcelableExtra<KakaoRegistrationResponse>(KAKAO_REG_RESPONSE_KEY)

        val kakaoRegisterViewModel
                = ViewModelProviders.of(this)[KakaoRegisterViewModel::class.java]

        kakaoRegisterViewModel.setKakaoRegisterData(response)
    }
}