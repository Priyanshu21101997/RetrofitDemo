package com.example.retrofitdemo

import com.google.gson.annotations.SerializedName

 class Post(userId:Int,id:Int,title:String?,text:String) {

    var userId = userId

    var id = id

     var title = title

    @SerializedName("body")
    var text = text

}


