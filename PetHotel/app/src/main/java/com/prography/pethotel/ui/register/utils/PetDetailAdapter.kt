package com.prography.pethotel.ui.register.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.prography.pethotel.R
import com.prography.pethotel.models.PetInfo
import com.prography.pethotel.utils.TAG_PET_DETAIL
import kotlinx.android.synthetic.main.pet_detail_layout.view.*


//val petInfoDetailViewModel : ViewModel = RegisterPetInfoDetailViewModel()

class PetDetailAdapter(
    val petInfoList: ArrayList<PetInfo>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    lateinit var holder : RecyclerView.ViewHolder
    lateinit var view : View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == TYPE_HEADER){
            view = LayoutInflater.from(parent.context).inflate(R.layout.pet_detail_rv_header, parent, false)
            holder = PetDetailHeaderViewHolder(view)
        }else if(viewType == TYPE_ITEM){
            view = LayoutInflater.from(parent.context).inflate(R.layout.pet_detail_layout, parent, false)
            holder = PetDetailViewHolder(view)
        }

        return holder
    }

    override fun getItemCount(): Int {
        // 헤더, 푸터 개수만큼 추가해 주어야 함.
        return petInfoList.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is PetDetailHeaderViewHolder){
            val headerViewHolder : PetDetailHeaderViewHolder = holder
        }else if (holder is PetDetailViewHolder){
            val itemViewHolder : PetDetailViewHolder = holder
            //itemViewHolder.bind()
            Log.d(TAG_PET_DETAIL, holder.adapterPosition.toString())
            itemViewHolder.listenEditText()
            itemViewHolder.checkPetNumberOnButtonClick()

        }
    }


    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> TYPE_HEADER
            else -> TYPE_ITEM
        }
    }

    class PetDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val etPetName : EditText = itemView.et_register_pet_name
        val etPetNumber : EditText = itemView.et_register_pet_number
        val etPetBirthYear : EditText = itemView.et_register_pet_birth_year
        val btnCheckPetNumber : Button = itemView.check_register_pet_number
        val checkButton : ImageButton = itemView.register_pet_number_checked

        fun listenEditText(){
            etPetName.addTextChangedListener(
                onTextChanged = {
                        charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->
                    Log.d(TAG_PET_DETAIL, "position : $adapterPosition / $charSequence")

                }
            )

            etPetNumber.addTextChangedListener(
                onTextChanged = {
                        charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->
                    Log.d(TAG_PET_DETAIL, "position : $adapterPosition / $charSequence")
                }
            )

            etPetBirthYear.addTextChangedListener(
                onTextChanged = {
                        charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->
                    Log.d(TAG_PET_DETAIL, "position : $adapterPosition / $charSequence")
                }
            )
        }

        fun checkPetNumberOnButtonClick(){
            btnCheckPetNumber.setOnClickListener {
                //동물 등록번호 확인 -> 서버에 등록번호 보내기 -> 서버에서 정상 응답 오면
                //받아온 정보들 저장하기. (추후에 PetInfo DB 테이블 필드에 맞게 수정해야 함)

                //postPetInfoToServer(PetInfo(name, number, birthYear))

                //서버에서 받아온 정보는 setter 를 통해서 객체에 정보 추가
                Toast.makeText(itemView.context, "Checked", Toast.LENGTH_SHORT).show()

                //getPetInfoFromServer(PetInfo) -> Save to local Database

                //서버 응답 상태에 따라서 progress bar -> checked 상태로 넘어가는 로직 구현하기.
                checkButton.visibility = View.VISIBLE
                //서버에서 API 요청 날리고, OK 응답 받으면 서버에 PET INFO 저장하기.
                // 만약 실패이면 경고 메시지 띄우기. (X 표 아이콘으로 이미지 버튼 대체하기.)

            }
        }
    }


    class PetDetailHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}


