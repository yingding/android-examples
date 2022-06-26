package com.example.android.hilt.di

import android.content.Context
import androidx.room.Room
import com.example.android.hilt.data.AppDatabase
import com.example.android.hilt.data.LogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Since the LoggerLocalDataSource is scoped to the application container, the LogDao
 * binding needs to be available in the application container.
 *
 * @InstallIn annotation passing in the class of the Hilt component associated with this module
 * In this case it is the application container, SingletonComponent::class
 *
 * For @InstallIn, the Hilt component and the Injector for android component can be found
 * https://developer.android.com/training/dependency-injection/hilt-android#generated-components
 */
@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    /**
     * the function parameter of @Provides, tells the transitive dependencies,
     * the return type tells hilt of the binding
     */
    @Provides
    fun provideLogDao(database: AppDatabase): LogDao {
        return database.logDao()
    }

    /** this project doesn't own the AppDatabase class, because it is generated by room,
     *  we can't constructor inject AppDatabase
     *
     *  Each Hilt container comes with a set of default bindings, that can be injected
     *  as dependencies into your custom bindings. Annotate @ApplicationContext to access
     *  the `applicationContext`
     *
     *  @Provides creates a static binding of interface
     */
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "logging.db"
        ).build()
    }
}