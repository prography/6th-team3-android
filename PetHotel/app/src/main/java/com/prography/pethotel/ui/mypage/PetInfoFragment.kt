package com.prography.pethotel.ui.mypage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prography.pethotel.R
import com.prography.pethotel.room.auth.AccountPropertiesViewModel
import com.prography.pethotel.ui.mypage.adapters.PetListAdapter
import com.prography.pethotel.utils.AuthTokenViewModel
import kotlinx.android.synthetic.main.fragment_pet_info.*


private const val TAG = "PetInfoFragment"
class PetInfoFragment : Fragment() {

    private val accountPropertiesViewModel : AccountPropertiesViewModel by activityViewModels()
    private val authTokenViewModel : AuthTokenViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pet_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fab_add_pet.setOnClickListener {
            findNavController().navigate(R.id.action_petInfoFragment_to_registerPetFragment)
        }

        val userId = authTokenViewModel.getUserId(requireActivity())
        Log.d(TAG, "onActivityCreated: $userId")
        accountPropertiesViewModel.fetchPetByUserId(userId)

        accountPropertiesViewModel.petListResult.observe(viewLifecycleOwner, Observer{
            Log.d(TAG, "onActivityCreated: Pet list observing ... $it")
            if(!it.isNullOrEmpty()){
                Log.d(TAG, "onActivityCreated: $it")
                no_pet_yet_container.visibility = View.GONE
                val adapter = PetListAdapter(it, requireContext())
                rv_my_pet.apply {
                    setAdapter(adapter)
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(requireContext())
                }
                adapter.notifyDataSetChanged()
            }
        })
    }
}