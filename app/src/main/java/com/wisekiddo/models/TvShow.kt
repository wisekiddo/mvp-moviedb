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


class TvShow : Serializable {
    @SerializedName("original_name")
    var originalName: String? = null
    @SerializedName("genre_ids")
    var genreIds: List<Int>? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("popularity")
    var popularity: Double? = null
    @SerializedName("origin_country")
    var originCountry: List<String>? = null
    @SerializedName("vote_count")
    var voteCount: Int? = null
    @SerializedName("first_air_date")
    var firstAirDate: String? = null
    @SerializedName("backdrop_path")
    var backdropPath: String? = null
    @SerializedName("original_language")
    var originalLanguage: String? = null
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("vote_average")
    var voteAverage: Double? = null
    @SerializedName("overview")
    var overview: String? = null
    @SerializedName("poster_path")
    var posterPath: String? = null
}