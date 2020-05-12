package com.prography.pethotel.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.prography.pethotel.R
import kotlinx.android.synthetic.main.input_field_layout_with_button.view.*
import kotlinx.android.synthetic.main.testing_input_field_layout.*

class LoginRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.testing_input_field_layout)


        input_box_name.title_field.text = "사용자 이름"
        input_box_phone_number.title_field.text = "전화번호"
        input_box_year.title_field.text = "태어난 년도"

        input_box_name.edit_text_field.inputType = EditorInfo.TYPE_TEXT_VARIATION_PERSON_NAME
        input_box_name.edit_text_field.hint = "이름을 입력해 주세요"

    }


}
