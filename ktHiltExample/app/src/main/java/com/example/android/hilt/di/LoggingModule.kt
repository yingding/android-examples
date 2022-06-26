package com.example.android.hilt.di

import com.example.android.hilt.data.LoggerDataSource
import com.example.android.hilt.data.LoggerInMemoryDataSource
import com.example.android.hilt.data.LoggerLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

/* since the different implementations of LoggerDataSource are scoped to different containers,
 * we cannot use the same module: LoggerInMemoryDataSource is scoped to the `Activity` container
 * and LoggerLocalDataSource to the `Application` container
 */

@Qualifier
annotation class InMemoryLogger
@Qualifier
annotation class DatabaseLogger

/**
 * need abstract method in abstract class to defined interface binding with @Binds annotation
 * for dynamic binding
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class LoggingDatabaseModule {

    /**
     * use the @Singleton to remember the instance in container
     */
    @DatabaseLogger
    @Singleton
    @Binds
    abstract fun bindDatabaseLogger(impl: LoggerLocalDataSource): LoggerDataSource
}

@InstallIn(ActivityComponent::class)
@Module
abstract class LoggingInMemoryModule {

    /**
     * use the @ActivityScoped to indicate the scope of this binding.
     * @Binds methods must have the scoping annotations if the type is scoped, so that's why this
     * function is annotated with @ActivityScoped.
     */
    @InMemoryLogger
    @ActivityScoped
    @Binds
    abstract fun bindInMemoryLogger(impl: LoggerInMemoryDataSource): LoggerDataSource
}