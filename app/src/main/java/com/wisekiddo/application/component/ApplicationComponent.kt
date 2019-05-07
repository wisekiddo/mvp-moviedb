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

package com.wisekiddo.application.component

import com.wisekiddo.application.module.NetworkModule
import com.wisekiddo.application.module.PresentersModule
import com.wisekiddo.feature.popularshows.views.MovieShowsActivity
import com.wisekiddo.feature.showdetails.views.ShowDetailsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, PresentersModule::class])
interface ApplicationComponent {

    fun inject(activity: MovieShowsActivity)
    fun inject(activity: ShowDetailsActivity)
}