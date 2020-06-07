package com.prography.pethotel.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.prography.pethotel.R
import kotlinx.android.synthetic.main.input_field_layout_with_button.view.*
import kotlinx.android.synthetic.main.testing_input_field_layout.*

class LoginRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        // TODO network checking! Base Activity 만들어서 네트워크 확인하기.


    }

    override fun onBackPressed() {
        val alertBuilder = MaterialAlertDialogBuilder(this)
        alertBuilder.apply {
            setTitle("종료하기")
            setMessage("마이펫밀리를 종료하시겠습니까?")
            setCancelable(true)
            setPositiveButton("종료") { _, _ ->
                finishAndRemoveTask()
            }
            setNeutralButton("취소") { dialog, _ ->
                dialog.cancel()
            }
        }
        val alertDialog = alertBuilder.create()
        alertDialog.show()
    }

}
