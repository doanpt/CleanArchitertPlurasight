package com.ddona.banking.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ddona.banking.local.model.UserInfoLocal
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface UserInfoDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUserInfo(userInfo: UserInfoLocal)

    @Query("SELECT * FROM user_info WHERE account_id = :userIdentifier")
    fun getUserInfo(userIdentifier: String): Observable<UserInfoLocal>

    @Query("DELETE FROM user_info")
    fun clearCachedUserInfo(): Completable
}