package com.azielu.lorempicsumbrowser.ui.images

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.azielu.lorempicsumbrowser.databinding.FragmentDetailBinding
import com.azielu.lorempicsumbrowser.extensions.requireListener
import com.azielu.lorempicsumbrowser.extensions.setUrlImage
import com.azielu.lorempicsumbrowser.model.ImageData


interface DetailView {}

class DetailFragment : Fragment(), DetailView {

    private lateinit var selectedImage: ImageData
    private var _binding: FragmentDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<ImageData>(KEY_IMAGE_DATA).let {
            checkNotNull(it, { "Passed image cannot be null" })
            selectedImage = it
            loadImage(it)
        }
        binding.buttonShare.setOnClickListener { onShareButtonClicked() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireListener<DetailViewListener>().bindView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadImage(image: ImageData) {
        binding.imageView.setUrlImage(this.requireContext(), image)
        binding.textviewAuthor.text = image.author
    }

    private fun onShareButtonClicked() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Check out this photo: ${selectedImage.url}")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    interface DetailViewListener {
        fun bindView(view: DetailView)
    }

    companion object {
        const val KEY_IMAGE_DATA = "KEY_IMAGE_DATA"
    }
}
