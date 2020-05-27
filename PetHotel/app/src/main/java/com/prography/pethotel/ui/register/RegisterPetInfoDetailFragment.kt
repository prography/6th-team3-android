package com.prography.pethotel.ui.register

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager

import com.prography.pethotel.R
import com.prography.pethotel.models.PetInfo
import com.prography.pethotel.ui.MainActivity
import com.prography.pethotel.ui.register.utils.PetDetailAdapter
import com.prography.pethotel.ui.register.viewmodels.RegisterPetInfoDetailViewModel
import com.prography.pethotel.utils.TAG_PET_DETAIL
import kotlinx.android.synthetic.main.register_pet_info_detail_fragment.*

class RegisterPetInfoDetailFragment : Fragment() {

    companion object {
        fun newInstance() =
            RegisterPetInfoDetailFragment()
    }

    private lateinit var viewModel: RegisterPetInfoDetailViewModel
    private lateinit var petDetailAdapter: PetDetailAdapter
    private lateinit var petInfoList: ArrayList<PetInfo>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register_pet_info_detail_fragment, container, false)
    }

    private val args : RegisterPetInfoDetailFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(RegisterPetInfoDetailViewModel::class.java)

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        petInfoList = ArrayList()

        val numOfPets = args.numOfPets
//        Toast.makeText(context, "Num of Pets : $numOfPets", Toast.LENGTH_SHORT).show()
        if(petInfoList.isEmpty()){
            for (x in 1 .. numOfPets) {
                petInfoList.add(PetInfo())
            }
        }

        petDetailAdapter = PetDetailAdapter(petInfoList)

        rv_register_pet_detail.apply {
            adapter = petDetailAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        btn_pet_detail_footer.setOnClickListener {
            Log.d(TAG_PET_DETAIL, "입력완료 버튼 클릭")

            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }

    }

}

