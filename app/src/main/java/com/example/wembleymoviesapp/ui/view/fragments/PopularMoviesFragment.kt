package com.example.wembleymoviesapp.ui.view.fragments

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wembleymoviesapp.databinding.FragmentPopularMoviesBinding
import com.example.wembleymoviesapp.domain.MovieItem
import com.example.wembleymoviesapp.ui.controllers.PopularController
import com.example.wembleymoviesapp.ui.view.activities.DetailMovieActivity
import com.example.wembleymoviesapp.ui.view.activities.MainActivity
import com.example.wembleymoviesapp.ui.view.adapters.PopularMoviesAdapter

class PopularMoviesFragment : Fragment() {

    private var binding: FragmentPopularMoviesBinding? = null

    lateinit var controller: PopularController

    private lateinit var popularMoviesAdapter: PopularMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = PopularController(this)
        controller.createDB()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentPopularMoviesBinding.inflate(layoutInflater, container, false).also {
        binding = it

        //Crear adaptador
        createAdapter()
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Loading the popular movies from the API
        controller.getPopularMovies()
    }

    // Implementado para la salida y reactivacion del Fragment
    override fun onResume() {
        super.onResume()
        controller.createDB()
    }

    override fun onPause() {
        super.onPause()
        controller.destroyDB()
    }

    // Important destroy the binding here, because the lifecycle of the fragments is different
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun showNotMoviesText() {
        binding?.recyclerViewPopularMovies?.visibility = View.GONE
        binding?.tvPopularDefault?.visibility = View.VISIBLE
    }

    private fun createAdapter() {

        //Put visibility DefaultText Gone
        binding?.tvPopularDefault?.visibility = View.GONE
        binding?.recyclerViewPopularMovies?.visibility = View.VISIBLE

        //Charge the adapter
        popularMoviesAdapter = PopularMoviesAdapter(
            {
                val intent: Intent = Intent(context, DetailMovieActivity::class.java).apply {
                    putExtra("ID", it.id)
                }
                startActivity(intent)
            },
            {
                controller.pressFavButton(it)
            },
            {
                sharedInfo("¿Te apetece venir a ver conmigo la pelicula ${it.title}?")
            }
        )

        binding?.recyclerViewPopularMovies?.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = popularMoviesAdapter
        }

    }

    fun updatePopularMoviesAdapter(items: List<MovieItem>) {
        popularMoviesAdapter.submitList(items)
    }

    private fun sharedInfo(text: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    fun showErrorAPI() {
        Toast.makeText(this.requireContext(), "Connection failure", Toast.LENGTH_SHORT).show()
    }


}