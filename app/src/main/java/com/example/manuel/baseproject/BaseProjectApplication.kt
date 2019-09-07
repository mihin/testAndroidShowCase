package com.example.manuel.baseproject

import android.app.Application
import com.example.manuel.baseproject.di.BaseProjectModule
import com.example.manuel.baseproject.di.NetworkModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class BaseProjectApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@BaseProjectApplication)
            modules(
                    listOf(
                            BaseProjectModule.mainModule,
                            NetworkModule.retrofitModule
                    )
            )
        }
    }
}