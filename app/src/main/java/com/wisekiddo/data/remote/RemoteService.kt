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

package com.wisekiddo.data.remote

import com.wisekiddo.data.Constants
import com.wisekiddo.models.MovieShows
import com.wisekiddo.models.PaginatedResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteService {

    @GET(Constants.ApiEndpoint.NOW_PLAYING)
    fun getMovieShows(@Query(Constants.QueryParams.PAGE) page: Int = 1,
                      @Query(Constants.QueryParams.API_KEY)
                          apiKey: String = Constants.API_KEY
    ): Flowable<PaginatedResponse<MovieShows>>

    @GET(Constants.ApiEndpoint.SIMILAR_MOVIE)
    fun getSimilarMovieShows(
        @Path(Constants.PathParams.MOVIE_ID) tvShowId: Int,
        @Query(Constants.QueryParams.PAGE) page: Int = 1,
        @Query(Constants.QueryParams.API_KEY)
        apiKey: String = Constants.API_KEY
    ): Flowable<PaginatedResponse<MovieShows>>
}