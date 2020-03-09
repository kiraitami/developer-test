package com.example.developertest.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.developertest.R
import com.example.developertest.ui.adapters.PostsAdapter
import com.example.developertest.database.db.AppDatabase
import com.example.developertest.database.entities.PostEntity
import com.example.developertest.models.Post
import com.example.developertest.network.RetrofitConfig
import com.example.developertest.network.isOnline
import com.example.developertest.strings.USER_ID
import com.example.developertest.strings.USER_NAME
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_posts.*

class PostsActivity : AppCompatActivity() {

    private var disposable: Disposable? = null
    private var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        userId = intent.getStringExtra(USER_ID) ?: ""
        val userName = intent.getStringExtra(USER_NAME) ?: "Posts"

        supportActionBar?.title = userName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (isOnline(this))
            getPostsByRetrofit(userId)
        else
            getPostsFromDatabase()
    }

    override fun onStop() {
        super.onStop()
        disposable?.dispose()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showPosts(postList: List<Post>){
        val recyclerView = posts_recycler_view
        recyclerView.adapter =
            PostsAdapter(postList, this)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun getPostsByRetrofit(userId: String){
        disposable = RetrofitConfig().endPointService().getUserPublications(userId)
            .subscribeOn(Schedulers.computation())
            .doOnEach { result ->
                val db = AppDatabase.getInstance(this).postDao()
                result.value?.forEach { db.insertPost(it.convertToPostEntity()) }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> showPosts(result) },
                { error -> Toast.makeText(this, error.message, Toast.LENGTH_LONG).show() }
            )
    }

    private fun getPostsFromDatabase() {
        disposable = AppDatabase.getInstance(this).postDao().getPostsFromDb(userId.toInt())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> showPosts(result.convertToPostList()) },
                { error -> Toast.makeText(this, error.message, Toast.LENGTH_LONG).show() }
            )
    }

    private fun List<PostEntity>.convertToPostList() : List<Post> {
        return this.map { entity -> entity.convertToPost() }
    }
}
