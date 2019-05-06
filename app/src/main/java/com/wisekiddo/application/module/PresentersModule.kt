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

package com.wisekiddo.application.module


import com.wisekiddo.data.remote.RemoteService
import com.wisekiddo.feature.popularshows.presenter.PopularShowsPresenter
import com.wisekiddo.feature.showdetails.presenter.ShowDetailsPresenter
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class])
class PresentersModule {
    @Provides
    fun getHomePresenter(remoteService: RemoteService) = PopularShowsPresenter(remoteService)

    @Provides
    fun getShowDetailPresenter(remoteService: RemoteService) = ShowDetailsPresenter(remoteService)
}