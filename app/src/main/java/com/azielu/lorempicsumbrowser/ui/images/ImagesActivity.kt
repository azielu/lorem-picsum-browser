package com.azielu.lorempicsumbrowser.ui.images

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.azielu.lorempicsumbrowser.R
import com.azielu.lorempicsumbrowser.databinding.ActivityMainBinding
import com.azielu.lorempicsumbrowser.model.ImageData
import com.azielu.lorempicsumbrowser.model.TempGlobalImages

class ImagesActivity : AppCompatActivity(), ImagesContract.View,
    ImageListFragment.ListViewListener,
    DetailFragment.DetailViewListener {

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

    override fun fetchImages() {
        loadImages(TempGlobalImages)
    }

    override fun bindView(view: DetailView) {
        detailView = view
    }

    override fun fetchItem(id: Int) {
        detailView?.loadImage(TempGlobalImages.first { it.id == id })
    }

    override fun onItemClicked(item: ImageData) {
        val bundle = bundleOf("id" to item.id)
        findNavController(R.id.nav_host_fragment_content_main)
            .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }
}

