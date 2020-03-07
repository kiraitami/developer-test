package com.example.developertest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.developertest.R
import com.example.developertest.adapters.PostsAdapter
import com.example.developertest.models.Post
import com.example.developertest.network.RetrofitConfig
import com.example.developertest.strings.USER_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_posts.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsActivity : AppCompatActivity() {

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        val userId = intent.getStringExtra(USER_ID) ?: ""

        getPostsByRetrofit(userId)
    }

    override fun onStop() {
        super.onStop()
        disposable?.dispose()
    }

    private fun showPosts(postList: List<Post>){
        val recyclerView = posts_recycler_view
        recyclerView.adapter = PostsAdapter(postList, this)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun getPostsByRetrofit(userId: String){

        disposable = RetrofitConfig().endPointService().getUserPublications(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> showPosts(result) },
                { error -> Toast.makeText(this, error.message, Toast.LENGTH_LONG).show() }
            )

        /*
        val call = RetrofitConfig().endPointService().getUserPublications(userId)
        call.enqueue(object : Callback<List<Post>?> {
            override fun onResponse(call: Call<List<Post>?>, response: Response<List<Post>?>) {
                response?.body()?.let {
                    val postList: List<Post> = it
                    showPosts(postList)
                }
            }

            override fun onFailure(call: Call<List<Post>?>, t: Throwable) {
                Log.e("ERROR", t?.message)
            }
        })
         */
    }
}
