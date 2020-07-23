package com.prography.pethotel.ui.mypage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.prography.pethotel.R
import com.prography.pethotel.api.auth.request.CheckPetBody
import com.prography.pethotel.api.auth.request.PetData
import com.prography.pethotel.api.auth.response.PostPetResponseData
import com.prography.pethotel.api.auth.response.UserToken
import com.prography.pethotel.room.auth.AccountPropertiesViewModel
import com.prography.pethotel.room.entities.Pet
import com.prography.pethotel.ui.authentication.register.RegisterViewModel
import com.prography.pethotel.ui.authentication.utils.BaseFragment
import com.prography.pethotel.utils.AuthTokenViewModel
import kotlinx.android.synthetic.main.fragment_register_pet.*
import kotlinx.android.synthetic.main.layout_bottom_sheet.view.*
import kotlinx.android.synthetic.main.pet_detail_layout.view.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


private const val TAG = "RegisterPetFragment"
class RegisterPetFragment : BaseFragment() {

    private val registerViewModel : RegisterViewModel by activityViewModels()
    private val accountPropertiesViewModel: AccountPropertiesViewModel by activityViewModels()
    private val authTokenViewModel: AuthTokenViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_register_pet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val userToken = authTokenViewModel.getUserToken(requireActivity())

        var petName = ""
        var registerNum =""
        var petBirthYear = ""

        check_register_pet_number.setOnClickListener {
            petName = et_register_pet_name.text.toString()
            registerNum = et_register_pet_number.text.toString()
            petBirthYear = et_register_pet_birth_year.text.toString()

            checkWithPublicApi(petName, registerNum, petBirthYear)
        }

        img_register_pet_image.setOnClickListener {
            getAlbum()
        }
        //check public api response
        registerViewModel.getCheckPetResponse().observe(viewLifecycleOwner, Observer {
            response ->
            if(response != null){
                //open up bottom sheet dialog
                val bottomSheetDialog = BottomSheetDialog(
                    requireContext(),
                    R.style.BottomSheetDialogTheme
                )
                val bottomSheetView = LayoutInflater.from(requireContext())
                    .inflate(R.layout.layout_bottom_sheet, null)

                bottomSheetView.apply {
                    findViewById<TextView>(R.id.check_pet_name).text = getString(R.string.checkname, petName)
                    findViewById<TextView>(R.id.check_pet_breed).text = getString(R.string.checkbreed, response.breed)
                    findViewById<TextView>(R.id.check_pet_birth_year).text = getString(R.string.checkyear, petBirthYear.toInt())
                    findViewById<TextView>(R.id.check_pet_neutered).text = when(response.isNeutered){
                        true -> getString(R.string.checkneuter, "OK")
                        else -> getString(R.string.checkneuter, "NO")
                    }
                    findViewById<TextView>(R.id.check_pet_gender).text = getString(R.string.checkgender, response.gender)
                    findViewById<TextView>(R.id.check_pet_num).text = getString(R.string.checknumber, response.registerNumber)
                }
                bottomSheetView.findViewById<Button>(R.id.check_pet_user_ok).setOnClickListener {
                    //if user click OK, save to checkedPet
                    //register pet to user
                    registerPetToUser(
                        userToken,
                        PetData(
                            petName = petName,
                            registerNumber = registerNum,
                            birthYear = petBirthYear.toInt(),
                            breed =  response.breed,
                            isNeutered = response.isNeutered,
                            gender = response.gender,
                            rfidCode = response.registerNumber,
                            petProfilePhoto = null
                        )
                    )
                }
                bottomSheetView.findViewById<Button>(R.id.check_pet_user_no).setOnClickListener {
                    //dismiss the dialog
                    //let user fix information
                    bottomSheetDialog.dismiss()
                }
                bottomSheetDialog.apply {
                    setContentView(bottomSheetView)
                    show()
                }
            }else{
                Toast.makeText(requireContext(), "동물등록번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        })

        //observe register response
        //결과를 관찰한다.
        accountPropertiesViewModel.fetchUser(userToken)

        accountPropertiesViewModel.userProperty.observe(viewLifecycleOwner, Observer {
            //get user id => get pet register response => insert pet to user
            registerViewModel.getRegisterPetResponse().observe(viewLifecycleOwner, Observer {
                    petResponse ->
                when {
                    petResponse == null -> {
                        Log.d(TAG, "registerPetInfoToUser: REGISTER FAILED")
                    }
                    petResponse.status == "success" -> {
                        Log.d(TAG, "registerPetInfoToUser: 펫 등록 성공")
                            insertPetToDatabase(it.userId, petResponse.data)
                        //이전 화면으로 이동하기
                        findNavController().navigate(R.id.action_registerPetFragment_to_petInfoFragment)
                    }
                    else -> {
                        Log.d(TAG, "registerPetInfoToUser: REGISTER FAILED, $petResponse")
                    }
                }
            })
        })

    }

    private fun insertPetToDatabase(userId : Int, petData: PostPetResponseData) {
        accountPropertiesViewModel.insertPetToDb(
            Pet(
                petId = petData.id,
                petName = petData.name,
                petBirthYear = petData.year,
                dogRegisterNo = petData.registerNum,
                rfidCardNo = petData.rfidCode,
                gender = petData.gender,
                kind = petData.breed,
                isNeutered = petData.isNeutered,
                ownerId = userId
            )
        )
    }

    private fun registerPetToUser(userToken: String, petData: PetData) {
        val map = HashMap<String, RequestBody>()
        map["data[petName]"] = petData.petName
            .toRequestBody("text/plain".toMediaTypeOrNull())
        map["data[registerNumber]"] = petData.registerNumber
            .toRequestBody("text/plain".toMediaTypeOrNull())
        map["data[year]"] = petData.birthYear.toString()
            .toRequestBody("text/plain".toMediaTypeOrNull())
        map["data[breed]"] = petData.breed
            .toRequestBody("text/plain".toMediaTypeOrNull())
        map["data[isNeutered]"] = petData.isNeutered.toString()
            .toRequestBody("text/plain".toMediaTypeOrNull())
        map["data[gender]"] = petData.gender
            .toRequestBody("text/plain".toMediaTypeOrNull())
        map["data[rfidCode]"] = petData.rfidCode
            .toRequestBody("text/plain".toMediaTypeOrNull())

                //map["photo"] = petPhotoFile
        registerViewModel.registerPetToUser(
            token = userToken,
            registerPetParts = map,
            petProfileUrl = null
        )
    }

    private fun checkWithPublicApi(name : String, registerNum: String, petBirthYear : String) {
        /*필드 중 하나라도 비어있으면 정보 입력 하세요 토스트 */
        if(name.isEmpty() ||
            registerNum.isEmpty() ||
            petBirthYear.isEmpty() ||
            petBirthYear.toInt() < 2000){

            Toast.makeText(requireContext(),
                getString(R.string.enter_info_msg),
                Toast.LENGTH_SHORT).show()
        } else {
            registerViewModel.checkPetNumber(
                CheckPetBody(
                    petName = name,
                    registerNumber = registerNum,
                    birthYear = petBirthYear.toInt()
                )
            )
        }
    }
}