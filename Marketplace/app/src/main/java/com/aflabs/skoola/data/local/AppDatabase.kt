package com.aflabs.skoola.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aflabs.skoola.data.local.dao.AddressDao
import com.aflabs.skoola.data.local.dao.CartDao
import com.aflabs.skoola.data.local.dao.WishlistDao
import com.aflabs.skoola.data.local.entity.AddressEntity
import com.aflabs.skoola.data.local.entity.CartEntity
import com.aflabs.skoola.data.local.entity.WishlistEntity

@Database(
    entities = [
        CartEntity::class,
        WishlistEntity::class,
        AddressEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun wishlistDao(): WishlistDao
    abstract fun addressDao(): AddressDao
}
