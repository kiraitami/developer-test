package com.example.developertest.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager

import com.example.developertest.R
import com.example.developertest.adapters.PicturesAdapter
import com.example.developertest.models.Picture
import com.example.developertest.network.RetrofitConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_pictures.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class PicturesFragment : Fragment() {

    var disposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pictures, container, false)
    }

    override fun onResume() {
        super.onResume()
        getPicturesByRetrofit()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    private fun showPictures(pictureList: List<Picture>){
        val recyclerView = pictures_recycler_view
        recyclerView.adapter = PicturesAdapter(this.requireContext(), pictureList)
        recyclerView.layoutManager = GridLayoutManager(this.requireContext(), 2)
    }

    private fun getPicturesByRetrofit(){

        disposable = RetrofitConfig().endPointService().getListOfPictures()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> showPictures(result) },
                { error -> Toast.makeText(context, error.message, Toast.LENGTH_LONG).show() }
            )

        /*
        val call = RetrofitConfig().endPointService().getListOfPictures()
        call.enqueue(object : Callback<List<Picture>?> {
            override fun onResponse(call: Call<List<Picture>?>, response: Response<List<Picture>?>) {
                response?.body()?.let { it ->
                    val pictureList: List<Picture> = it
                    showPictures(pictureList)
                }
            }

            override fun onFailure(call: Call<List<Picture>?>, t: Throwable) {
                Log.e("ERROR", t?.message)
            }
        })
        */
    }

}
