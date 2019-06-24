package com.ucong.blog_kotlin.apiHelper

import android.widget.EditText
import com.ucong.blog_kotlin.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface APIServices {

    @POST("login")
    @FormUrlEncoded
    fun registrationPost(

        @Field("email") email: String,
        @Field("password") password: String): Call<User>


    @POST("register")
    @FormUrlEncoded
    fun registrationPost(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("c_password") c_password: String
    ): Call<User>

    // call API http://localhost:8000/api/logout
    @GET("logout")
    fun userLogout(): Call<ResponseBody>

    @GET("getDetails")
    fun getPosts(): Call<List<ResponseBody>>

}

