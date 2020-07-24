package com.prography.pethotel.ui.mypage.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prography.pethotel.R
import com.prography.pethotel.room.entities.Pet
import kotlinx.android.synthetic.main.pet_list_layout.view.*


private const val TAG = "PetListAdapter"
class PetListAdapter(
    private val petList : List<Pet>,
    val context : Context
)
    : RecyclerView.Adapter<PetListAdapter.PetListViewHolder>() {


    class PetListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pet : Pet){
            Log.d(TAG, "bind: ${pet.gender}")

            val gender = if(pet.gender == "MALE"){ "수컷" }else{ "암컷" }
            val desc = "${pet.petName} / $gender / ${pet.petBirthYear}"
            val num = pet.dogRegisterNo
            val breed = pet.kind
            val profileImage = pet.profileImagePath

            itemView.pet_name_gender_year.text = desc
            itemView.pet_number.text = num
            itemView.pet_breed. text = breed

            if(!profileImage.isNullOrEmpty()){
                Glide.with(itemView.context)
                    .load(profileImage)
                    .error(R.drawable.mily_sleepy)
                    .placeholder(R.drawable.mily_happy)
                    .into(itemView.pet_image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.pet_list_layout, parent, false)
        return PetListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return petList.size
    }

    override fun onBindViewHolder(holder: PetListViewHolder, position: Int) {
        holder.bind(petList[position])
    }
}