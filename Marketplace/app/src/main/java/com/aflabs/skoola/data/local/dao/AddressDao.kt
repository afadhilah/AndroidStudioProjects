package com.aflabs.skoola.data.local.dao

import androidx.room.*
import com.aflabs.skoola.data.local.entity.AddressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {
    @Query("SELECT * FROM addresses")
    fun getAddresses(): Flow<List<AddressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(address: AddressEntity)

    @Query("DELETE FROM addresses WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("UPDATE addresses SET isPrimary = 0")
    suspend fun clearPrimaryFlags()

    @Transaction
    suspend fun insertAndSetPrimary(address: AddressEntity) {
        if (address.isPrimary) {
            clearPrimaryFlags()
        }
        insert(address)
    }
}
