package com.example.magicthegathering.Network.Modules

import com.example.magicthegathering.Utils.IRxSchedulers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

private const val MODULE_NAME = "App Module"

@Module
@InstallIn(ApplicationComponent::class)
object AppModule{
    @Provides
    @Singleton
    fun getIRxSchedulers(): IRxSchedulers = object : IRxSchedulers {
        override fun main(): Scheduler = AndroidSchedulers.mainThread()
        override fun io(): Scheduler = Schedulers.io()
    }
}