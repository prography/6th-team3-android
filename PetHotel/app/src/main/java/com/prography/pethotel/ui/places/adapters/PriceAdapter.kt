package com.prography.pethotel.ui.places.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prography.pethotel.R
import com.prography.pethotel.api.main.response.HotelPrice
import kotlinx.android.synthetic.main.hotel_price_layout_v4.view.*


class PriceAdapter(
    val context : Context,
    private val priceList : ArrayList<HotelPrice>
) : RecyclerView.Adapter<PriceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceViewHolder {
        val view = LayoutInflater.from(context).inflate(
                R.layout.hotel_price_layout_v4, parent, false
            )
        return PriceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return priceList.size
    }

    override fun onBindViewHolder(holder: PriceViewHolder, position: Int) {
            holder.bind(priceList[position])
    }

}

class PriceViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    @SuppressLint("SetTextI18n")
    fun bind(data: HotelPrice) {

        itemView.weight.text = "~ ${(data.weight?.div(1000.00))}kg"
        itemView.price.text = "${data.price}￦"

        if(data.day != null){
            if(data.day == "all"){
                itemView.day.text = "평일/주말"
            }else{
                when (data.day) {
                    "weekday" -> {
                        itemView.day.text = "평일"
                    }
                    "saturday" -> {
                        itemView.day.text = "토요일"
                    }
                    "sunday" -> {
                        itemView.day.text = "일요일"
                    }
                }
            }
        }
    }
}
