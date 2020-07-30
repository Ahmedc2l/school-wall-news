package com.ahmedc2l.schoolwall.features.home.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ahmedc2l.schoolwall.features.home.entities.Post

@Entity(tableName = "news")
data class NewsItemDatabaseModel(
    @PrimaryKey()
    val id: String,
    val type: Int,
    val userName: String,
    val title: String,
    val userTitle: String,
    val userProfileImageUrl: String,
    val shortDescription: String,
    val createdDate: String,
    val posterImageUrl: String,
    val sharingUrl: String?
)

fun List<NewsItemDatabaseModel>.asDomainModel(): List<Post> {
    return map {
        Post(it.id, it.type, it.userName, it.title, it.userTitle, it.userProfileImageUrl, it.shortDescription
            , it.createdDate, it.posterImageUrl, it.sharingUrl)
    }
}