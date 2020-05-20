package com.prography.pethotel.ui.register

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.prography.pethotel.R
import com.prography.pethotel.ui.register.RegisterPetInfoDetailFragmentArgs
import com.prography.pethotel.ui.register.utils.PetDetailAdapter
import com.prography.pethotel.ui.register.viewmodels.RegisterPetInfoDetailViewModel
import kotlinx.android.synthetic.main.register_pet_info_detail_fragment.*

class RegisterPetInfoDetailFragment : Fragment() {

    companion object {
        fun newInstance() =
            RegisterPetInfoDetailFragment()
    }

    private lateinit var viewModel: RegisterPetInfoDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register_pet_info_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RegisterPetInfoDetailViewModel::class.java)


        // 몇 마리 입력하는 건지 이전 화면에서 받아오기
        // 동적으로 레이아웃 추가할 수 있도록 하기 ?

    }

    private val args : RegisterPetInfoDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val numOfPets = args.numOfPets
        Toast.makeText(context, "Num of Pets : $numOfPets", Toast.LENGTH_SHORT).show()

        val petDetailAdapter = PetDetailAdapter(numOfPets)
        rv_register_pet_detail.apply {
            adapter = petDetailAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }

}

