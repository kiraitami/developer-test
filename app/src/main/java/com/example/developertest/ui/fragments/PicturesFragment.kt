package com.example.developertest.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager

import com.example.developertest.R
import com.example.developertest.ui.activities.FullScreenImageActivity
import com.example.developertest.ui.adapters.PicturesAdapter
import com.example.developertest.database.db.AppDatabase
import com.example.developertest.database.entities.PictureEntity
import com.example.developertest.models.Picture
import com.example.developertest.network.RetrofitConfig
import com.example.developertest.network.isOnline
import com.example.developertest.strings.PICTURE_URL
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_pictures.*

/**
 * A simple [Fragment] subclass.
 */
class PicturesFragment : Fragment(), PicturesAdapter.OnPictureListener {

    var disposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pictures, container, false)
    }

    override fun onResume() {
        super.onResume()
        if (isOnline(requireContext()))
            getPicturesByRetrofit()
        else
            getPicturesFromDatabase()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    private fun showPictures(pictureList: List<Picture>){
        val recyclerView = pictures_recycler_view
        recyclerView.adapter =
            PicturesAdapter(
                this.requireContext(),
                pictureList,
                this
            )
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
    }

    private fun getPicturesFromDatabase() {
        disposable = AppDatabase.getInstance(requireContext()).pictureDao().getPicturesFromDb()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result-> showPictures(result.convertToPictureList()) },
                { error -> Toast.makeText(context, error.message, Toast.LENGTH_LONG).show() }
            )
    }

    private fun insertPictureInDatabase(picture: Picture) {
        Completable.fromRunnable {
            AppDatabase.getInstance(requireContext()).pictureDao()
                .insertPicture(picture.convertToPictureEntity())
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    }

    override fun onPictureClick(picture: Picture) {
        insertPictureInDatabase(picture)

        val intent = Intent(requireContext(), FullScreenImageActivity::class.java)
        intent.putExtra(PICTURE_URL, picture.url)
        requireContext().startActivity(intent)
    }

    private fun List<PictureEntity>.convertToPictureList() : List<Picture> {
        return this.map { entity -> entity.convertToPicture() }
    }

}
