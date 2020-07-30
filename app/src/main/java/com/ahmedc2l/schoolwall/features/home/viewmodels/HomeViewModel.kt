package com.ahmedc2l.schoolwall.features.home.viewmodels

import android.app.Application
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmedc2l.schoolwall.core.database.getDatabase
import com.ahmedc2l.schoolwall.features.home.entities.Post
import com.ahmedc2l.schoolwall.features.home.repositories.HomeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class HomeViewModel(private val application: Application) : ViewModel()  {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(application)
    private val homeRepository =
        HomeRepository(database)

    private val _navigateToPostDetails = MutableLiveData<Post>()
    val navigateToPostDetails: LiveData<Post>
        get() = _navigateToPostDetails

    private val _sharePostUrl = MutableLiveData<Intent>()
    val sharePostUrl: LiveData<Intent>
        get() = _sharePostUrl

    val allFeed = homeRepository.allFeed

    init {
        refreshPosts()
    }

    fun refreshPosts(){
        viewModelScope.launch {
            val params = mapOf("SchoolId" to "d8b66827-8627-494a-b0a2-011046066b4b", "count" to "30")
            homeRepository.getNews(params)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }

    fun clickSharePostUrl(postUrl: String){
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"

            putExtra(Intent.EXTRA_TEXT, postUrl)
        }
        _sharePostUrl.value = shareIntent
    }

    fun onPostShared(){
        _sharePostUrl.value = null
    }

    fun onPostClicked(post: Post){
        _navigateToPostDetails.value = post
    }
    fun onPostDetailsNavigated() {
        _navigateToPostDetails.value = null
    }
}