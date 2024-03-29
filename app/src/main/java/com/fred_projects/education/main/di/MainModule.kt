package com.fred_projects.education.main.di

import com.fred_projects.database.MainDB
import com.fred_projects.education.main.model.repository.*
import com.fred_projects.education.main.model.verification.check_date.ICheckDate
import com.fred_projects.education.main.model.verification.check_image.ICheckImage
import com.fred_projects.education.main.model.verification.check_lvl.ICheckLVL
import com.fred_projects.education.main.model.verification.check_mark.ICheckMark
import com.fred_projects.education.main.model.verification.check_pw.ICheckPW
import com.fred_projects.education.main.model.verification.check_student.ICheckStudent
import com.fred_projects.education.main.model.verification.check_variant.ICheckVariant
import com.fred_projects.education.main.use_case.MainUseCases
import com.fred_projects.education.main.use_case.crud.*
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    @Singleton
    fun provideRepository(db: MainDB): IMainRepository = MainRepository(db.mainDao)
    @Provides
    @Singleton
    fun provideGetData(repository: IMainRepository) = GetPWData(repository)
    @Provides
    @Singleton
    fun provideDeleteData(repository: IMainRepository) = DeletePWData(repository)
    @Provides
    @Singleton
    fun provideAddData(
        repository: IMainRepository,
        checkPW: ICheckPW,
        checkStudent: ICheckStudent,
        checkVariant: ICheckVariant,
        checkLVL: ICheckLVL,
        checkDate: ICheckDate,
        checkMark: ICheckMark,
        checkImage: ICheckImage
    ) = AddPWData(repository, checkPW, checkStudent, checkVariant, checkLVL, checkDate, checkMark, checkImage)
    @Provides
    @Singleton
    fun provideGetPW(repository: IMainRepository) = GetPWRecord(repository)
    @Provides
    @Singleton
    fun provideDatabaseUseCases(
        getData: GetPWData,
        deleteData: DeletePWData,
        addData: AddPWData,
        getPW: GetPWRecord
    ) = MainUseCases(getData, deleteData, addData, getPW)
}