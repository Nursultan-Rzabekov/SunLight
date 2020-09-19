package com.example.sunlightdesign.ui.auth.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.sunlightdesign.R


class BannerViewPagerAdapter(
        private var context: Context,
        private var onPageSelected: OnPageSelected
) : PagerAdapter() {

    override fun destroyItem(
            container: ViewGroup,
            position: Int,
            arg1: Any
    ) = Unit

    override fun getCount(): Int {
        return 0
    }

    override fun isViewFromObject(collection: View, `object`: Any): Boolean {
        return collection === `object` as View
    }

    @SuppressLint("InflateParams")
    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val inflater = collection.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.banner_image_item, null)
        val imageView = view.findViewById<ImageView>(R.id.image_set)

//        imageView.setOnClickListener {
//            onPageSelected.onPageSelectedByPosition(listNews.news[position].id)
//        }

        (collection as ViewPager).addView(view, 0)
        return view
    }

    interface OnPageSelected{
        fun onPageSelectedByPosition(position: Int)
    }
}