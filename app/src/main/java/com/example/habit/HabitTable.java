package com.example.habit;

import android.provider.BaseColumns;

/**
 * Created by nicoleenos on 12/12/16.
 */

public class HabitTable {

    public HabitTable(){

    }

    public static abstract class HabitInfo implements BaseColumns{
        public static final String HABIT_NAME = "habit";
        public static final String TIME = "12:00";
        public static final String FREQ = "1";
        public static final String DATABASE_NAME = "User_Input";
        public static final String TABLE_NAME = "habit_info";
    }

}
