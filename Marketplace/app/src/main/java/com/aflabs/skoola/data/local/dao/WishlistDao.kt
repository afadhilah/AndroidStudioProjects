package com.aflabs.skoola.data.local.dao

import androidx.room.*
import com.aflabs.skoola.data.local.entity.WishlistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {
    @Query("SELECT * FROM wishlist_items")
    fun getWishlistItems(): Flow<List<WishlistEntity>>

    @Query("SELECT COUNT(*) FROM wishlist_items WHERE id = :id")
    fun isWishlisted(id: String): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: WishlistEntity)

    @Query("DELETE FROM wishlist_items WHERE id = :id")
    suspend fun deleteById(id: String)
}
