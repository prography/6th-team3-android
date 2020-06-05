package com.prography.pethotel.ui.reservation

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*


class DatePickerFragment(
    var dateTimeViewModel: DateTimeViewModel,
    var isEnter: Boolean
) : DialogFragment(), DatePickerDialog.OnDateSetListener{


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date = "${year}년 ${month+1}월 ${dayOfMonth}일"
        if(isEnter){
            dateTimeViewModel.setEnterDate(date)

        }else{
            dateTimeViewModel.setExitDate(date)
        }
    }
}
