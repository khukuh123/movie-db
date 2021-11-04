package com.miko.moviedb.ext.utils

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromString(value: String): ArrayList<String> =
        ArrayList(value.split(','))

    @TypeConverter
    fun fromArrayList(value: ArrayList<String>): String =
        value.joinToString()
}