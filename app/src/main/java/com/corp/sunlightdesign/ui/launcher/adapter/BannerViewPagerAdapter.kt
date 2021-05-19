package com.corp.sunlightdesign.ui.launcher.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.corp.sunlightdesign.BuildConfig
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.data.source.dataSource.remote.main.entity.Banners


class BannerViewPagerAdapter(
    private var banners: Banners,
    private var context: Context,
    private var onPageSelected: OnPageSelected
) : PagerAdapter() {

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        arg1: Any
    ) = Unit

    override fun getCount() = banners.banners.size

    override fun isViewFromObject(collection: View, `object`: Any): Boolean {
        return collection === `object` as View
    }

    @SuppressLint("InflateParams")
    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val inflater = collection.context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.banner_image_item, null)
        val imageView = view.findViewById<ImageView>(R.id.image_set)
        val titleView = view.findViewById<TextView>(R.id.banner_title_tv)

        titleView.text =
            Html.fromHtml(banners.banners[position].content, Html.FROM_HTML_MODE_COMPACT).trim()

        Glide.with(view)
            .load(BuildConfig.BASE_URL_IMAGE + banners.banners[position].media_path_mobile)
            .placeholder(R.drawable.white_bordered_box)
            .error(R.drawable.white_bordered_box)
            .centerCrop().into(imageView)

        imageView.setOnClickListener {
            onPageSelected.onPageSelectedByPosition(banners.banners[position].post_id)
        }

        (collection as ViewPager).addView(view, 0)
        return view
    }

    interface OnPageSelected {
        fun onPageSelectedByPosition(id: Int)
    }
}