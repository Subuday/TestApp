package com.muzzlyworld.testapp.ui.movies

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muzzlyworld.testapp.App
import com.muzzlyworld.testapp.R
import com.muzzlyworld.testapp.Router
import com.muzzlyworld.testapp.databinding.FragmentMoviesBinding
import com.muzzlyworld.testapp.utils.LoadingAdapter

class MoviesFragment : Fragment() {

    private val moviesViewModel by viewModels<MoviesViewModel> {
        MoviesViewModelFactory(
            (requireActivity().application as App).appContainer.trendingMoviePaginator,
            (requireActivity().application as App).appContainer.searchedMoviePaginator
        )
    }

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private var _movieAdapter: MovieAdapter? = null
    private val movieAdapter: MovieAdapter get() = _movieAdapter!!

    private var _loadingAdapter: LoadingAdapter? = null
    private val loadingAdapter: LoadingAdapter get() = _loadingAdapter!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _movieAdapter = MovieAdapter { (requireActivity() as Router).navigateToMovie(it) }
        _loadingAdapter = LoadingAdapter()
        binding.movies.adapter = ConcatAdapter(movieAdapter, loadingAdapter)
        binding.movies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val lastVisibleItemPosition =
                    (binding.movies.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                moviesViewModel.loadNextMovies(lastVisibleItemPosition)
            }
        })

        binding.searchIcon.setOnClickListener { moviesViewModel.onSearchIconClick() }

        binding.searchInput.addTextChangedListener(onTextChanged = { s, _, _, _ ->
            moviesViewModel.searchMovie(s.toString())
        })

        moviesViewModel.effect.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { clearSearchInput() }
        }
        moviesViewModel.state.observe(viewLifecycleOwner) { render(it) }
    }

    override fun onDestroyView() {
        _movieAdapter = null
        _loadingAdapter = null
        binding.movies.adapter = null
        _binding = null
        super.onDestroyView()
    }

    private fun render(state: MoviesViewState) {
        if (state.isSearching) binding.searchIcon.setIconResource(R.drawable.ic_back_arrow)
        else binding.searchIcon.setIconResource(R.drawable.ic_search)
        binding.error.visibility = if(state.showError) View.VISIBLE else View.GONE
        if (state.isSearching) movieAdapter.submitList(state.searchedMovies) else movieAdapter.submitList(state.trendingMovies)
        loadingAdapter.isLoading = state.showLoading
    }

    private fun clearSearchInput() {
        requireActivity().currentFocus?.let {
            (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(it.windowToken, 0)
        }
        binding.searchInput.text.clear()
        binding.searchInput.clearFocus()
    }
}
