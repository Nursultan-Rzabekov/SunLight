package com.example.sunlightdesign.ui.launcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.Banners
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.Categories
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.Post
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.Posts
import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.usecase.usercase.SharedUseCase
import com.example.sunlightdesign.usecase.usercase.mainUse.get.*

/**
 * ViewModel for the task list screen.
 */

class LauncherViewModel constructor(
    sharedUseCase: SharedUseCase,
    private val getMainPostUseCase: GetMainPostUseCase,
    private val getMainCategoriesUseCase: GetMainCategoriesUseCase,
    private val getMainBannersUseCase: GetMainBannersUseCase,
    private val getPostsByCategoryId: GetPostsByCategoryId,
    private val getPostByIdUseCase: GetPostByIdUseCase
) : StrongViewModel() {

    val progress = MutableLiveData<Boolean>(false)
    private var _bearerToken = MutableLiveData<Boolean>(false)
    val bearerToken get() = _bearerToken

    private var _banners = MutableLiveData<Banners>()
    val banners:LiveData<Banners> get() = _banners

    private var _categories = MutableLiveData<Categories>()
    val categories:LiveData<Categories> get() = _categories

    private var _posts = MutableLiveData<Posts>()
    val posts:LiveData<Posts> get() = _posts

    private var _postsById = MutableLiveData<Posts>()
    val postsById:LiveData<Posts> get() = _postsById

    private var _postItem = MutableLiveData<Post?>()
    val postItem: LiveData<Post?> get() = _postItem

    init {
        if (!sharedUseCase.getSharedPreference().bearerToken.isNullOrEmpty())
            _bearerToken.postValue(true)
    }

    fun getBanners(){
        progress.postValue(true)
        getMainBannersUseCase.execute {
            onComplete {
                progress.postValue(false)
                _banners.postValue(it)
            }
            onNetworkError {
                progress.postValue(false)
                handleError(errorMessage = it.message)
            }
            onError {
                progress.postValue(false)
                handleError(throwable = it)
            }
        }
    }

    fun getCategories(){
        progress.postValue(true)
        getMainCategoriesUseCase.execute {
            onComplete {
                progress.postValue(false)
                _categories.postValue(it)
            }
            onNetworkError {
                progress.postValue(false)
                handleError(errorMessage = it.message)
            }
            onError {
                progress.postValue(false)
                handleError(throwable = it)
            }
        }
    }

    fun getPosts(){
        progress.postValue(true)
        getMainPostUseCase.execute {
            onComplete {
                progress.postValue(false)
                _posts.postValue(it)
            }
            onNetworkError {
                progress.postValue(false)
                handleError(errorMessage = it.message)
            }
            onError {
                progress.postValue(false)
                handleError(throwable = it)
            }
        }
    }

    fun getPostsByCategoryId(id: Int){
        progress.postValue(true)
        getPostsByCategoryId.setItems(id)
        getPostsByCategoryId.execute {
            onComplete {
                progress.postValue(false)
                _postsById.postValue(it)
            }
            onNetworkError {
                progress.postValue(false)
                handleError(errorMessage = it.message)
            }
            onError {
                progress.postValue(false)
                handleError(throwable = it)
            }
        }
    }

    fun getPostById(id: Int) {
        progress.postValue(true)
        getPostByIdUseCase.setData(PostByIdModel(id))
        getPostByIdUseCase.execute {
            onComplete {
                progress.postValue(false)
                _postItem.postValue(it?.post)
            }
            onNetworkError {
                progress.postValue(false)
                handleError(errorMessage = it.message)
            }
            onError {
                progress.postValue(false)
                handleError(throwable = it)
            }
        }
    }

}


