package com.example.proyectofinal.dataLayer.dataSource.retrofit

import com.example.proyectofinal.dataLayer.dataSource.retrofit.services.PuppyApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private const val BASE_URL = "https://dog.ceo"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder().build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val dogBreedsService: PuppyApiService by lazy {
        retrofit.create(PuppyApiService::class.java)
    }

}