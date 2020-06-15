package com.prography.pethotel.utils

import android.app.Activity
import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class Alerts {

    fun promptExitMessage(activity : Activity){
        val alertBuilder = MaterialAlertDialogBuilder(activity.applicationContext)
        alertBuilder.apply {
            setTitle("종료하기")
            setMessage("마이펫밀리를 종료하시겠습니까?")
            setCancelable(true)
            setPositiveButton("종료") { _, _ ->
                activity.finishAndRemoveTask()
            }
            setNeutralButton("취소") { dialog, _ ->
                dialog.cancel()
            }
        }
        val alertDialog = alertBuilder.create()
        alertDialog.show()
    }
}