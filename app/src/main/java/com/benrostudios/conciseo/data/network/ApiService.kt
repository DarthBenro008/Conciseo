package com.benrostudios.conciseo.data.network


import android.content.Context
import com.benrostudios.conciseo.data.models.ShortnerResponse
import com.benrostudios.conciseo.util.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("shorten")
    suspend fun shortenLink(@Query("url") incomingUrl: String): Response<ShortnerResponse>

    companion object{
        operator fun invoke():ApiService{
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}