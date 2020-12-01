package com.example.sunlightdesign.ui.screens.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.Category
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.launcher.adapter.BannerViewPagerAdapter
import com.example.sunlightdesign.ui.launcher.adapter.PostAdapter
import com.example.sunlightdesign.ui.launcher.adapter.CategoriesAdapter
import com.example.sunlightdesign.ui.launcher.company.CompanyActivity
import com.example.sunlightdesign.ui.launcher.news.NewsActivity
import com.example.sunlightdesign.ui.screens.home.structure.StructureActivity
import com.example.sunlightdesign.ui.screens.order.market.MarketActivity
import com.example.sunlightdesign.utils.NumberUtils
import com.example.sunlightdesign.utils.showMessage
import kotlinx.android.synthetic.main.launcher_authenticated.*
import kotlinx.android.synthetic.main.sunlight_banner.*


class HomeFragment : StrongFragment<HomeViewModel>(HomeViewModel::class),
    BannerViewPagerAdapter.OnPageSelected,
    PostAdapter.PostInteraction,
    CategoriesAdapter.CategoryInterface {

    private lateinit var categoriesAdapter: CategoriesAdapter

    private lateinit var postAdapter: PostAdapter

    private val handler = Handler()
    private val delay = 3000L //milliseconds

    private var page = 0
    private var newsViewPagerAdapter: BannerViewPagerAdapter? = null

    var runnable: Runnable = object : Runnable {
        override fun run() {
            if (newsViewPagerAdapter?.count === page) {
                page = 0
            } else {
                page++
            }
            news_view_pager.setCurrentItem(page, true)
            handler.postDelayed(this, delay)
        }
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, delay)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.launcher_authenticated, container, false)
    }

    override fun onActivityCreated(savedInstanceViewState: Bundle?) {
        super.onActivityCreated(savedInstanceViewState)


        btn_enter_cv.visibility = View.GONE
        btn_structure_cv.visibility = View.VISIBLE

        btn_company_cv.setOnClickListener {
            startActivity(Intent(context, CompanyActivity::class.java))
        }

        setObservers()
        setListeners()

        viewModel.getBanners()
        viewModel.getCategories()
        viewModel.getPosts()
    }

    private fun setObservers() {
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.visibility = if (it) View.VISIBLE else View.GONE
            })
            banners.observe(viewLifecycleOwner, Observer {
                if(it.banners.isNotEmpty()){
                    sunlight_banner_lv.visibility = View.VISIBLE
                    newsViewPagerAdapter = BannerViewPagerAdapter(
                        banners = it, context = requireContext(), onPageSelected = this@HomeFragment
                    )
                    news_view_pager.adapter = newsViewPagerAdapter
                    news_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                        override fun onPageScrolled(
                            position: Int,
                            positionOffset: Float,
                            positionOffsetPixels: Int
                        ) {
                        }

                        override fun onPageSelected(position: Int) {
                            newsViewPagerAdapter?.let { viewPager ->
                                if (position == (viewPager.count - 1)) {
                                    page = (viewPager.count - 1) - position - 1
                                } else {
                                    page = position
                                }
                            }
                        }

                        override fun onPageScrollStateChanged(state: Int) {}
                    })
                    dots_indicator.attachViewPager(news_view_pager)
                    dots_indicator.setDotTintRes(R.color.sunBlackColor)
                }
            })
            categories.observe(viewLifecycleOwner, Observer {
                it.categories.first().isSelected = true
                categoriesAdapter =
                    CategoriesAdapter(items = it.categories, categoryInterface = this@HomeFragment)
                categories_recycler_view.adapter = categoriesAdapter

                viewModel.getPostsByCategoryId(it.categories.first().id)
            })
            posts.observe(viewLifecycleOwner, Observer {

            })
            postsById.observe(viewLifecycleOwner, Observer {
                postAdapter = PostAdapter(requireContext(), it.posts, this@HomeFragment)
                post_recyclerview.adapter = postAdapter
            })
        }
    }

    private fun setListeners() {
        btn_company_cv.setOnClickListener {
            startActivity(Intent(context, CompanyActivity::class.java))
        }

        btn_structure_cv.setOnClickListener {
            startActivity(Intent(requireContext(), StructureActivity::class.java))
        }

        btn_market_cv.setOnClickListener {
            startActivity(Intent(requireContext(),MarketActivity::class.java))
        }
    }

    override fun onPageSelectedByPosition(id: Int) {
//        val bundle = bundleOf(
//            NewsActivity.KEY_POST_ID to id
//        )
//        findNavController().navigate(R.id.action_homeFragment_to_newsActivity2, bundle)
    }

    override fun onPostClicked(id: Int) {
        val bundle = bundleOf(
            NewsActivity.KEY_POST_ID to id
        )
        findNavController().navigate(R.id.action_homeFragment_to_newsActivity2, bundle)
    }

    override fun onCategorySelected(item: Category) {
        viewModel.getPostsByCategoryId(item.id)
    }

}