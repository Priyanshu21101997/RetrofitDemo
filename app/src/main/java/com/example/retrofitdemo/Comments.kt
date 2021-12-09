package com.example.retrofitdemo

import com.google.gson.annotations.SerializedName

class Comments(postId:Int,id:Int,name:String,email:String,text:String) {

    var postId = postId

    var id = id

    var name = name

    var email = email

    @SerializedName("body")
    var text = text
}