package com.ddona.banking.local.database

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ddona.banking.local.model.TransactionLocal
import com.ddona.banking.local.model.UserInfoLocal


@Database(
    entities = [UserInfoLocal::class, TransactionLocal::class],
    version = 1,
    exportSchema = false
)
abstract class BankBuddyDatabase : RoomDatabase() {

    companion object {
        private val LOCK = Any()
        private const val DATABASE_NAME = "bank_buddy.db"
        @Volatile
        private var INSTANCE: BankBuddyDatabase? = null

        fun getInstance(@NonNull context: Context): BankBuddyDatabase {
            if (INSTANCE == null) {
                synchronized(LOCK) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            BankBuddyDatabase::class.java,
                            DATABASE_NAME
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

    abstract fun getTransactionDao(): TransactionDAO

    abstract fun getUserInfoDao(): UserInfoDAO

}

