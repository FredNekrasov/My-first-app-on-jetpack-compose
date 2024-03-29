package com.fred_projects.education.main.model.repository

import com.fred_projects.database.dao.IMainDao
import com.fred_projects.education.main.model.entity.PracticalWork
import kotlinx.coroutines.flow.Flow

class MainRepository(private val dao: IMainDao) : IMainRepository {
    override fun getData(): Flow<List<PracticalWork>> = dao.getData()

    override suspend fun deleteRecord(pw: PracticalWork) = dao.deleteRecord(pw)

    override suspend fun insertOrUpdateRecord(pw: PracticalWork) = dao.insertRecord(pw)

    override suspend fun getRecord(id: Int): PracticalWork? = dao.getRecord(id)
}