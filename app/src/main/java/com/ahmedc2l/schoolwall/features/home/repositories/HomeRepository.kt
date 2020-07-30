package com.ahmedc2l.schoolwall.features.home.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ahmedc2l.schoolwall.core.database.AppDatabase
import com.ahmedc2l.schoolwall.core.network.Network
import com.ahmedc2l.schoolwall.features.home.entities.Post
import com.ahmedc2l.schoolwall.features.home.models.asDatabaseModel
import com.ahmedc2l.schoolwall.features.home.models.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

private const val TAG = "HomeRepository"

class HomeRepository(private val database: AppDatabase){
    val allFeed: LiveData<List<Post>> = Transformations.map(database.newsDao.getAllFeed()){
        it.asDomainModel()
    }

    suspend fun getNews(params: Map<String, String>){
        withContext(Dispatchers.IO){
            try {
                // TODO add your valid authorization token here
                val headers = mapOf("Accept" to "application/json", "Authorization" to "")
                val apiResult = Network.retrofitService.getSchoolNewsAsync(headers, params).await()

                if(apiResult.responseCode == 200){
                    Log.i(TAG, "getNews: inserting...")
                    database.newsDao.insertNews(*apiResult.responseData.items.asDatabaseModel())
                }else
                    Log.e(TAG, "getNews: API error code is ${apiResult.responseCode} :(")
            }catch (ex: Exception){
                Log.e(TAG, "getNews: ", ex)
            }
        }
    }
}

