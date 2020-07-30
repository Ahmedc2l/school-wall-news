package com.ahmedc2l.schoolwall.features.home.models

import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class GetNewsApiResult(
    val responseCode: Int,
    val responseData: Data
)

@JsonClass(generateAdapter = true)
data class Data(
    val items: List<NewsItemApiModel>
)

@JsonClass(generateAdapter = true)
data class NewsItemApiModel(
    val type: Int,
    val item: ItemData
)

fun List<NewsItemApiModel>.asDatabaseModel(): Array<NewsItemDatabaseModel>{
    return map {
        val image: String = it.item.image
            ?: if(it.item.medias != null && it.item.medias.isNotEmpty())
                it.item.medias[0]
            else
                "https://www.renovabike.it/wp-content/themes/gecko/assets/images/placeholder.png"

        var stringDate = it.item.createdDate
        var format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val date = format.parse(it.item.createdDate)
        date?.let {
            format = SimpleDateFormat("MMM dd 'at' HH:mm", Locale.ENGLISH)
            stringDate = format.format(date)
        }

        NewsItemDatabaseModel(it.item.id, it.type, it.item.userName, it.item.title, it.item.userPosition, it.item.userImage,
            it.item.shortDesc, stringDate, image, it.item.sharingUrl)
    }.toTypedArray()
}

@JsonClass(generateAdapter = true)
data class ItemData(
    val id: String,
    val userImage: String,
    val userName: String,
    val userPosition: String,
    val title: String,
    val shortDesc: String,
    val createdDate: String,
    val image: String?,
    val sharingUrl: String?,
    val medias: List<String>?
)