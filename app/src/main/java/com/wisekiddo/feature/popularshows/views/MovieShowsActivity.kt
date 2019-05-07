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

package com.wisekiddo.feature.popularshows.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import com.wisekiddo.R
import com.wisekiddo.application.ProjectApplication
import com.wisekiddo.feature.base.BaseAdapter
import com.wisekiddo.feature.popularshows.presenter.MovieShowsPresenter
import com.wisekiddo.feature.showdetails.views.ShowDetailsActivity
import com.wisekiddo.models.MovieShows
import com.wisekiddo.widgets.loaders.CurvesLoader
import javax.inject.Inject


class MovieShowsActivity : MvpActivity<MovieShowsPresenter.View, MovieShowsPresenter>(),
    MovieShowsPresenter.View,
    BaseAdapter.Listener {

    lateinit var progressIndicator: CurvesLoader
    lateinit var recyclerView: RecyclerView

    private lateinit var adapter: BaseAdapter
    private lateinit var clickedItemView: View
    private lateinit var toolbar: Toolbar

    @Inject
    lateinit var movieShowsPresenter: MovieShowsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.popular_shows_activity)

        initViews()
        getPresenter().init()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.rv_shows_list)
        progressIndicator = findViewById(R.id.progress_bar)
        toolbar = findViewById(R.id.toolbar)

        adapter = BaseAdapter(this, R.layout.popular_show_list_item)
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                onScrolled()
            }

        })

    }


    private fun onScrolled() {
        val lastItem = (recyclerView.layoutManager as LinearLayoutManager).itemCount
        val currentlyVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        if (lastItem - currentlyVisibleItem < 5) {
            getPresenter().getMoreMovieShows()
        }
    }

    override fun createPresenter(): MovieShowsPresenter {
        ProjectApplication.INSTANCE.appComponent.inject(this)
        return movieShowsPresenter
    }

    override fun onMovieShowClicked(movieShows: MovieShows, itemView: View) {
        this.clickedItemView = itemView
        getPresenter().onMovieShowClicked(movieShows)
    }

    override fun showFooterLoader() {
        adapter.showFooter()
    }

    override fun hideFooterLoader() {
        adapter.removeFooter()
    }

    override fun showFullscreenProgress() {
        progressIndicator.visibility = View.VISIBLE
    }

    override fun hideFullscreenProgress() {
        progressIndicator.visibility = View.GONE
    }

    override fun addItems(movieShows: List<MovieShows>) {
        adapter.addItems(movieShows)
    }

    override fun showError(errorMessage: String) {
        //Snackbar.make(findViewById(R.id.parent_container), errorMessage, Snackbar.LENGTH_SHORT).show()
    }

    override fun openMovieShowDetailScreen(movieShows: MovieShows) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this)
        startActivity(intent, options.toBundle())

        val intent = ShowDetailsActivity.getMyIntent(this, movieShows)
        ActivityCompat.startActivity(this, intent, options.toBundle())
        overridePendingTransition(0, 0)
    }
}
