package com.example.android.hilt.di

import com.example.android.hilt.navigator.AppNavigator
import com.example.android.hilt.navigator.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 *
 */
@InstallIn(ActivityComponent::class)
@Module
abstract class NavigationModule {

    /**
     * @Binds must annotate an abstract function
     * This binds an interface with and implementation
     * @param impl: AppNavigatorImpl is the implementation
     * @return AppNavigator is the interface
     *
     * this bindNavigator is not scoped, every fragment will create a new AppNavigator instance,
     * it would be better to scope to Activity
     */
    @Binds
    abstract fun bindNavigator(impl: AppNavigatorImpl): AppNavigator
}