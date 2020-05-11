package com.prography.pethotel.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager

import com.prography.pethotel.R
import com.prography.pethotel.models.PetInfo
import kotlinx.android.synthetic.main.register_pet_info_detail_fragment.*

class RegisterPetInfoDetailFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterPetInfoDetailFragment()
    }

    private lateinit var viewModel: RegisterPetInfoDetailViewModel
    private lateinit var petInfoArrayList : ArrayList<PetInfo>
    private lateinit var petDetailPagerAdapter: PetDetailPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register_pet_info_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RegisterPetInfoDetailViewModel::class.java)


        // 몇 마리 입력하는 건지 이전 화면에서 받아오기. 지금은 테스트용으로 하드코딩.

        createEmptyPetInfoContainer(3)
        petDetailPagerAdapter = PetDetailPagerAdapter(context, petInfoArrayList)
        view_pager_register_pet_info_detail.adapter = petDetailPagerAdapter
        view_pager_register_pet_info_detail.offscreenPageLimit = 1
        view_pager_register_pet_info_detail.pageMargin = 30

    }

    private fun createEmptyPetInfoContainer(numPets : Int){
        for( i in 0..numPets){
            petInfoArrayList.add(PetInfo())
        }
    }

    private fun viewPagerEnterInfo(){
        view_pager_register_pet_info_detail.addOnPageChangeListener(
            ViewPagerOnPageSelected()
        )
    }

}

class ViewPagerOnPageSelected(private val pageSelected: (Int) -> Unit = {}) : ViewPager.OnPageChangeListener {

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }
}
