package com.example.happybirthday

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface transactionDao {
    @Query("SELECT * from transactions")
    fun getAll(): List<Transaction>

    @Insert
    fun insertAll(vararg transaction: Transaction)

    @Delete
    fun delete(transaction: Transaction)

    @Query("DELETE FROM transactions")
     fun deleteAll()


    @Update
    fun update(vararg transaction: Transaction)

}