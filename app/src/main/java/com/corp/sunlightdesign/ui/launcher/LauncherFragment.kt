package com.corp.sunlightdesign.ui.launcher

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
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.data.source.dataSource.remote.main.entity.Category
import com.corp.sunlightdesign.ui.base.StrongFragment
import com.corp.sunlightdesign.ui.launcher.adapter.BannerViewPagerAdapter
import com.corp.sunlightdesign.ui.launcher.adapter.PostAdapter
import com.corp.sunlightdesign.ui.launcher.auth.AuthActivity
import com.corp.sunlightdesign.ui.launcher.company.CompanyActivity
import com.corp.sunlightdesign.ui.launcher.adapter.CategoriesAdapter
import com.corp.sunlightdesign.ui.launcher.news.NewsActivity
import com.corp.sunlightdesign.utils.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.launcher_authenticated.*
import kotlinx.android.synthetic.main.sunlight_banner.*


@Suppress("IMPLICIT_BOXING_IN_IDENTITY_EQUALS")
class LauncherFragment : StrongFragment<LauncherViewModel>(LauncherViewModel::class),
    BannerViewPagerAdapter.OnPageSelected,
    PostAdapter.PostInteraction,
    CategoriesAdapter.CategoryInterface {

    private lateinit var categoriesAdapter: CategoriesAdapter

    private lateinit var postAdapter: PostAdapter

    private val handler = Handler()
    private val delay = 5000L //milliseconds
    private val smoothScrollDelay = 1500

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

        centerBtnText.text = getString(R.string.about_goods)
        news_view_pager.setPageTransformer(true, ZoomOutPageTransformer())

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
                        banners = it, context = requireContext(), onPageSelected = this@LauncherFragment
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
                    dots_indicator.setDotTintRes(R.color.white)
                }

            })
            categories.observe(viewLifecycleOwner, Observer {
                it.categories.first().isSelected = true
                categoriesAdapter = CategoriesAdapter(items = it.categories, categoryInterface = this@LauncherFragment)
                categories_recycler_view.adapter = categoriesAdapter

                viewModel.getPostsByCategoryId(it.categories.first().id)

            })
            posts.observe(viewLifecycleOwner, Observer {

            })
            postsById.observe(viewLifecycleOwner, Observer {
                postAdapter = PostAdapter(requireContext(), it.posts, this@LauncherFragment)
                post_recyclerview.adapter = postAdapter
            })
        }
    }

    private fun setListeners() {
        btn_enter_cv.setOnClickListener {
            startActivity(Intent(context, AuthActivity::class.java))
        }

        btn_company_cv.setOnClickListener {
            startActivity(Intent(context, CompanyActivity::class.java))
        }

        btn_structure_cv.setOnClickListener {

        }

        btn_market_cv.setOnClickListener {
            val aboutGoodCategory = viewModel.categories.value?.categories?.firstOrNull {
                it.id == 3
            } ?: return@setOnClickListener
            val position = viewModel.categories.value?.categories?.indexOfFirst {
                it.id == aboutGoodCategory.id
            } ?: return@setOnClickListener
            viewModel.getPostsByCategoryId(aboutGoodCategory.id)
            categoriesAdapter.selectItem(position)

            nestedScroll.smoothScrollTo(0, newsTextView.top, smoothScrollDelay)
        }
    }

    override fun onPageSelectedByPosition(id: Int) {
        val bundle = bundleOf(
            NewsActivity.KEY_POST_ID to id
        )
        findNavController().navigate(R.id.action_launcher_fragment_to_newsActivity, bundle)
    }

    override fun onPostClicked(id: Int) {
        val bundle = bundleOf(
            NewsActivity.KEY_POST_ID to id
        )
        findNavController().navigate(R.id.action_launcher_fragment_to_newsActivity, bundle)
    }

    override fun onCategorySelected(item: Category) {
        viewModel.getPostsByCategoryId(item.id)
    }
}
