package com.azielu.lorempicsumbrowser.ui.images

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.azielu.lorempicsumbrowser.R
import com.azielu.lorempicsumbrowser.databinding.FragmentListBinding
import com.azielu.lorempicsumbrowser.extensions.requireListener
import com.azielu.lorempicsumbrowser.extensions.setUrlImage
import com.azielu.lorempicsumbrowser.model.ImageData
import com.jakewharton.rxbinding4.recyclerview.scrollStateChanges
import io.reactivex.rxjava3.disposables.Disposable

interface ImageListView {
    fun loadImages(images: List<ImageData>)
    fun showError(error : Throwable)
}

class ImageListFragment : Fragment(), ImageListView {

    private lateinit var recyclerViewDisposable: Disposable
    private lateinit var imageAdapter: ImagesAdapter
    private var _binding: FragmentListBinding? = null
    private var isLoadingNewPhotos: Boolean = false

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireListener<ListViewListener>().bindView(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val circularProgressDrawable = CircularProgressDrawable(requireContext())
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 100f
        circularProgressDrawable.start()

        binding.progressBar.setImageDrawable(circularProgressDrawable)
        binding.progressBar.isVisible = true

        imageAdapter = ImagesAdapter(
            images = mutableListOf(),
            listener = { item: ImageData ->
                requireListener<ListViewListener>().onItemClicked(item)
            })

        binding.recyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            val gridSize = resources.getInteger(R.integer.grid_size);
            layoutManager =
                object : GridLayoutManager(context, gridSize) {
                    //This code will load more pages if there is still empty space on the screen
                    // after loading first page

                    override fun onLayoutCompleted(state: RecyclerView.State?) {
                        super.onLayoutCompleted(state)

                        fetchNewImagesIfNeeded()
                    }
                }
            adapter = imageAdapter
            recyclerViewDisposable = scrollStateChanges()
                .map { true }
                .subscribe {
                    fetchNewImagesIfNeeded()
                }
        }

        requireListener<ListViewListener>().onImageListViewReady()
    }

    private fun fetchNewImagesIfNeeded() {
        if (isLastItemVisible() && !isLoadingNewPhotos) {
            isLoadingNewPhotos = true;
            requireListener<ListViewListener>().fetchImages()
        }
    }

    private fun isLastItemVisible(): Boolean {
        val layoutManager = binding.recyclerView.layoutManager as GridLayoutManager
        return layoutManager.findLastCompletelyVisibleItemPosition() == imageAdapter.itemCount - 1
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        recyclerViewDisposable.dispose()
    }

    override fun loadImages(images: List<ImageData>) {
        isLoadingNewPhotos = false
        imageAdapter.addItems(images)
        binding.progressBar.isVisible = false
        binding.recyclerView.isVisible = true
    }

    override fun showError(error: Throwable) {
        isLoadingNewPhotos = false
        binding.progressBar.setImageResource(R.drawable.ic_loading_error)
        binding.progressBar.setOnClickListener{fetchNewImagesIfNeeded()}

        Toast.makeText(requireContext(),"Error occurred while downloading images",Toast.LENGTH_SHORT ).show()
    }

    private class ImagesAdapter(
        var images: MutableList<ImageData>,
        val listener: (ImageData) -> Unit
    ) : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image, parent, false)
        )

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val image = images[position]
            holder.imageView.setUrlImage(holder.imageView.context, image)
            holder.imageView.setOnClickListener { listener(image) }
        }

        override fun getItemCount() = images.size

        fun addItems(newImages: List<ImageData>) {
            val lastOldItemIndex = images.count() - 1
            images.addAll(newImages)
            notifyItemRangeInserted(lastOldItemIndex + 1, newImages.count())
        }
    }

    private class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageview)
    }

    interface ListViewListener {
        fun bindView(view: ImageListView)
        fun fetchImages()
        fun onImageListViewReady()
        fun onItemClicked(item: ImageData)
    }
}


