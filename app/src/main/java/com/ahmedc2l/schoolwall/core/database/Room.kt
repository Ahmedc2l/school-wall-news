package com.ahmedc2l.schoolwall.core.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.ahmedc2l.schoolwall.features.home.models.NewsItemDatabaseModel

@Dao
interface NewsDao{
    @Query("SELECT * FROM news")
    fun getAllFeed(): LiveData<List<NewsItemDatabaseModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNews(vararg news: NewsItemDatabaseModel)
}

@Database(entities = [NewsItemDatabaseModel::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase(){
    abstract val newsDao: NewsDao
}

private lateinit var INSTANCE: AppDatabase

fun getDatabase(context: Context): AppDatabase{
    synchronized(AppDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).build()
        }
    }
    return INSTANCE
}