package com.azielu.lorempicsumbrowser.ui.images

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.azielu.lorempicsumbrowser.R
import com.azielu.lorempicsumbrowser.databinding.ActivityMainBinding
import com.azielu.lorempicsumbrowser.model.ImageData
import com.azielu.lorempicsumbrowser.mvp.BasePresenterActivity
import com.azielu.lorempicsumbrowser.repository.ImagesRepositoryImpl
import com.azielu.lorempicsumbrowser.usecase.FetchPhotosUseCase
import com.azielu.lorempicsumbrowser.util.SharedPreferancesCache
import io.reactivex.disposables.CompositeDisposable

class ImagesActivity : BasePresenterActivity<ImagesContract.View, ImagesContract.Presenter>(),
    ImagesContract.View,
    ImageListFragment.ListViewListener,
    DetailFragment.DetailViewListener {

    override val mvpView: ImagesContract.View = this

    //TODO use dependency injection like Koin
    override val presenter: ImagesContract.Presenter by lazy {
        ImagesPresenter(
            CompositeDisposable(),
            FetchPhotosUseCase(ImagesRepositoryImpl(SharedPreferancesCache(applicationContext)))
        )}

    private var imageListView: ImageListView? = null
    private var detailView: DetailView? = null

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun loadImages(images: List<ImageData>) {
        imageListView?.loadImages(images)
    }

    override fun bindView(view: ImageListView) {
        imageListView = view
    }

    override fun bindView(view: DetailView) {
        detailView = view
    }

    override fun fetchImages() {
        presenter.fetchMoreImages()
    }

    override fun onImageListViewReady() {
        presenter.fetchFirstImages()
    }

    override fun onItemClicked(item: ImageData) {
        val bundle = bundleOf(DetailFragment.KEY_IMAGE_DATA to item)
        findNavController(R.id.nav_host_fragment_content_main)
            .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }
}

