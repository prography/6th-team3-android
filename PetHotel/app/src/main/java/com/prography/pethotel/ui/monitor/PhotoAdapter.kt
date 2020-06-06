package com.prography.pethotel.ui.monitor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.prography.pethotel.R
import kotlinx.android.synthetic.main.monitor_photo_video_view_holder.view.*

class PhotoAdapter(
    val context : Context,
    val mediaList : ArrayList<MonitorMedia>
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.monitor_photo_video_view_holder, container, false)
        val tvComment = view.monitor_photo_comment
        val imgMedia = view.monitor_photo
        val tvDate = view.monitor_photo_date

        tvComment.text = mediaList[position].mediaComment
        Glide.with(context).load(mediaList[position].mediaUrl)
            .placeholder(R.drawable.pet)
            .error(R.drawable.detail_scrim_gradient)
            .into(imgMedia)
        tvDate.text = mediaList[position].createdAt

        container.addView(view)
        return view
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        if(mediaList.size > 10){
            return 10
        }
        return mediaList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}