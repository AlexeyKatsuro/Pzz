package com.alexeykatsuro.pzz.di.db

import android.content.Context
import androidx.room.Room
import com.alexeykatsuro.pzz.data.db.DatabaseTransactionRunner
import com.alexeykatsuro.pzz.data.db.PzzDataBase
import com.alexeykatsuro.pzz.data.db.RoomPzzDatabase
import com.alexeykatsuro.pzz.data.db.RoomTransactionRunner
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(
    includes = [
        RoomDatabaseModule::class,
        DatabaseDaoModule::class,
        DatabaseModuleBinds::class
    ]
)
class DatabaseModule

@Module
class RoomDatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Context): RoomPzzDatabase {
        val builder =
            Room.databaseBuilder(
                context,
                RoomPzzDatabase::class.java,
                "PzzDatabase.db"
            ).fallbackToDestructiveMigration()

        return builder.build()
    }
}

@Module
class DatabaseDaoModule {
    @Provides
    fun provideProductsDao(db: PzzDataBase) = db.productsDao()


}

@Module
abstract class DatabaseModuleBinds {

    @Binds
    abstract fun bindPzzBankDatabase(context: RoomPzzDatabase): PzzDataBase

    @Singleton
    @Binds
    abstract fun provideDatabaseTransactionRunner(runner: RoomTransactionRunner): DatabaseTransactionRunner

}
