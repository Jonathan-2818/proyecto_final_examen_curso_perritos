package com.example.proyectofinal.dataLayer.dataSource.retrofit.services


import com.example.proyectofinal.dataLayer.model.ImgsBreeds
import com.example.proyectofinal.dataLayer.model.PuppyBreeds
import retrofit2.http.GET
import retrofit2.http.Path

interface PuppyApiService {
    @GET("api/breeds/list/all")
    suspend fun getBreeds(): PuppyBreeds

    @GET("api/breed/{breeds}/images/random/3")
    suspend fun getBreedsImages(
        @Path("breeds") breeds:String,
    ): ImgsBreeds

    @GET("api/breed/{breeds}/{hound}/images/random/3")
    suspend fun getBreedsImages(
        @Path("breeds") breeds:String,
        @Path("hound") hound:String
    ): ImgsBreeds

}