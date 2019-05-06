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

package com.wisekiddo.data

object Constants {

    const val API_KEY = "2d9f2b9a7cb8f1aa7ac46888aca20a42"
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/"

    object ApiEndpoint {
        const val POPULAR_TV = "tv/popular"
        const val SIMILAR_TV = "tv/{" + PathParams.TV_SHOW_ID + "}/similar"
    }

    object QueryParams {
        const val API_KEY = "api_key"
        const val LANGUAGE = "language"
        const val PAGE = "page"
    }

    object PathParams {
        const val TV_SHOW_ID = "tv_id"
    }

    object ImageSize {
        const val W500 = "w500"
        const val W780 = "w780"
        const val W1280 = "w1280"
        const val ORIGINAL = "original"
    }

}