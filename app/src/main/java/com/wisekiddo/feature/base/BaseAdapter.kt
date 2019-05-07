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

package com.wisekiddo.feature.base


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.wisekiddo.R
import com.wisekiddo.data.Constants
import com.wisekiddo.models.MovieShows

class BaseAdapter(private var listener: Listener, @LayoutRes private val tvShowLayout: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEMS = 1
    private val FOOTER = 2

    private var isFooterVisible = false
    private var showsList = ArrayList<MovieShows>()
    private var isVertical = true

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        isVertical = ((recyclerView.layoutManager as? LinearLayoutManager)?.orientation != OrientationHelper.HORIZONTAL)
    }

    fun removeFooter() {
        if (isFooterVisible) {
            isFooterVisible = false
            notifyItemRemoved(itemCount)
        }
    }

    fun addItems(tvShowsList: List<MovieShows>) {
        val position = this.showsList.size
        this.showsList.addAll(tvShowsList)
        notifyItemRangeInserted(position, tvShowsList.size)
    }

    fun showFooter() {
        if (!isFooterVisible) {
            isFooterVisible = true
            notifyItemInserted(itemCount - 1)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (showsList.size > position) {
            ITEMS
        } else {
            FOOTER
        }
    }


    override fun getItemCount(): Int {
        var itemCount = showsList.size
        if (isFooterVisible)
            itemCount++
        return itemCount
    }


    interface Listener {
        fun onTvShowClicked(movieShows: MovieShows, itemView: View)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEMS -> {
                val view = LayoutInflater.from(parent!!.context).inflate(tvShowLayout, parent, false)
                TvShowsViewHolder(view)
            }
            FOOTER -> {
                val footerLayout = R.layout.progressbar_curves


                val view = LayoutInflater.from(parent!!.context).inflate(footerLayout, parent, false)
                ProgressFooterViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(tvShowLayout, parent, false)
                TvShowsViewHolder(view)
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TvShowsViewHolder) {
            holder.bind(showsList[position])
        }
    }


    inner class TvShowsViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        var tvShowContainer: ViewGroup = view.findViewById(R.id.tvshow_container)
        var coverImageView: ImageView = view.findViewById(R.id.iv_cover)
        var titleTv: TextView = view.findViewById(R.id.tv_title)
        var starRatingTv: TextView = view.findViewById(R.id.tv_star_rating)
        var descTv: TextView = view.findViewById(R.id.tv_desc)

        fun bind(movieShows: MovieShows) {
            Glide.with(coverImageView)
                .load(Constants.BASE_URL_IMAGE + Constants.ImageSize.W500 + movieShows.posterPath)
                .into(coverImageView)

            titleTv.text = movieShows.name
            descTv?.text = movieShows.overview
            starRatingTv.text = movieShows.voteAverage.toString()
            //yearTv?.text = movieShows.firstAirDate?.split("-")?.get(0)

            tvShowContainer.setOnClickListener { listener.onTvShowClicked(movieShows, itemView) }
        }


    }

    inner class ProgressFooterViewHolder(view: View) : RecyclerView.ViewHolder(view)
}