package com.ahmedc2l.schoolwall.core.network

import com.ahmedc2l.schoolwall.features.home.models.GetNewsApiResult
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://scholigit.developmentyard.org/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


object Network{
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

    val retrofitService : ApiEndpoints by lazy {
        retrofit.create(ApiEndpoints::class.java)
    }
}

interface ApiEndpoints {

    @GET("api/News/GetNews")
    fun getSchoolNewsAsync(@HeaderMap headers: Map<String, String>, @QueryMap quires: Map<String, String>): Deferred<GetNewsApiResult>

}