package com.prography.pethotel.ui.reservation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class DateTimeViewModel : ViewModel(){

    companion object{
        private lateinit var instance : DateTimeViewModel

        fun getInstance() : DateTimeViewModel{
            instance = if(::instance.isInitialized){
                instance
            }else{
                DateTimeViewModel()
            }
            return instance
        }
    }

    val dateTime : MutableLiveData<ReservationDateTime> = MutableLiveData()

    fun initDateTime(){
        val c : Calendar = Calendar.getInstance()
        val enterDate = "${c.get(Calendar.YEAR)}/${c.get(Calendar.MONTH) + 1}/${c.get(Calendar.DAY_OF_MONTH)}"
        val exitDate = "${c.get(Calendar.YEAR)}/${c.get(Calendar.MONTH)+ 1}/${c.get(Calendar.DAY_OF_MONTH)}"
        val enterTime = "${c.get(Calendar.HOUR_OF_DAY)}:00"
        val exitTime = "${c.get(Calendar.HOUR_OF_DAY)}:00"

        dateTime.value = ReservationDateTime(enterTime = enterTime, exitTime =  exitTime,
        enterDate = enterDate, exitDate = exitDate)
    }

    fun setEnterDate(date : String){
        dateTime.value = dateTime.value.also {
            if (it != null) {
                it.enterDate = date
            }
        }
    }

    fun setExitDate(date : String){
        dateTime.value = dateTime.value.also {
            if (it != null) {
                it.exitDate = date
            }
        }
    }

    fun setEnterTime(time : String){
        dateTime.value = dateTime.value.also {
            if (it != null) {
                it.enterTime = time
            }
        }
    }

    fun setExitTime(time : String){
        dateTime.value = dateTime.value.also {
            if (it != null) {
                it.exitTime = time
            }
        }
    }
}