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


import android.graphics.drawable.Drawable
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
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.wisekiddo.R
import com.wisekiddo.data.Constants
import com.wisekiddo.models.MovieShows
import java.text.DateFormat
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

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
            //notifyItemInserted(itemCount - 1)
            isFooterVisible = true
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
        fun onMovieShowClicked(movieShows: MovieShows, itemView: View)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEMS -> {
                val view = LayoutInflater.from(parent!!.context).inflate(tvShowLayout, parent, false)
                MovieShowsViewHolder(view)
            }
            FOOTER -> {

                var footerLayout = R.layout.progressbar_curves_small

                if (!isVertical)
                    footerLayout = R.layout.progressbar_curves_small



                val view = LayoutInflater.from(parent!!.context).inflate(footerLayout, parent, false)
                ProgressFooterViewHolder(view)

            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(tvShowLayout, parent, false)
                MovieShowsViewHolder(view)
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieShowsViewHolder) {
            holder.bind(showsList[position])
        }
    }


    inner class MovieShowsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvShowContainer: ViewGroup = view.findViewById(R.id.movieshow_container)
        var coverImageView: ImageView = view.findViewById(R.id.iv_cover)
        var tvTitle: TextView = view.findViewById(R.id.tv_title)
        var tvRatingStar: TextView = view.findViewById(R.id.tv_star_rating)
        var tvDescription: TextView? = view.findViewById(R.id.tv_desc)
        var tvReleaseDate: TextView? = view.findViewById(R.id.tv_release_date)
        var progressBar: ViewGroup? = view.findViewById(R.id.progress_bar)

        fun bind(movieShows: MovieShows) {
            Glide.with(coverImageView)
                .load(Constants.BASE_URL_IMAGE + Constants.ImageSize.ORIGINAL + movieShows.posterPath)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar?.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar?.visibility = View.GONE
                        return false
                    }

                })
                .into(coverImageView)



            val simpleDateFormat = SimpleDateFormat("yyyy-mm-dd")
            val date = simpleDateFormat.parse(movieShows.releaseDate)
            val strDate = simpleDateFormat.format(date)

            if(tvReleaseDate!=null)
                tvReleaseDate!!.text = "Release Date: " + strDate
            if(tvDescription!=null)
                tvDescription!!.text = movieShows.overview

            tvTitle.text = movieShows.name

            tvRatingStar.text = movieShows.voteAverage.toString() + itemView.resources.getString(R.string.overall_rating)


            tvShowContainer.setOnClickListener { listener.onMovieShowClicked(movieShows, itemView) }
        }


    }

    inner class ProgressFooterViewHolder(view: View) : RecyclerView.ViewHolder(view){


        fun bind(movieShows: MovieShows){
            //progressBar.visibility = View.GONE
        }
    }
}