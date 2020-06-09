package com.claykab.roomapptodolist.persistence;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimeStap(Long value){
        return value ==null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date){
        return date == null ? null: date.getTime();
    }



}
