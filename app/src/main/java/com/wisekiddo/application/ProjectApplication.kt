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

package com.wisekiddo.application

import android.app.Application

import com.wisekiddo.application.component.ApplicationComponent
import com.wisekiddo.application.component.DaggerApplicationComponent
import com.wisekiddo.application.module.NetworkModule
import com.wisekiddo.application.module.PresentersModule


class ProjectApplication : Application() {

    companion object {
        lateinit var INSTANCE: ProjectApplication
            private set
    }

    lateinit var appComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerApplicationComponent.builder()
            .networkModule(NetworkModule(this))
            .presentersModule(PresentersModule())
            .build()
    }

}