package com.muzzlyworld.testapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.muzzlyworld.testapp.databinding.ActivityMainBinding
import com.muzzlyworld.testapp.di.AppContainer
import com.muzzlyworld.testapp.ui.movie.MovieFragment
import com.muzzlyworld.testapp.ui.movies.MoviesFragment

interface Router {
    fun navigateToMovie(id: Int)
    fun back()
}

class MainActivity : AppCompatActivity(), Router {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(supportFragmentManager.findFragmentById(binding.container.id) == null) {
            val fragment = MoviesFragment()
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, fragment, MoviesFragment::class.simpleName)
                .commit()
        }
    }

    override fun navigateToMovie(id: Int) {
        val fragment = MovieFragment.newInstance(id)
        supportFragmentManager.beginTransaction()
            .add(binding.container.id, fragment, MovieFragment::class.simpleName)
            .addToBackStack("TAG")
            .commit()
    }

    override fun back() {
        supportFragmentManager.popBackStack()
    }
}