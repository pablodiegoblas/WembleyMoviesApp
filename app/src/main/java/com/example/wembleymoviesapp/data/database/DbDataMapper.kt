package com.example.wembleymoviesapp.data.database

import com.example.wembleymoviesapp.domain.MovieDetail
import com.example.wembleymoviesapp.domain.MovieItem

class DbDataMapper {

    fun convertListToDomainMovieItem(listFav: MutableList<MovieDB>) =
        listFav.mapIndexed { _, movieDB ->
            convertToDomainMovieItem(movieDB)
        }

    private fun convertToDomainMovieItem(movieDB: MovieDB) = with(movieDB) {
        MovieItem(id, title, poster, favourite)
    }

    fun convertToDomainMovieDetail(movieDB: MovieDB) = with(movieDB) {
        MovieDetail(title, backdrop, overview, voteAverage)
    }
}