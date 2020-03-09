package com.example.developertest.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.developertest.R
import com.example.developertest.adapters.UsersAdapter
import com.example.developertest.database.db.AppDatabase
import com.example.developertest.database.entities.UserEntity
import com.example.developertest.models.User
import com.example.developertest.network.RetrofitConfig
import com.example.developertest.network.isOnline
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_users.*

/**
 * A simple [Fragment] subclass.
 */

class UsersFragment : Fragment() {

    var disposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {
        val view = inflater.inflate(R.layout.fragment_users, container, false)
        return view
    }

    override fun onResume() {
        super.onResume()
        if (isOnline(requireContext())) {
            getUsersByRetrofit()
        }
        else {
            getUsersFromDatabase()
        }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun showUsers(userList: List<User>){
        val recyclerView = users_recycler_view
        recyclerView.adapter = UsersAdapter(userList, this.requireContext())
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)

    }

    private fun getUsersByRetrofit(){
        disposable = RetrofitConfig().endPointService().getListOfUsers()
            .subscribeOn(Schedulers.computation())
            .doOnEach { result ->
                val db = AppDatabase.getInstance(requireContext()).userDao()
                result.value?.forEach { db.insertUser(it.convertToUserEntity()) }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> showUsers(result.sortedBy { it.name }) },
                { error -> Toast.makeText(context, error.message, Toast.LENGTH_LONG).show() }
            )

        /*
        val call = RetrofitConfig().endPointService().getListOfUsers()
        call.enqueue(object : Callback<List<User>?> {
            override fun onResponse(call: Call<List<User>?>, response: Response<List<User>?>) {
                response?.body()?.let { it ->
                    val userList: List<User> = it
                    showUsers(userList.sortedBy { it.name })
                }
            }

            override fun onFailure(call: Call<List<User>?>, t: Throwable) {
                Log.e("ERROR", t?.message)
            }
        })
         */
    }

    private fun getUsersFromDatabase(){
        disposable = AppDatabase.getInstance(requireContext()).userDao().getUsersFromDb()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> showUsers(result.convertToUserList()) },
                { error -> Toast.makeText(context, error.message, Toast.LENGTH_LONG).show() }
            )
    }

    private fun List<UserEntity>.convertToUserList() : List<User> {
       return this.map { entity -> entity.convertToUser() }.sortedBy { it.name }
    }

}
