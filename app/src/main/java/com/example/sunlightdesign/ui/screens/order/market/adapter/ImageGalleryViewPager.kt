package com.example.sunlightdesign.ui.screens.order.market.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TimePicker
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.sunlightdesign.R
import com.example.sunlightdesign.utils.getImageUrl
import timber.log.Timber

class ImageGalleryViewPager(
    private val images: List<String>
) : PagerAdapter() {

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        arg1: Any
    ) = Unit

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }

    override fun getCount(): Int = images.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = container.context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.image_gallery_pager_item, null)

        val imageView = view.findViewById<ImageView>(R.id.itemImageView)

        Timber.d(getImageUrl(images[position]))
        Glide.with(view)
            .load(getImageUrl(images[position]))
            .centerInside()
            .into(imageView)

        (container as ViewPager).addView(view, 0)
        return view
    }
}