package com.muzzlyworld.testapp.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.muzzlyworld.testapp.App
import com.muzzlyworld.testapp.R
import com.muzzlyworld.testapp.Router
import com.muzzlyworld.testapp.databinding.FragmentMovieBinding
import com.muzzlyworld.testapp.utils.loadImage

private const val MOVIE_ID_ARG = "movie_id_arg"

class MovieFragment : Fragment() {

    private lateinit var movieViewModel: MovieViewModel

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private var _actorAdapter: ActorAdapter? = null
    private val actorAdapter get() = _actorAdapter!!

    private var _directorAdapter: DirectorAdapter? = null
    private val directorAdapter get() = _directorAdapter!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!::movieViewModel.isInitialized) {
            val movieId = arguments?.getInt(MOVIE_ID_ARG) ?: throw IllegalStateException()
            val appContainer = (requireActivity().application as App).appContainer
            movieViewModel = MovieViewModel(movieId, appContainer.movieRepository)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _actorAdapter = ActorAdapter()
        binding.cast.adapter = actorAdapter
        _directorAdapter = DirectorAdapter()
        binding.directors.adapter = directorAdapter
        binding.back.setOnClickListener { (requireActivity() as Router).back() }
        movieViewModel.state.observe(viewLifecycleOwner) { render(it) }
    }

    override fun onDestroyView() {
        _directorAdapter = null
        binding.directors.adapter = null
        _actorAdapter = null
        binding.cast.adapter = null
        _binding = null
        super.onDestroyView()
    }

    private fun render(state: MovieViewState) {
        binding.poster.loadImage(state.poster)
        binding.name.text = state.name
        binding.description.text = state.description
        binding.releaseDate.text = state.releaseDate
        binding.genres.text = state.genres
        actorAdapter.submitList(state.cast)
        directorAdapter.submitList(state.directors)
    }

    companion object {
        @JvmStatic
        fun newInstance(movieId: Int): MovieFragment = MovieFragment().apply {
            arguments = bundleOf(MOVIE_ID_ARG to movieId)
        }
    }

}