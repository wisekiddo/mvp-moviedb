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

package com.wisekiddo.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class MovieShows : Serializable {

    @SerializedName("original_title")
    val originalTitle: String? = null
    @SerializedName("title")
    val name: String? = null
    @SerializedName("genre_ids")
    var genreIds: List<Int>? = null
    @SerializedName("adult")
    val adult: Boolean? = null
    @SerializedName("popularity")
    var popularity: Double? = null
    @SerializedName("original_language")
    var originalLanguage: String? = null
    @SerializedName("vote_count")
    var voteCount: Int? = null
    @SerializedName("first_air_date")
    var firstAirDate: String? = null
    @SerializedName("backdrop_path")
    var backdropPath: String? = null
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("vote_average")
    var voteAverage: Double? = null
    @SerializedName("overview")
    var overview: String? = null
    @SerializedName("poster_path")
    var posterPath: String? = null
    @SerializedName("release_date")
    val releaseDate: String? = null
    @SerializedName("video")
    val video: Boolean? = null

}


data class Result(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)