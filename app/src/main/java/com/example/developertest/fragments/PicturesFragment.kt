package com.example.developertest.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.example.developertest.R
import com.example.developertest.adapters.PicturesAdapter
import com.example.developertest.models.Picture
import com.example.developertest.network.RetrofitConfig
import kotlinx.android.synthetic.main.fragment_pictures.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class PicturesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pictures, container, false)
    }

    override fun onResume() {
        super.onResume()
        getPicturesByRetrofit()
    }

    private fun showPictures(pictureList: List<Picture>){
        val recyclerView = pictures_recycler_view
        recyclerView.adapter = PicturesAdapter(this.requireContext(), pictureList)
        recyclerView.layoutManager = GridLayoutManager(this.requireContext(), 4)
    }

    private fun getPicturesByRetrofit(){
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
    }

}
