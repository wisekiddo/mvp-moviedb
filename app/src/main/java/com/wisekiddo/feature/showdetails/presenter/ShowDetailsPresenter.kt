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

package com.wisekiddo.feature.showdetails.presenter

import android.util.Log
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.wisekiddo.data.remote.RemoteService
import com.wisekiddo.models.MovieShows
import com.wisekiddo.models.PaginatedResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ShowDetailsPresenter(private var remoteService: RemoteService) : MvpBasePresenter<ShowDetailsPresenter.View>() {

    private var tvShowId: Int = 0
    private var currentPage = 0
    private var pageAvailable = 1
    private var isLoading = false
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun init(movieShows: MovieShows) {
        tvShowId = movieShows.id!!
        ifViewAttached { it.showProgress() }
        getSimilarTvShows()
    }

    fun getMoreSimilarShows() {
        ifViewAttached { it.showFooterLoader() }
        getSimilarTvShows()
    }

    private fun getSimilarTvShows() {
        if (currentPage < pageAvailable && !isLoading) {
            isLoading = true

            val disposable = remoteService.getSimilarTvShows(tvShowId, currentPage + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> onGetSimilarTvShowsSuccess(response) },
                    { e -> onGetSimilarTvShowsFailure(e) })

            compositeDisposable.add(disposable)
        }
    }

    private fun onGetSimilarTvShowsSuccess(response: PaginatedResponse<MovieShows>?) {
        currentPage = response!!.page
        pageAvailable = response.totalPages

        isLoading = false
        ifViewAttached {
            it.hideProgress()
            it.hideFooterLoader()
            it.addSimilarTvShows(response.results)
        }
    }

    private fun onGetSimilarTvShowsFailure(e: Throwable?) {
        isLoading = false
        ifViewAttached {
            it.hideProgress()
            it.hideFooterLoader()
            it.showError(e!!.message!!)
            Log.e("Error", e.toString())
        }
    }

    fun onTvShowClicked(movieShows: MovieShows) {
        ifViewAttached { it.openTvShowDetailScreen(movieShows) }
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.clear()
    }

    override fun destroy() {
        super.destroy()
        compositeDisposable.dispose()
    }

    interface View : MvpView {
        fun showProgress()
        fun hideProgress()
        fun showFooterLoader()
        fun hideFooterLoader()
        fun addSimilarTvShows(similarMovieShows: List<MovieShows>)
        fun openTvShowDetailScreen(movieShows: MovieShows)
        fun showError(errorMessage: String)
    }
}