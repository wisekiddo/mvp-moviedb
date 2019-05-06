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