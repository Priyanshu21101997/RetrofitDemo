package com.example.retrofitdemo

import retrofit2.Call
import retrofit2.http.*


interface JsonPlaceHolderApi {

//     Normal URL
//    @GET("posts")
//    fun getPosts():Call<List<Post>>

//    URL Manipulation with @QUERY -> www.example.com/posts?userId=functionUserId
//    @GET("posts")
//    fun  getPosts(@Query("userId") functionUserId:Int):Call<List<Post>>

    // Multiple Query
    // Sort based on "sort field " and order by "order field" ed -> sort-> id order desc
//    @GET("posts")
//    fun  getPosts(@Query("userId") userId:Int,@Query("_sort") sort:String,@Query("_order") order:String):Call<List<Post>>

//     Can also pass list of USER ID ed -> getPosts(listof(4,5,6),id,desc)
//    @GET("posts")
//    fun  getPosts(@Query("userId") userId:List<Int>,@Query("_sort") sort:String,@Query("_order") order:String):Call<List<Post>>

//    // QUERYMAP USE
//    // IF I DON'T WANT TO DEFINE FIXED QUERY PARAMS INSTEAD USE SOME QUERY PARAMS OF CHOICE
    @GET("posts")
    fun getPosts(@QueryMap parameters:MutableMap<String,String>):Call<List<Post>>

    // URL Manipulation with @PATH
    @GET("posts/{id}/comments")
    fun getComments(@Path("id") postId:Int):Call<List<Comments>>


    // POST ENDPOINTS

    // Passing json body (Gson converts java obj into json)
//    @POST("posts")
//    fun createPost(@Body post:Post):Call<Post>

    // Using Forms Way
    // createPost(23,"Title","Text")
    @FormUrlEncoded
    @POST("posts")
    fun createPost(
        @Field("userId") userId:Int,
        @Field("title") title:String,
        @Field("body") text:String
    ):Call<Post>

//    @PUT("posts/{id}")
//    fun putPost(@Path("id") id:Int,@Body post:Post):Call<Post>

    @PATCH("posts/{id}")
    fun patchPost(@Path("id") id:Int,@Body post:Post):Call<Post>

    @DELETE("posts/{id}")
    fun deletePost(@Path("id") id:Int):Call<Unit>

    // Adding headers
//    1) Static headers
//    @Headers("Static-Header1:123","Static-Header2:456")
//    @PUT("posts/{id}")
//    fun putPost(@Path("id") id:Int,@Body post:Post):Call<Post>

    // 2) Dynamic Headers
//    @PUT("posts/{id}")
//    fun putPost(@Header("Dynamic-Header") header:String,@Path("id") id:Int,@Body post:Post):Call<Post>

    //2.1) We can pass Map of headers as well
    @PUT("posts/{id}")
    fun putPost(@HeaderMap headers:MutableMap<String,String> ,@Path("id") id:Int, @Body post:Post):Call<Post>



}