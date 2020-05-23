package com.prography.pethotel.ui.register.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.prography.pethotel.R
import kotlinx.android.synthetic.main.pet_detail_layout.view.*

class PetDetailAdapter(
    private val numOfPets : Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1
    private val TYPE_FOOTER = 2

    lateinit var holder : RecyclerView.ViewHolder
    lateinit var view : View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == TYPE_HEADER){
            view = LayoutInflater.from(parent.context).inflate(R.layout.pet_detail_rv_header, parent, false)
            holder = PetDetailHeaderViewHolder(view)
        }else if(viewType == TYPE_ITEM){
            view = LayoutInflater.from(parent.context).inflate(R.layout.pet_detail_layout, parent, false)
            holder = PetDetailViewHolder(view)
        }else{
            // TODO footer 만들기 (입력완료 버튼 추가하기.)
        }

        return holder
    }

    override fun getItemCount(): Int {
        // 헤더, 푸터 개수만큼 추가해 주어야 함.
        return numOfPets + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PetDetailHeaderViewHolder){
            val headerViewHolder : PetDetailHeaderViewHolder = holder
        }else if (holder is PetDetailViewHolder){
            val itemViewHolder : PetDetailViewHolder = holder
            //itemViewHolder.bind()
            Log.d("RVADAPTER", holder.adapterPosition.toString())
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> TYPE_HEADER
            else -> TYPE_ITEM
        }
    }

}


class PetDetailHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

}

class PetDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    fun bind(itemView: View){

    }

}