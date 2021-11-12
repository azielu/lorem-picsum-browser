package com.azielu.lorempicsumbrowser.ui.images

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.azielu.lorempicsumbrowser.databinding.FragmentDetailBinding
import com.azielu.lorempicsumbrowser.extensions.requireListener
import com.azielu.lorempicsumbrowser.extensions.setUrlImage
import com.azielu.lorempicsumbrowser.model.ImageData

class DetailFragment : Fragment(), DetailView {

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

        arguments?.getInt("id")?.let {
            requireListener<DetailViewListener>().fetchItem(it)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireListener<DetailViewListener>().bindView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun loadImage(image: ImageData) {
        binding.imageView.setUrlImage(this.requireContext(), image)
        binding.textviewAuthor.text = image.author
    }

    interface DetailViewListener {
        fun bindView(view: DetailView)
        fun fetchItem(id: Int)
    }
}

interface DetailView {
    fun loadImage(image: ImageData)
}
