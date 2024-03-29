package com.fred_projects.database.dao

import androidx.room.*
import com.fred_projects.education.rest_api.math.model.entity.MathEntity

@Dao
interface IMathDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMathInfo(math: MathEntity)
    @Query("DELETE FROM MathEntity WHERE expression = :expression")
    suspend fun deleteMathInfo(expression: String)
    @Query("SELECT * FROM MathEntity WHERE expression = :expression")
    suspend fun getMathInfo(expression: String): MathEntity?
}