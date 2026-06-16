package com.aflabs.skoola.di

import android.content.Context
import androidx.room.Room
import com.aflabs.skoola.data.local.AppDatabase
import com.aflabs.skoola.data.local.dao.AddressDao
import com.aflabs.skoola.data.local.dao.CartDao
import com.aflabs.skoola.data.local.dao.WishlistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "skoola_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideCartDao(database: AppDatabase): CartDao = database.cartDao()

    @Provides
    fun provideWishlistDao(database: AppDatabase): WishlistDao = database.wishlistDao()

    @Provides
    fun provideAddressDao(database: AppDatabase): AddressDao = database.addressDao()
}
