package com.prography.pethotel.ui.reservation

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*


class TimePickerFragment(
    private var dateTimeViewModel: DateTimeViewModel,
    private val isEnter: Boolean
) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        val dialog =  TimePickerDialog(requireContext(), this, hour, minute, DateFormat.is24HourFormat(requireContext()))

        return dialog
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        var min: String = (if(minute == 0){
                "00"
            }else if(minute < 10){
                "0${minute}"
            } else{
                minute.toString()
            })

        val time = "${hourOfDay}:${min}"

        if(isEnter){
            dateTimeViewModel.setEnterTime(time)
        }else{
            dateTimeViewModel.setExitTime(time)
        }
    }
}