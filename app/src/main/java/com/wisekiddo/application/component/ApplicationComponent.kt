package com.wisekiddo.application.component

import com.wisekiddo.application.module.NetworkModule
import com.wisekiddo.application.module.PresentersModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, PresentersModule::class])
interface ApplicationComponent {

    //fun inject(activity: PopularTvShowsActivity)
    //fun inject(activity: ShowDetailsActivity)
}