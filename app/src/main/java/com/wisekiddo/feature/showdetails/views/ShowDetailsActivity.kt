/*
 * Copyright 2019 Wisekiddo by Ronald Garcia Bernardo. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wisekiddo.feature.showdetails.views

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import com.wisekiddo.R
import com.wisekiddo.application.ProjectApplication
import com.wisekiddo.data.Constants
import com.wisekiddo.feature.base.BaseAdapter
import com.wisekiddo.feature.showdetails.presenter.ShowDetailsPresenter
import com.wisekiddo.models.MovieShows
import com.wisekiddo.widgets.loaders.CurvesLoader
import java.util.*
import javax.inject.Inject

class ShowDetailsActivity : MvpActivity<ShowDetailsPresenter.View, ShowDetailsPresenter>(),
    ShowDetailsPresenter.View,
    BaseAdapter.Listener {

    @Inject
    lateinit var showDetailsPresenter: ShowDetailsPresenter

    lateinit var adapter: BaseAdapter
    lateinit var clickedItemView: View
    lateinit var ivBackdrop: ImageView
    lateinit var ivHeaderCover: ImageView
    lateinit var tvTitle: TextView
    lateinit var tvDescription: TextView
    lateinit var tvStarRating: TextView
    lateinit var tvYear: TextView
    lateinit var tvLanguage: TextView
    lateinit var similarTvShowsRecyclerView: RecyclerView
    lateinit var progressIndicator: CurvesLoader
    lateinit var toolbar: Toolbar

    companion object {
        const val SHOW_KEY = "movie_show_key"
        fun getMyIntent(context: Context, movieShows: MovieShows) =
            Intent(context, ShowDetailsActivity::class.java).apply {
                putExtra(SHOW_KEY, movieShows)
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_view_activity)


        val movieShows = intent.extras.getSerializable(SHOW_KEY) as MovieShows
        initViews(movieShows)
        getPresenter().init(movieShows)
    }

    private fun initViews(movieShows: MovieShows) {

        progressIndicator = findViewById(R.id.progress_bar)
        toolbar = findViewById(R.id.toolbar)
        similarTvShowsRecyclerView = findViewById(R.id.rv_similar)
        tvYear = findViewById(R.id.tv_year)
        tvLanguage = findViewById(R.id.tv_language)
        tvStarRating = findViewById(R.id.tv_star_rating)
        tvDescription = findViewById(R.id.tv_desc)
        tvTitle = findViewById(R.id.tv_title)
        ivHeaderCover = findViewById(R.id.iv_cover)
        ivBackdrop = findViewById(R.id.iv_backdrop)


        Glide.with(this)
            .load(Constants.BASE_URL_IMAGE + Constants.ImageSize.ORIGINAL + movieShows.backdropPath)
            .into(ivBackdrop)

        Glide.with(this)
            .load(Constants.BASE_URL_IMAGE + Constants.ImageSize.W500 + movieShows.posterPath)
            .into(ivHeaderCover)

        tvTitle.text = movieShows.name
        tvDescription.text = movieShows.overview
        tvStarRating.text = movieShows.voteAverage.toString()
        tvYear.text = movieShows.releaseDate.toString()
        tvLanguage.text = Locale(movieShows.originalLanguage).displayLanguage

        initList()

        toolbar.setNavigationOnClickListener { onBackPressed() }
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        //}
    }

    private fun initList() {
        adapter = BaseAdapter(this, R.layout.detail_similar_item_layout)
        similarTvShowsRecyclerView.adapter = adapter
        similarTvShowsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                onScrolled()
            }
        })
    }

    private fun onScrolled() {
        val lastItem = (similarTvShowsRecyclerView.layoutManager as LinearLayoutManager).itemCount
        val currentlyVisibleItem =
            (similarTvShowsRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        if (lastItem - currentlyVisibleItem < 5) {
            getPresenter().getMoreSimilarShows()
        }
    }

    override fun onMovieShowClicked(movieShows: MovieShows, itemView: View) {
        this.clickedItemView = itemView
        getPresenter().onMovieShowClicked(movieShows)
    }

    override fun addSimilarMovieShows(similarMovieShows: List<MovieShows>) {
        adapter.addItems(similarMovieShows)
    }

    override fun showProgress() {
        progressIndicator.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressIndicator.visibility = View.GONE
    }

    override fun showFooterLoader() {
        adapter.showFooter()
    }

    override fun hideFooterLoader() {
        adapter.removeFooter()
    }

    override fun openMovieShowDetailScreen(movieShows: MovieShows) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this)

        val intent = getMyIntent(this, movieShows)
        ActivityCompat.startActivity(this, intent, options.toBundle())
        overridePendingTransition(0, 0)
    }

    override fun showError(errorMessage: String) {
        Snackbar.make(findViewById(R.id.parent_container), errorMessage, Snackbar.LENGTH_SHORT).show()
    }

    override fun createPresenter(): ShowDetailsPresenter {
        ProjectApplication.INSTANCE.appComponent.inject(this)
        return showDetailsPresenter
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }
}
