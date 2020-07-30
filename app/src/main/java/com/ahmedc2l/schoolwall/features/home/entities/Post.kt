package com.ahmedc2l.schoolwall.features.home.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
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
): Parcelable{
 override fun toString(): String {
  return "{id: $id, type: $type, userName: $userName, title: $title, userTitle: $userTitle, " +
          "userProfileImageUrl: $userProfileImageUrl, shortDescription: $shortDescription, createdData: $createdDate," +
          "postImageUrl: $posterImageUrl, sharingUrl: $sharingUrl}"
 }
}