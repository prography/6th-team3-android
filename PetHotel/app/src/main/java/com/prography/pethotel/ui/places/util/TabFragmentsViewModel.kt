package com.prography.pethotel.ui.places.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

class TabFragmentsViewModel : ViewModel(){


    var tabFragments : MutableLiveData<ArrayList<Pair<String, Fragment>>> = MutableLiveData()

    fun addLiveData(fragment: Fragment, title : String){
        tabFragments.value?.add(Pair(title, fragment))
    }

    fun removeLiveData(position : Int){
        tabFragments.value?.removeAt(position)
    }

}