package com.prography.pethotel.ui.places.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class GenericRecyclerViewAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>{


    var listItems : ArrayList<T>

    constructor(listItems : ArrayList<T>){
        this.listItems = listItems
    }

    constructor(){
        listItems = ArrayList()
    }

    fun setItems(listItems: ArrayList<T>){
        this.listItems = listItems
        notifyDataSetChanged()
    }

    abstract fun getLayoutId(position: Int, obj : T) : Int

    abstract fun getViewHolder(view : View, viewType: Int) : RecyclerView.ViewHolder

    internal interface Binder<T>{
        fun bind(data : T)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false), viewType)
    }


    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Binder<T>).bind(listItems[position])
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutId(position, listItems[position])
    }


}