package io.usmon.registration.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.usmon.registration.data.data_source.type_converter.UserTypeConverter
import io.usmon.registration.data.model.local.entity.UserEntity
import io.usmon.registration.util.Constants.DB_VERSION

// Created by Usmon Abdurakhmanv on 5/11/2022.

@Database(
    entities = [UserEntity::class],
    version = DB_VERSION,
    exportSchema = false
)
@TypeConverters(UserTypeConverter::class)
abstract class UserDatabase : RoomDatabase() {

    abstract val dao: UserDao
}