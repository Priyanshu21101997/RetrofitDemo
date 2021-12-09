package com.example.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var jsonPlaceHolderApi: JsonPlaceHolderApi
    private lateinit var textView: TextView

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById<TextView>(R.id.textView)

            // This is the code for logging our responses

        val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
                .build()


        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient )  // This line added for logging
            .build()

        // Retrofit will automatically take care of implementation of interface's methods
            jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)

            // GET ENDPOINTS

//        getPosts()
//        getComments()
//        createPost()

            // Put patch both covered in here
            updatePost()

//            deletePost()



    }

    private fun deletePost(){
        val call:Call<Unit> = jsonPlaceHolderApi.deletePost(1)

        call.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                textView.text = "Code = ${response.code()}"
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                textView.text = t.message
            }
        })


    }

    private fun updatePost(){

        // Map of headers

        val headers = HashMap<String,String>()
        headers.put("Map-Header-1","123")
        headers.put("Map-Header-2","456")

        val postObj = Post(100,0,"TITLE","THIS IS THE TEXT OF NEW TITLE")
        val call:Call<Post> = jsonPlaceHolderApi.putPost(headers,1,postObj)

        // PATCH OBJ
        // Since we are having title null it will get neglected while updating and default title
        // will be there . This is speciality of PATCH.
//        val patchObj = Post(userId = 12,0,null,"NEW TEXT")
//        val call:Call<Post> = jsonPlaceHolderApi.patchPost(2,patchObj)

        call.enqueue(object : Callback<Post>{
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                // Here we can also get 404 response when request was successful but data was not there

                if(!response.isSuccessful){
                    textView.text = ("Code: "  + response.code() )
                    return
                }

                val postReturnObj:Post? = response.body()

                var stringToShow:String = ""

                if (postReturnObj != null) {
                    stringToShow += "ID : ${postReturnObj.id} " + "\n" +
                            "USER ID : ${postReturnObj.userId} " + "\n" +
                            "TITLE : ${postReturnObj.title} " + "\n" +
                            "STATUS CODE : ${response.code()} " + "\n" +
                            "TEXT : ${postReturnObj.text} " + "\n \n \n"
                }

                textView.append(stringToShow)


            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                textView.text = t.message
            }
        })



    }

    private fun createPost(){
//        val postObj = Post(100,0,"NEW TITLE","THIS IS THE TEXT OF NEW TITLE")

        val call:Call<Post> = jsonPlaceHolderApi.createPost(1000,"New Title","New Text")

        call.enqueue(object : Callback<Post>{
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                // Here we can also get 404 response when request was successful but data was not there

                if(!response.isSuccessful){
                    textView.text = ("Code: "  + response.code() )
                    return
                }

                val postReturnObj:Post? = response.body()

                var stringToShow:String = ""

                if (postReturnObj != null) {
                        stringToShow += "ID : ${postReturnObj.id} " + "\n" +
                                "USER ID : ${postReturnObj.userId} " + "\n" +
                                "TITLE : ${postReturnObj.title} " + "\n" +
                                "STATUS CODE : ${response.code()} " + "\n" +
                                "TEXT : ${postReturnObj.text} " + "\n \n \n"
                }

                textView.append(stringToShow)


            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                textView.text = t.message
            }
        })


    }

    private fun getComments(){
        val call:Call<List<Comments>> = jsonPlaceHolderApi.getComments(4)

        call.enqueue(object : Callback<List<Comments>>{
            override fun onResponse(call: Call<List<Comments>>, response: Response<List<Comments>>) {
                // Here we can also get 404 response when request was successful but data was not there

                if(!response.isSuccessful){
                    textView.text = ("Code: "  + response.code() )
                    return
                }

                val comments:List<Comments>? = response.body()

                var stringToShow:String = ""

                if (comments != null) {
                    for(singleComment in comments){
                        stringToShow += "ID : ${singleComment.id} " + "\n" +
                                "POST ID : ${singleComment.postId} " + "\n" +
                                "NAME : ${singleComment.name} " + "\n" +
                                "E-MAIL : ${singleComment.email} " + "\n" +
                                "TEXT : ${singleComment.text} " + "\n \n \n"
                    }
                }

                textView.append(stringToShow)


            }

            override fun onFailure(call: Call<List<Comments>>, t: Throwable) {
                textView.text = t.message
            }
        })
    }

    private fun getPosts(){

        // Defining usage of QueryMAP
        val parameters = HashMap<String,String>()
        parameters.put("userId","1")
        parameters.put("_sort","id")
        parameters.put("_order","desc")


        val call:Call<List<Post>> = jsonPlaceHolderApi.getPosts(parameters)

        // Does synchronised call . So if we do on main thread a network call we will get a exception
        // call.execute() // NOT USING HERE

        // Instead we will use call.enqueue() -> It will deserialize the JSON Response
        call.enqueue(object : Callback<List<Post>>{
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                // Here we can also get 404 response when request was successful but data was not there

                if(!response.isSuccessful){
                    textView.text = ("Code: "  + response.code() )
                    return
                }

                val posts:List<Post>? = response.body()

                var stringToShow:String = ""

                if (posts != null) {
                    for(singlePost in posts){
                        stringToShow += "ID : ${singlePost.id} " + "\n" +
                                "USER ID : ${singlePost.userId} " + "\n" +
                                "TITLE : ${singlePost.title} " + "\n" +
                                "TEXT : ${singlePost.text} " + "\n \n \n"
                    }
                }

                textView.append(stringToShow)


            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                textView.text = t.message
            }
        })
    }
}